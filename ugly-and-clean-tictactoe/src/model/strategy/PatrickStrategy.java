package model.strategy;

import model.gamestate.Board;
import model.gamestate.Move;
import model.gamestate.MoveGroup;
import model.gamestate.Board.SeriesSize;
import model.patternsearching.PatternFinder;
import controller.gameplay.TicTacToeGame.MoveScore;

public class PatrickStrategy implements IStrategy {
	private Board board;
	private PatternFinder patternFinder;

	public PatrickStrategy() {
		this.board = new Board();
		this.patternFinder = new PatternFinder(board);
	}

	public IStrategy newInstance() {
		return new PatrickStrategy();
	}

	public Board getBoard() {
		return board;
	}

	public boolean wonTheGame(int playerMark, SeriesSize winningSeriesSize) {
		return patternFinder.canFindSeriesOfSize(winningSeriesSize, playerMark);
	}

	public int makeMove(boolean reporting) {
		MoveGroup moveGroup = getAllPossibleMoves(reporting);
		Move highestScoringMove = highestScoringMove(moveGroup);
		
		if (foundAGoodMove(highestScoringMove)) {
			return highestScoringMove.getPosition();
		}

		throw new RuntimeException("Could not find any moves to make.");
	}

	private Move highestScoringMove(MoveGroup moveGroup) {
		return moveGroup.bestOverallMove();
	}

	private boolean foundAGoodMove(Move highestScoringMove) {
		return PatrickStrategy.weFoundAGoodPosition(highestScoringMove.getPosition());
	}

	private MoveGroup getAllPossibleMoves(boolean reporting) {
		MoveGroup moveGroup = new MoveGroup(reporting);

		moveGroup = gatherPenteMoves(moveGroup);
		moveGroup = gatherTesseraMoves(moveGroup);
		moveGroup = gatherTriaMoves(moveGroup);
		moveGroup = gatherLowerPriorityMoves(moveGroup);
		return moveGroup;
	}

	private MoveGroup gatherPenteMoves(MoveGroup moveGroup) {
		gatherTesseraCappingMoves(moveGroup);
		gatherPentaBlockingMoves(moveGroup);
		gatherTesseraBlockingMoves(moveGroup);
		gatherPentaGapFillingMoves(moveGroup);

		return moveGroup;
	}

	private MoveGroup gatherTesseraMoves(MoveGroup moveGroup) {
		gatherTriaBlockingMoves(moveGroup);
		gatherTesseraGapBlockingMoves(moveGroup);
		gatherTriaCappingMoves(moveGroup);
		gatherTesseraGapFillingMoves(moveGroup);

		return moveGroup;
	}

	private MoveGroup gatherTriaMoves(MoveGroup moveGroup) {
		gatherTriaGapBlockingMoves(moveGroup);
		gatherPairBlockingMoves(moveGroup);
		gatherTriaGapFillingMoves(moveGroup);
		gatherPairCappingMoves(moveGroup);

		return moveGroup;
	}

	private MoveGroup gatherLowerPriorityMoves(MoveGroup moveGroup) {
		gatherShadowCornerMoves(moveGroup);
		findRandomMidBoardMove(moveGroup);
		findRandomOpenPosition(moveGroup);

		return moveGroup;
	}

	private void gatherPentaGapFillingMoves(MoveGroup moveGroup) {
		String message;
		message = "Penta gap-blocking position: ";
		moveGroup.add(new Move(patternFinder.getGapForGapSeriesOfSize(
				SeriesSize.FIVE, Board.THEIR_PLAYER_MARK), MoveScore.NINE,
				message));
		moveGroup.add(new Move(patternFinder
				.getAlternateBlockingPositionForSeries(), MoveScore.NINE,
				message));
	}

	private void gatherTesseraBlockingMoves(MoveGroup moveGroup) {
		String message;
		message = "Tessera-blocking position: ";
		moveGroup.add(new Move(patternFinder
				.getBestBlockingPositionForSeriesOfSize(SeriesSize.FOUR,
						Board.THEIR_PLAYER_MARK), MoveScore.NINE, message));
		moveGroup.add(new Move(patternFinder
				.getAlternateBlockingPositionForSeries(), MoveScore.NINE,
				message));
	}

	private void gatherPentaBlockingMoves(MoveGroup moveGroup) {
		String message;
		message = "Penta gap-filling position: ";
		moveGroup
				.add(new Move(patternFinder.getGapForGapSeriesOfSize(
						SeriesSize.FIVE, Board.OUR_PLAYER_MARK), MoveScore.TEN,
						message));
		moveGroup.add(new Move(patternFinder
				.getAlternateBlockingPositionForSeries(), MoveScore.TEN,
				message));
	}

