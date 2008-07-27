package model.patternsearching;

import model.gamestate.Board;
import model.gamestate.Board.SeriesSize;

public class PatternFinder {
	private RandomPositionFinder randomPositionFinder;
	private ShadowPositionFinder shadowFinder;
	private SeriesFinder seriesFinder;
	private GapSeriesFinder gapSeriesFinder;

	public PatternFinder(Board board) {
		randomPositionFinder = new RandomPositionFinder(board);
		shadowFinder = new ShadowPositionFinder(board);
		seriesFinder = new SeriesFinder(board);
		gapSeriesFinder = new GapSeriesFinder(board);
	}
	
	public int findRandomEmptyMidBoardPosition() {
		return randomPositionFinder.findRandomEmptyMidBoardPosition();	
	}

	public int getBestShadowPosition(int ourPlayerMark) {
		return shadowFinder.getPositionInMostOpenSeriesNearOppositePlayer(ourPlayerMark);
	}
	
	public int findRandomEmptyPosition() {
		return randomPositionFinder.findRandomEmptyPosition();
	}

	public int getBestBlockingPositionForSeriesOfSize(SeriesSize size, int playerMark) {
		return seriesFinder.getBestBlockingPositionForSeriesOfSize(size, playerMark);
	}
	
	public int getAlternateBlockingPositionForSeries() {
		return seriesFinder.getAlternatePosition();
	}

	public int getGapForGapSeriesOfSize(SeriesSize size, int playerMark) {
		return gapSeriesFinder.getBestBlockingPositionForSeriesOfSize(size, playerMark);
	}

	public boolean canFindSeriesOfSize(SeriesSize size, int playerMark) {
		return seriesFinder.containsSeriesOfSize (size, playerMark);
	}
}
