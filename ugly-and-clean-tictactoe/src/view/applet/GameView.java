package view.applet;

public interface GameView {

	void restartGame();

	void drawMark(int row, int column, int playerMark);

	void weWonGame();

	void theyWonGame();

	void gameIsADraw();

}
