package model.patterns;

import model.gamestate.Board;

public class IndexListFactory {
	// TODO Yow. You think maybe these guys could use some tests for different
	// MAX_COLUMN and MAX_ROW values? :-;
	private static final int FIRST_SQUARE_OF_LAST_LOWER_BOARD_DIAG_UP_WITH_PENTE_ROOM = (Board.MAX_COLUMNS * Board.MAX_ROWS) - 5;
	private static final int FIRST_SQUARE_OF_FIRST_LOWER_BOARD_DIAG_UP_WITH_PENTE_ROOM = (Board.MAX_COLUMNS * (Board.MAX_COLUMNS - 1)) + 1;
	private static final int FIRST_SQUARE_OF_FIRST_UPPER_BOARD_DIAG_UP_WITH_PENTE_ROOM = Board.MAX_COLUMNS * 4;
	private static final int FIRST_SQUARE_OF_FIRST_LOWER_DIAG_DOWN_WITH_PENTE_ROOM = (Board.MAX_ROWS - 5)
			* Board.MAX_COLUMNS;

	public static final int DIAGONAL_UP_AFTER_INCREMENT = -(Board.MAX_COLUMNS - 1);
	public static final int DIAGONAL_UP_BEFORE_INCREMENT = (Board.MAX_COLUMNS - 1);
	public static final int DIAGONAL_DOWN_AFTER_INCREMENT = (Board.MAX_COLUMNS + 1);
	public static final int DIAGONAL_DOWN_BEFORE_INCREMENT = -(Board.MAX_COLUMNS + 1);
	public static final int VERTICAL_AFTER_INCREMENT = Board.MAX_COLUMNS;
	public static final int VERTICAL_BEFORE_INCREMENT = -Board.MAX_COLUMNS;
	public static final int HORIZONTAL_AFTER_INCREMENT = 1;
	public static final int HORIZONTAL_BEFORE_INCREMENT = -1;

	public IndexListGroup getAllIndexLists() {
		IndexListGroup listGroup = new IndexListGroup();

		listGroup = getIndexHorizontalRows(listGroup);
		listGroup = getIndexVerticalColumns(listGroup);
		listGroup = getIndexDiagonalDownsWithPentaRoom(listGroup);
		listGroup = getIndexDiagonalUps(listGroup);

		return listGroup;
	}

	public IndexListGroup getIndexHorizontalRows(IndexListGroup listGroup) {
		for (int row = 0; row < Board.MAX_ROWS; row++) {
			IndexList list = new IndexList();

			getAllSpacesInThisRow(row, list);
			listGroup.add(list);
		}
		return listGroup;
	}

	private void getAllSpacesInThisRow(int row, IndexList list) {
		int spaceIndex;
		for (int space = 0; space < Board.MAX_COLUMNS; space++) {
			spaceIndex = row * Board.MAX_COLUMNS + space;

			list.add(spaceIndex);
		}
	}

	public IndexListGroup getIndexVerticalColumns(IndexListGroup listGroup) {
		for (int column = 0; column < Board.MAX_COLUMNS; column++) {
			IndexList list = new IndexList();

			getAllSpacesInThisColumn(column, list);
			listGroup.add(list);
		}

		return listGroup;
	}

	private void getAllSpacesInThisColumn(int column, IndexList list) {
		int spaceIndex;
		for (int space = 0; space < Board.MAX_BOARD_SIZE; space = space
				+ Board.MAX_ROWS) {
			spaceIndex = column + space;

			list.add(spaceIndex);
		}
	}

	public IndexListGroup getIndexDiagonalDownsWithPentaRoom(
			IndexListGroup listGroup) {
		assembleLowerBoardDiagonalDownsWithPentaRoom(listGroup);
		assembleUpperBoardDiagonalDownsWithPentaRoom(listGroup);

		return listGroup;
	}

	public IndexListGroup getIndexDiagonalUps(IndexListGroup listGroup) {
		assembleUpperBoardDiagonalUps(listGroup);
		assembleLowerBoardDiagonalUps(listGroup);

		return listGroup;
	}

