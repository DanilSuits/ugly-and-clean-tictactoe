package controller.gameplay;

import junit.framework.TestCase;
import model.gamestate.Board;
import strategy.StrategyFake;

public class TicTacToeGameTests extends TestCase {

	public void testThatItTakesFiveForTheComputerToWin() {
		StrategyFake fakeStrategy = new StrategyFake();
		Integer currentSequenceSize = 4;
		fakeStrategy.playerSeriesSizes.put(Board.COMPUTER_PLAYER_MARK, currentSequenceSize);	
		
		TicTacToeGame game = new TicTacToeGame(fakeStrategy, new StubView());
		assertTrue("there was a winner when there shouldn't be", game.noWinnerYet());
		
		fakeStrategy.playerSeriesSizes.put(Board.COMPUTER_PLAYER_MARK, 5);
		assertFalse(game.noWinnerYet());
	}
}
