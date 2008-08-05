package view.applet;

public interface GameView {

	void restartGame();

	void drawMark(int row, int column, int playerMark);

	void computerWonGame();

	void humanComputerWonGame();

	void gameIsADraw();

}