	private void assembleUpperBoardDiagonalDownsWithPentaRoom(
			IndexListGroup listGroup) {
		int currentDiagonalDownSize = 9;

		for (int diagonalGroup = 1; diagonalGroup < 6; diagonalGroup++) {
			IndexList list = new IndexList();

			getAllSpacesInThisUpperBoardDiagonalDown(currentDiagonalDownSize,
					diagonalGroup, list);
			listGroup.add(list);
			currentDiagonalDownSize--;
		}
	}

	private void getAllSpacesInThisUpperBoardDiagonalDown(
			int currentDiagonalDownSize, int diagonalGroup, IndexList list) {
		int spaceIndex;
		for (int space = 0; space < currentDiagonalDownSize; space++) {
			spaceIndex = diagonalGroup
					+ (space * DIAGONAL_DOWN_AFTER_INCREMENT);

			list.add(spaceIndex);
		}
	}

	private int assembleLowerBoardDiagonalDownsWithPentaRoom(
			IndexListGroup listGroup) {
		int currentDiagonalDownSize = 5;

		for (int diagonalGroup = FIRST_SQUARE_OF_FIRST_LOWER_DIAG_DOWN_WITH_PENTE_ROOM; diagonalGroup > -1; diagonalGroup = diagonalGroup
				- VERTICAL_AFTER_INCREMENT) {
			IndexList list = new IndexList();

			getAllSpacesInThisLowerBoardDiagonalDown(currentDiagonalDownSize,
					diagonalGroup, list);

			listGroup.add(list);
			currentDiagonalDownSize++;
		}
		return currentDiagonalDownSize;
	}

	private void getAllSpacesInThisLowerBoardDiagonalDown(
			int currentDiagonalDownSize, int diagonalGroup, IndexList list) {
		int spaceIndex;
		for (int space = 0; space < currentDiagonalDownSize; space++) {
			spaceIndex = diagonalGroup
					+ (space * DIAGONAL_DOWN_AFTER_INCREMENT);

			list.add(spaceIndex);
		}
	}

	private void assembleLowerBoardDiagonalUps(IndexListGroup listGroup) {
		int currentDiagonalUpSize = 9;

		for (int diagonalGroup = FIRST_SQUARE_OF_FIRST_LOWER_BOARD_DIAG_UP_WITH_PENTE_ROOM; diagonalGroup <= FIRST_SQUARE_OF_LAST_LOWER_BOARD_DIAG_UP_WITH_PENTE_ROOM; diagonalGroup++) {
			IndexList list = new IndexList();

			getAllSpacesInThisLowerBoardDiagonalUp(currentDiagonalUpSize,
					diagonalGroup, list);

			listGroup.add(list);
			currentDiagonalUpSize--;
		}

	}

	private void getAllSpacesInThisLowerBoardDiagonalUp(
			int currentDiagonalUpSize, int diagonalGroup, IndexList list) {
		int spaceIndex;
		for (int space = 0; space < currentDiagonalUpSize; space++) {
			spaceIndex = diagonalGroup - (space * DIAGONAL_UP_BEFORE_INCREMENT);

			list.add(spaceIndex);
		}
	}

	private int assembleUpperBoardDiagonalUps(IndexListGroup listGroup) {
		int currentDiagonalUpSize = 5;

		for (int diagonalGroup = FIRST_SQUARE_OF_FIRST_UPPER_BOARD_DIAG_UP_WITH_PENTE_ROOM; diagonalGroup < Board.MAX_BOARD_SIZE; diagonalGroup = diagonalGroup
				+ VERTICAL_AFTER_INCREMENT) {
			IndexList list = new IndexList();

			getAllSpacesInThisUpperBoardDiagonalUp(currentDiagonalUpSize,
					diagonalGroup, list);
			listGroup.add(list);
			currentDiagonalUpSize++;
		}
		return currentDiagonalUpSize;
	}

	private void getAllSpacesInThisUpperBoardDiagonalUp(
			int currentDiagonalUpSize, int diagonalGroup, IndexList list) {
		int spaceIndex;
		for (int space = 0; space < currentDiagonalUpSize; space++) {
			spaceIndex = diagonalGroup - (space * DIAGONAL_UP_BEFORE_INCREMENT);

			list.add(spaceIndex);
		}
	}

}
