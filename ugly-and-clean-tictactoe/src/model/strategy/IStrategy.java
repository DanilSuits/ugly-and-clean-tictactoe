package model.strategy;

import model.gamestate.Board;
import model.gamestate.Board.SeriesSize;

public interface IStrategy {
	public IStrategy newInstance();

	public Board getBoard();

	public int makeMove(boolean reporting);

	public boolean wonTheGame(int playerMark, SeriesSize winningSeriesSize);
}