	private void gatherTesseraCappingMoves(MoveGroup moveGroup) {
		String message;
		message = "Tessera-capping position: ";
		moveGroup.add(new Move(patternFinder
				.getBestBlockingPositionForSeriesOfSize(SeriesSize.FOUR,
						Board.OUR_PLAYER_MARK), MoveScore.TEN, message));
		moveGroup.add(new Move(patternFinder
				.getAlternateBlockingPositionForSeries(), MoveScore.TEN,
				message));
	}

	private void gatherTesseraGapFillingMoves(MoveGroup moveGroup) {
		String message;
		message = "Tessera gap-filling position: ";
		moveGroup.add(new Move(patternFinder.getGapForGapSeriesOfSize(
				SeriesSize.FOUR, Board.OUR_PLAYER_MARK), MoveScore.SEVEN,
				message));
		moveGroup.add(new Move(patternFinder
				.getAlternateBlockingPositionForSeries(), MoveScore.SEVEN,
				message));
	}

	private void gatherTriaCappingMoves(MoveGroup moveGroup) {
		String message;
		message = "Tria-capping position: ";
		moveGroup.add(new Move(patternFinder
				.getBestBlockingPositionForSeriesOfSize(SeriesSize.THREE,
						Board.OUR_PLAYER_MARK), MoveScore.SEVEN, message));
		moveGroup.add(new Move(patternFinder
				.getAlternateBlockingPositionForSeries(), MoveScore.SEVEN,
				message));
	}

	private void gatherTesseraGapBlockingMoves(MoveGroup moveGroup) {
		String message;
		message = "Tessera gap-blocking position: ";
		moveGroup.add(new Move(patternFinder.getGapForGapSeriesOfSize(
				SeriesSize.FOUR, Board.THEIR_PLAYER_MARK), MoveScore.EIGHT,
				message));
		moveGroup.add(new Move(patternFinder
				.getAlternateBlockingPositionForSeries(), MoveScore.EIGHT,
				message));
	}

	private void gatherTriaBlockingMoves(MoveGroup moveGroup) {
		String message;
		message = "Tria-blocking position: ";
		moveGroup.add(new Move(patternFinder
				.getBestBlockingPositionForSeriesOfSize(SeriesSize.THREE,
						Board.THEIR_PLAYER_MARK), MoveScore.EIGHT, message));
		moveGroup.add(new Move(patternFinder
				.getAlternateBlockingPositionForSeries(), MoveScore.EIGHT,
				message));
	}

	private void gatherPairCappingMoves(MoveGroup moveGroup) {
		String message;
		message = "Pair-capping position: ";
		moveGroup.add(new Move(patternFinder
				.getBestBlockingPositionForSeriesOfSize(SeriesSize.TWO,
						Board.OUR_PLAYER_MARK), MoveScore.FIVE, message));
	}

	private void gatherTriaGapFillingMoves(MoveGroup moveGroup) {
		String message;
		message = "Tria gap-filling position: ";
		moveGroup.add(new Move(patternFinder.getGapForGapSeriesOfSize(
				SeriesSize.THREE, Board.OUR_PLAYER_MARK), MoveScore.FIVE,
				message));
	}

	private void gatherPairBlockingMoves(MoveGroup moveGroup) {
		String message;
		message = "Pair-blocking position: ";
		moveGroup.add(new Move(patternFinder
				.getBestBlockingPositionForSeriesOfSize(SeriesSize.TWO,
						Board.THEIR_PLAYER_MARK), MoveScore.SIX, message));
	}

	private void gatherTriaGapBlockingMoves(MoveGroup moveGroup) {
		String message;
		message = "Tria gap-blocking position: ";
		moveGroup.add(new Move(patternFinder.getGapForGapSeriesOfSize(
				SeriesSize.THREE, Board.THEIR_PLAYER_MARK), MoveScore.SIX,
				message));
	}

	private void findRandomOpenPosition(MoveGroup moveGroup) {
		String message;
		message = "random open position: ";
		moveGroup.add(new Move(patternFinder.findRandomEmptyPosition(),
				MoveScore.ZERO, message));
	}

	private void findRandomMidBoardMove(MoveGroup moveGroup) {
		String message;
		message = "random open mid-board position: ";
		moveGroup.add(new Move(patternFinder.findRandomEmptyMidBoardPosition(),
				MoveScore.ONE, message));
	}

	private void gatherShadowCornerMoves(MoveGroup moveGroup) {
		String message;
		message = "Shadow corner near opposing player position: ";
		moveGroup.add(new Move(patternFinder
				.getBestShadowPosition(Board.OUR_PLAYER_MARK), MoveScore.TWO,
				message));
	}

	private static boolean weFoundAGoodPosition(int position) {
		return position != -1;
	}

}
