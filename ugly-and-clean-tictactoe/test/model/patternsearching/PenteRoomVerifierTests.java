package model.patternsearching;

import junit.framework.TestCase;
import model.gamestate.Board;
import model.patterns.IndexList;

public class PenteRoomVerifierTests extends TestCase {
	private PenteRoomVerifier verifier;
	private Board board;
	private IndexList list;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		board = new Board();
		verifier = new PenteRoomVerifier(board);
		list = new IndexList();
		list.add(50);
		list.add(51);
		list.add(52);
		list.add(53);
		list.add(54);
		list.add(55);
		list.add(56);
		list.add(57);
		list.add(58);
		list.add(59);
	}

	public void testReportsPenteRoomForPlayerMarkFromGivenPosition()
			throws Exception {
		board.setPosition(5, 3, Board.THEIR_PLAYER_MARK);

		assertTrue(verifier.hasPenteRoomForPlayerMark(list, Board
				.getSingleCoordValueFor(5, 6), Board.OUR_PLAYER_MARK));
	}

	public void testReportsNoPenteRoomForPlayerMarkFromGivenPosition()
			throws Exception {
		board.setPosition(5, 3, Board.THEIR_PLAYER_MARK);
		board.setPosition(5, 8, Board.THEIR_PLAYER_MARK);

		assertFalse(verifier.hasPenteRoomForPlayerMark(list, Board
				.getSingleCoordValueFor(5, 6), Board.OUR_PLAYER_MARK));
	}

}
