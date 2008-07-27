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
	
	public IStrategy gimmeANewOne() {
		return new PatrickStrategy();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int makeMove(boolean reporting) {
		Move highestScoringMove;
		MoveGroup moveGroup = new MoveGroup(reporting);
		
		moveGroup = gatherPenteMoves(moveGroup);		
		moveGroup = gatherTesseraMoves(moveGroup);		
		moveGroup = gatherTriaMoves(moveGroup);
		moveGroup = gatherLowerPriorityMoves(moveGroup);
		
		highestScoringMove = moveGroup.bestOverallMove();
		boolean foundAGoodMove = PatrickStrategy.weFoundAGoodPosition(highestScoringMove.getPosition());
		
		if (foundAGoodMove) {
			if (reporting) System.out.println("New Game found " + highestScoringMove.getMessage() + " " + highestScoringMove.getPosition());
			return highestScoringMove.getPosition();
		}
		
		throw new RuntimeException("Could not find any moves to make.");
	}
	
	public boolean wonTheGame(int playerMark, SeriesSize winningSeriesSize) {
		return patternFinder.canFindSeriesOfSize(winningSeriesSize, playerMark);
	}

	private MoveGroup gatherPenteMoves(MoveGroup moveGroup) {
		String message;
		message = "Tessera-capping position: ";
		moveGroup.add(new Move(patternFinder.getBestBlockingPositionForSeriesOfSize(SeriesSize.FOUR, Board.OUR_PLAYER_MARK), MoveScore.TEN, message));
		moveGroup.add(new Move(patternFinder.getAlternateBlockingPositionForSeries(), MoveScore.TEN, message));
		
		message = "Penta gap-filling position: ";
		moveGroup.add(new Move(patternFinder.getGapForGapSeriesOfSize(SeriesSize.FIVE, Board.OUR_PLAYER_MARK), MoveScore.TEN, message));
		moveGroup.add(new Move(patternFinder.getAlternateBlockingPositionForSeries(), MoveScore.TEN, message));
				
		message = "Tessera-blocking position: ";
		moveGroup.add(new Move(patternFinder.getBestBlockingPositionForSeriesOfSize(SeriesSize.FOUR, Board.THEIR_PLAYER_MARK), MoveScore.NINE, message));
		moveGroup.add(new Move(patternFinder.getAlternateBlockingPositionForSeries(), MoveScore.NINE, message));
	
		message = "Penta gap-blocking position: ";
		moveGroup.add(new Move(patternFinder.getGapForGapSeriesOfSize(SeriesSize.FIVE, Board.THEIR_PLAYER_MARK), MoveScore.NINE, message));		
		moveGroup.add(new Move(patternFinder.getAlternateBlockingPositionForSeries(), MoveScore.NINE, message));		
	
		return moveGroup;
	}

	MoveGroup gatherTesseraMoves(MoveGroup moveGroup) {
		String message;
		message = "Tria-blocking position: ";
		moveGroup.add(new Move(patternFinder.getBestBlockingPositionForSeriesOfSize(SeriesSize.THREE, Board.THEIR_PLAYER_MARK), MoveScore.EIGHT, message));
		moveGroup.add(new Move(patternFinder.getAlternateBlockingPositionForSeries(), MoveScore.EIGHT, message));
		
		message = "Tessera gap-blocking position: ";
		moveGroup.add(new Move(patternFinder.getGapForGapSeriesOfSize(SeriesSize.FOUR, Board.THEIR_PLAYER_MARK), MoveScore.EIGHT, message));		
		moveGroup.add(new Move(patternFinder.getAlternateBlockingPositionForSeries(), MoveScore.EIGHT, message));		
		
		message = "Tria-capping position: ";
		moveGroup.add(new Move(patternFinder.getBestBlockingPositionForSeriesOfSize(SeriesSize.THREE, Board.OUR_PLAYER_MARK), MoveScore.SEVEN, message));
		moveGroup.add(new Move(patternFinder.getAlternateBlockingPositionForSeries(), MoveScore.SEVEN, message));
	
		message = "Tessera gap-filling position: ";
		moveGroup.add(new Move(patternFinder.getGapForGapSeriesOfSize(SeriesSize.FOUR, Board.OUR_PLAYER_MARK), MoveScore.SEVEN, message));
		moveGroup.add(new Move(patternFinder.getAlternateBlockingPositionForSeries(), MoveScore.SEVEN, message));
		
		return moveGroup;
	}

	MoveGroup gatherTriaMoves(MoveGroup moveGroup) {
		String message;
		message = "Tria gap-blocking position: ";
		moveGroup.add(new Move(patternFinder.getGapForGapSeriesOfSize(SeriesSize.THREE, Board.THEIR_PLAYER_MARK), MoveScore.SIX, message));		
		
		message = "Pair-blocking position: ";
		moveGroup.add(new Move(patternFinder.getBestBlockingPositionForSeriesOfSize(SeriesSize.TWO, Board.THEIR_PLAYER_MARK), MoveScore.SIX, message));
		
		message = "Tria gap-filling position: ";
		moveGroup.add(new Move(patternFinder.getGapForGapSeriesOfSize(SeriesSize.THREE, Board.OUR_PLAYER_MARK), MoveScore.FIVE, message));		
		
		message = "Pair-capping position: ";
		moveGroup.add(new Move(patternFinder.getBestBlockingPositionForSeriesOfSize(SeriesSize.TWO, Board.OUR_PLAYER_MARK), MoveScore.FIVE, message));
		
		return moveGroup;
	}

	MoveGroup gatherLowerPriorityMoves(MoveGroup moveGroup) {
		String message;
		message = "Shadow corner near opposing player position: ";
		moveGroup.add(new Move(patternFinder.getBestShadowPosition(Board.OUR_PLAYER_MARK), MoveScore.TWO, message));
		
		message = "random open mid-board position: ";
		moveGroup.add(new Move(patternFinder.findRandomEmptyMidBoardPosition(), MoveScore.ONE, message));
		
		message = "random open position: ";
		moveGroup.add(new Move(patternFinder.findRandomEmptyPosition(), MoveScore.ZERO, message));
		
		return moveGroup;
	}

	private static boolean weFoundAGoodPosition(int position) {
		return position != -1;
	}

}
