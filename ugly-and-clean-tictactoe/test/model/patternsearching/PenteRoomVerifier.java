package model.patternsearching;

import model.gamestate.Board;
import model.patterns.ISeries;
import model.patterns.DirectionalCorridors;

public class PenteRoomVerifier {
	private Board board;
	private int thisPlayerMark;

	public PenteRoomVerifier(Board board) {
		this.board = board;
	}

	public boolean hasPenteRoomForPlayerMark(DirectionalCorridors list, int trialPosition,
			int thisPlayerMark) {
		this.thisPlayerMark = thisPlayerMark;
		int roomBefore = getRoomBeforeThisPosition(list, trialPosition);
		int roomAfter = getRoomAfterThisPosition(trialPosition, list);

		return ((roomBefore + roomAfter) >= Board.PENTE_SIZE);
	}

	private int getRoomAfterThisPosition(int trialPosition, DirectionalCorridors list) {
		int roomAfter = 0;
		int trialPositionIndex = list.getIndexFor(trialPosition);

		for (int i = trialPositionIndex + 1; i < list.size(); i++) {
			if (!board.within1DBoardPositionIndexBounds(i))
				return roomAfter;
			if (weHaveEncounteredOtherPlayerMark(list.get(i)))
				return roomAfter;

			roomAfter++;
		}

		return roomAfter;
	}

	private int getRoomBeforeThisPosition(DirectionalCorridors list, int trialPosition) {
		int roomBefore = 0;
		int trialPositionIndex = list.getIndexFor(trialPosition);

		for (int i = trialPositionIndex; i > -1; i--) {
			if (!board.within1DBoardPositionIndexBounds(i))
				return roomBefore;
			if (weHaveEncounteredOtherPlayerMark(list.get(i)))
				return roomBefore;

			roomBefore++;
		}

		return roomBefore;
	}

	private boolean weHaveEncounteredOtherPlayerMark(int boardIndex) {
		return board.getPosition(boardIndex) == board
				.getOppositePlayerMarkFor(thisPlayerMark);
	}

	public boolean hasPenteRoom(ISeries currentSeries, DirectionalCorridors currentList,
			int thisPlayerMark) {
		return hasPenteRoomForPlayerMark(currentList, currentSeries.get(0),
				thisPlayerMark);
	}
}
