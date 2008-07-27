package controller.gameplay;

import model.gamestate.Board;
import model.gamestate.GameState;
import model.gamestate.Board.SeriesSize;
import model.strategy.IStrategy;
import view.applet.GameView;

public class TicTacToeGame {
	private SeriesSize winningSize = SeriesSize.FIVE;
	
	private Board board;
	private GameState gameState;
	private IStrategy strategy;

	private GameView view;
	
	public static final int MAX_NUMBER_OF_MOVES = 48;
	
	public enum MoveScore { TEN(10), 
							NINE(9), 
							EIGHT(8), 
							SEVEN(7), 
							SIX(6), 
							FIVE(5), 
							FOUR(4), 
							THREE(3), 
							TWO(2), 
							ONE(1), 
							ZERO(0), 
							NONE(-1);
							
		private final int score;
		private MoveScore (int score) {
			this.score = score;
		}
		
		public int getScore() {
			return score;
		}
	}

	public TicTacToeGame(IStrategy strategy, GameView view) {
		this.strategy = strategy;
		this.view = view;
		startNewGame();
	}
	
	public void startNewGame() {
		this.strategy = strategy.gimmeANewOne();
		gameState = new GameState();
		board = strategy.getBoard();
		view.restartGame();
	}

	public void makeCompleteMoveCycle(int playerPosition) {
		incrementMoveNumber(); 
		markTheirMove(playerPosition);
		makeOurMove();
		updateGameState();
	}
	
	public void markTheirMove(int playerMove) {
		markMove(playerMove, Board.THEIR_PLAYER_MARK); 
	}

	public void makeOurMove() {
		if (noWinnerYet()) {
			int position = makeMove(true);
			markMove(position, Board.OUR_PLAYER_MARK); 
		}
	}
	
	public int makeMove(boolean reporting) {
		int movePosition = strategy.makeMove(reporting);
		gameState.setLastMove(movePosition);
		return movePosition;
	}

	public void updateGameState() {
		// TODO: we should be able to do if/else's here, but we get test failures.
		/*
		 * Might have found a bug - if you change the sequential 
		 * if's to a series of if/else blocks (which ought to be equivalent), then GameStateTests.isDraw() fails - it wins when it shouldn't.  
		 * Perhaps it's marking both as a win and as a draw?
		 */
		if (weWonTheGame()) {
			gameState.setWeWon();
			view.weWonGame();
		} else if (theyWonTheGame()) {
			gameState.setTheyWon();
			view.theyWonGame();
		} else if (isDraw()) {
			gameState.setDraw();
			view.gameIsADraw();
		}
	}

	public void incrementMoveNumber() {
		gameState.incrementMovenumber();
	}

	private boolean isDraw() {
		return ( moveNumber() > MAX_NUMBER_OF_MOVES) ;
	}

	private boolean theyWonTheGame() {
		System.out.println("They won = " + wonTheGame(Board.THEIR_PLAYER_MARK, getWinningSize()));
		return wonTheGame(Board.THEIR_PLAYER_MARK, getWinningSize());
	}

	private boolean weWonTheGame() {
		System.out.println("We won = " + wonTheGame(Board.THEIR_PLAYER_MARK, getWinningSize()));
		return wonTheGame(Board.OUR_PLAYER_MARK, getWinningSize());
	}
	
	public boolean noWinnerYet() {
		return (!theyWonTheGame() && (!weWonTheGame()));
	}

	public boolean wonTheGame(int playerMark, SeriesSize winningSeriesSize) {
		return strategy.wonTheGame(playerMark, winningSeriesSize);
	}

	public void markMove(int position, int playerMark) {
		int row = Board.getRowCoordFor(position);
		int column = Board.getColumnCoordFor(position);
		board.setPosition(row, column, playerMark);
		view.drawMark(row, column, playerMark);
	}
	
	public void setBoard(int[] incomingBoardArray) {
		board.setBoardArray(incomingBoardArray);
	}

	public Board getBoard() {
		return board;
	}

	public int getPosition(int position) {
		return board.getPosition(position);
	}

	public int moveNumber() {
		return gameState.moveNumber();
	}

	public boolean positionIsAvailable(int playerMove) {
		return board.positionIsEmpty(playerMove);
	}

	public boolean positionIsAvailable(int row, int column) {
		return board.positionIsEmpty(row, column);
	}
	public boolean weHaveAWinner() {
		return (!noWinnerYet());
	}

	public int lastMove() {
		return gameState.lastMove();
	}

	public boolean inPlay() {
		return gameState.inPlay();
	}

	public boolean theyWon() {
		return gameState.theyWon();
	}

	public boolean weWon() {
		return gameState.weWon();
	}

	public boolean justStarted() {
		return gameState.justStarted();
	}

	public GameState getGameState() {
		return gameState;
	}
	
	public SeriesSize getWinningSize() {
		return winningSize;
	}
}
