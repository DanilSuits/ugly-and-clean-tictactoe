package view.applet;

import java.applet.Applet;
import java.awt.Image;
import java.awt.MediaTracker;
import java.net.URL;

import model.gamestate.Board;

//TODO Put all graphics in graphics folder
//TODO Import all graphic file references from properties file	

public class GameImages {
	private MediaTracker tracker;

	private Image xMark;
	private Image oMark;
	private Image filledOMark;
	private Image emptySquare;
	private Image winningImage;
	private Image losingImage;
	private Image yourTurnImage;
	private Image newGameImage;

	public GameImages(Applet applet) {
		tracker = new MediaTracker(applet);
		URL codeBaseUrl = applet.getCodeBase();

		xMark = applet.getImage(codeBaseUrl, "xMark.jpg");
		oMark = applet.getImage(codeBaseUrl, "oMark.jpg");
		filledOMark = applet.getImage(codeBaseUrl, "oMarkFilled.jpg");
		emptySquare = applet.getImage(codeBaseUrl, "emptySquare.jpg");
		winningImage = applet.getImage(codeBaseUrl, "win.jpg");
		losingImage = applet.getImage(codeBaseUrl, "lose.jpg");
		yourTurnImage = applet.getImage(codeBaseUrl, "yourTurn.jpg");
		newGameImage = applet.getImage(codeBaseUrl, "newgame.jpg");

		tracker.addImage(xMark, 0);
		tracker.addImage(oMark, 0);
		tracker.addImage(filledOMark, 0);
		tracker.addImage(emptySquare, 0);
		tracker.addImage(winningImage, 0);
		tracker.addImage(losingImage, 0);
		tracker.addImage(yourTurnImage, 0);
		tracker.addImage(newGameImage, 0);
	}

	public void awaitImageLoad() {
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
			// TODO: would want to inform the user in a friendlier way than an
			// exception.
			throw new RuntimeException(
					"interrupted exception while loading images");
		}
	}

	public Image getImageForPlayerMark(int playerMark) {
		Image image;

		switch (playerMark) {
		case Board.OUR_PLAYER_MARK:
			// NOTE: the view starts with a filled O, then changes it to a
			// non-filled O with a timer
			image = filledOMark;
			break;

		case Board.THEIR_PLAYER_MARK:
			image = xMark;
			break;

		default:
			image = emptySquare;
		}

		return image;
	}

	public MediaTracker getTracker() {
		return tracker;
	}

	public Image getXMark() {
		return xMark;
	}

	public Image getOMark() {
		return oMark;
	}

	public Image getFilledOMark() {
		return filledOMark;
	}

	public Image getEmptySquare() {
		return emptySquare;
	}

	public Image getWinningImage() {
		return winningImage;
	}

	public Image getLosingImage() {
		return losingImage;
	}

	public Image getYourTurnImage() {
		return yourTurnImage;
	}

	public Image getNewGameImage() {
		return newGameImage;
	}
}
