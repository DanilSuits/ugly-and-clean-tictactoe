package oldagainstnewgame;

import java.math.BigDecimal;
import legacyGame.LegacyGame;
import model.gamestate.Board;
import model.strategy.ExampleStrategy;
import baseManualTestUtils.BaseSeriesMethodTestFixture;
import controller.gameplay.StubView;
import controller.gameplay.TicTacToeGame;

public class OldGameAgainstNewGameTests extends BaseSeriesMethodTestFixture {
	private TicTacToeGame newGame;
	private int moveNumberTotals;
	private BigDecimal newGamePercentage;
	private BigDecimal oldGamePercentage;
	private BigDecimal drawPercentage;
	private int averageMovesPerGame;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		newGame = new TicTacToeGame(new ExampleStrategy(), new StubView());
		drawTotal = 0;
		oldGameWinTotal = 0;
		newGameTotal = 0;
		moveNumberTotals = 0;
	}

	// TODO: Need board-comparison utility between moves, to ensure that only
	// one place changed!
	public void testNewGameBeatsOrDrawsOldGameMostOfTheTime() throws Exception {
		reporting = false;
		int totalGamesPlayed = 200;

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < totalGamesPlayed; i++) {
			playNewGameAgainstOldGame();
			if (reporting)
				System.out
						.println("************************************************ STARTING OVER ***********************************************");
			oldGame = null;
			oldGame = new LegacyGame();
			newGame = null;
			newGame = new TicTacToeGame(new ExampleStrategy(), new StubView());
		}
		reportResults(totalGamesPlayed, startTime);

		assertTrue(averageMovesPerGame > 15);
		assertTrue(newGamePercentage.floatValue() > 40);
		assertTrue(oldGamePercentage.floatValue() < 10);
		assertTrue(drawPercentage.floatValue() > 35);
	}

	private void playNewGameAgainstOldGame() {
		int newGamePosition = getRandomValidMove();
		oldGame.moveNumber++;
		int oldGamePosition = 0;

		while ((oldGame.gameState < 2)
				&& (theMaximumNumberOfMovesHasNotBeenTaken())) {
			oldGame.moveNumber++;
			newGamePosition = newGameMakesMove(newGamePosition, oldGamePosition);
			oldGamePosition = oldGameMakesMove(newGamePosition, oldGamePosition);
		}

		determineWinner();
	}

	private void reportResults(int totalGamesPlayed, long startTime) {
		long endTime = System.currentTimeMillis();
		long durationInSeconds = (endTime - startTime) / 1000;
		Long longMinutes = durationInSeconds / 60;
		int minutes = longMinutes.intValue();
		int secondsRemainder = new Long(durationInSeconds % 60).intValue();

		System.out.println();
		System.out.println("Total duration = " + minutes + " minutes and "
				+ secondsRemainder + " seconds.");
		System.out.println("Games played = " + totalGamesPlayed);
		newGamePercentage = getPercentageOfTime(newGameTotal, totalGamesPlayed);
		oldGamePercentage = getPercentageOfTime(oldGameWinTotal,
				totalGamesPlayed);
		drawPercentage = getPercentageOfTime(drawTotal, totalGamesPlayed);
		averageMovesPerGame = moveNumberTotals / totalGamesPlayed;
		System.out.println("Average moves per game = " + averageMovesPerGame);
		System.out.println("New Game won " + newGameTotal + " times, or "
				+ newGamePercentage + "%");
		System.out.println("Old Game won " + oldGameWinTotal + " times, or "
				+ oldGamePercentage + "%");
		System.out.println("Nobody won " + drawTotal + "  times, or "
				+ drawPercentage + "%");
	}

	private BigDecimal getPercentageOfTime(int total, int totalGamesPlayed) {
		Float totalFloat = new Float(total);
		Float gamesPlayedTotalFloat = new Float(totalGamesPlayed);
		Float percentageFloat = (totalFloat / gamesPlayedTotalFloat) * 100;
		BigDecimal percentage = new BigDecimal(percentageFloat);
		percentage = percentage.setScale(2, BigDecimal.ROUND_UP);

		return percentage;
	}

	private void determineWinner() {
		String winner = "Nobody";
		if (oldGame.gameState == 2)
			winner = "New Game";
		if (oldGame.gameState == 3)
			winner = "Old Game";

		if (winner == "Nobody")
			drawTotal++;
		moveNumberTotals += oldGame.moveNumber;

		System.out.println(winner + " wins in " + oldGame.moveNumber
				+ " moves.");
		if (reporting)
			System.out.println(oldGame
					.returnPrintableBoard(LegacyGame.CR_CHARACTER));
	}

	private int oldGameMakesMove(int newGamePosition, int oldGamePosition) {
		if (!newGameWon())
			oldGamePosition = oldGameMakesAMove(newGamePosition);
		if (reporting)
			System.out.println(oldGame
					.returnPrintableBoard(LegacyGame.CR_CHARACTER));

		if (oldGameWon()) {
			setGameStateToOldGameWon();
		}
		return oldGamePosition;
	}

	private int newGameMakesMove(int newGamePosition, int oldGamePosition) {
		if (!oldGameWon()) {
			newGamePosition = newGameMakesAMove(oldGamePosition);
		}
		if (reporting)
			System.out.println(oldGame
					.returnPrintableBoard(LegacyGame.CR_CHARACTER));

		if (newGameWon()) {
			setGameStateToNewGameWon();
		}
		return newGamePosition;
	}

	private int oldGameMakesAMove(int playerPosition) {
		int x = (playerPosition % Board.MAX_COLUMNS);
		int y = (playerPosition - x) / Board.MAX_ROWS;
		int respondingPosition = oldGame.makeComputerMove(x, y, reporting);
		takePosition(respondingPosition, LegacyGame.ZERO_MARK_FOR_COMPUTER,
				MAIN_LEVEL);
		return respondingPosition;
	}

	private int newGameMakesAMove(int computerPosition) {
		newGame.setBoard(oldGame.gameBoard[0]);
		int position = newGame.makeMove(reporting);

		takePosition(position, LegacyGame.X_MARK_FOR_PLAYER, MAIN_LEVEL);

		return position;
	}

}