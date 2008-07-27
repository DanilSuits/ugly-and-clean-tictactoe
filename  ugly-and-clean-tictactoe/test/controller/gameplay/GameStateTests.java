package controller.gameplay;

import junit.framework.TestCase;
import model.gamestate.Board;
import model.gamestate.GameState;
import model.strategy.PatrickStrategy;

public class GameStateTests extends TestCase {
	private static final int NOBODY_PLAYER_MARK_FORCES_DRAW = 7;
	private static final String DOUBLE_BLANK_SPACE = "  ";
	private static final String SINGLE_BLANK_SPACE = " ";

	private TicTacToeGame game;
	private Board board;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		game  = new TicTacToeGame(new PatrickStrategy(), new StubView());
		board = game.getBoard();
	}
	
	public void testGameStart() throws Exception {
		assertTrue(game.justStarted());
		assertEquals(0, game.moveNumber());
		assertTrue(game.getGameState().inPlay());
		assertTrue(game.inPlay());
		assertFalse(game.getGameState().theyWon());
		assertFalse(game.getGameState().weWon());
		assertFalse(game.weHaveAWinner());
	}
	
	public void testGameStateGetterOnGame() throws Exception {
		GameState gameState =  game.getGameState();
		
		assertEquals(GameState.IN_PLAY, gameState.state());
	}
	
	public void testLastMove() throws Exception {
		GameState gameState =  game.getGameState();
		gameState.setLastMove(48);
		
		assertEquals(48, game.lastMove());
	}
	
	public void testWeWinTheGame() throws Exception {
		board.setPosition(4, 4, Board.OUR_PLAYER_MARK);
		board.setPosition(4, 5, Board.OUR_PLAYER_MARK);
		board.setPosition(4, 6, Board.OUR_PLAYER_MARK);
		board.setPosition(4, 7, Board.OUR_PLAYER_MARK);
		board.setPosition(4, 8, Board.OUR_PLAYER_MARK);
		
		game.makeCompleteMoveCycle(48);
		
		assertTrue(game.getGameState().weWon());
		assertTrue(game.weWon());
		assertTrue(game.weHaveAWinner());
		assertFalse(game.getGameState().inPlay());
		assertFalse(game.inPlay());
		assertFalse(game.getGameState().theyWon());
	}
	
	public void testTheyWinTheGame() throws Exception {
		board.setPosition(4, 4, Board.THEIR_PLAYER_MARK);
		board.setPosition(4, 5, Board.THEIR_PLAYER_MARK);
		board.setPosition(4, 6, Board.THEIR_PLAYER_MARK);
		board.setPosition(4, 7, Board.THEIR_PLAYER_MARK);
		board.setPosition(4, 8, Board.THEIR_PLAYER_MARK);
		
		game.makeCompleteMoveCycle(48);
		
		assertTrue(game.getGameState().theyWon());
		assertTrue(game.weHaveAWinner());
		assertTrue(game.theyWon());
		assertFalse(game.getGameState().weWon());
		assertFalse(game.getGameState().inPlay());
		assertFalse(game.inPlay());
	}
	
	public void testDraw() throws Exception {
		int mark = Board.THEIR_PLAYER_MARK;
		
		for (int position = 0; position < Board.MAX_BOARD_SIZE; position++){
			mark = NOBODY_PLAYER_MARK_FORCES_DRAW;
			board.setPosition(Board.getRowCoordFor(position), 
							Board.getColumnCoordFor(position), mark);
		}
		
		for (int j = 0; j < 49; j++) {
			game.incrementMoveNumber();
		}
		
		System.out.println("final movenumber = " + game.moveNumber());
		System.out.println(returnPrintableBoard("\n"));
		
		assertFalse(game.getGameState().theyWon());
		assertTrue(game.getGameState().draw());
		assertFalse(game.getGameState().weWon());
		assertFalse(game.getGameState().inPlay());
	}
	
	
	private String returnPrintableBoard(String crCharacter) {
		String border = "*****************************" + crCharacter;
		String boardString = border;
		String spacer = SINGLE_BLANK_SPACE;

		spacer = DOUBLE_BLANK_SPACE;
		for (int x = 0; x < 10; x++) {
			boardString = printBoardRow(boardString, spacer, x);
		}
		boardString += border;

		return boardString;
	}

	
	private String printBoardRow(String boardString, String spacer, int x) {
		int cell;
		for (int y = 0; y < 10; y++) {
			String mark = null;
			cell = game.getPosition(y);
			
			if (doubleDigitRow(cell)) 
				spacer = SINGLE_BLANK_SPACE;
			else if ((game.getPosition(0) == 0) && (game.getPosition(1) == 0))
				spacer = DOUBLE_BLANK_SPACE;
			else 
				spacer = DOUBLE_BLANK_SPACE;

			if (game.getPosition(cell) == 2) {
				mark = "00";
				if (cell < 9) spacer = SINGLE_BLANK_SPACE;
			}
			if (game.getPosition(cell) == 1) {
				mark = "XX";
				if (cell < 9) spacer = SINGLE_BLANK_SPACE;
			}
			if (mark == null) {
				boardString += "" + cell + spacer;
			} else {
				boardString += "" + mark + spacer;
			}
		}
		boardString += "\n";
		return boardString;
	}

	private boolean doubleDigitRow(int cell) {
		return cell > 9;
	}


}
