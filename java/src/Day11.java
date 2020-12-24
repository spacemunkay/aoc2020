import java.util.Scanner;

public class Day11 {
	private static char[][] beforeGrid;
	private static char[][] afterGrid;

	public static void main(String[] args) throws Exception {
		StringBuffer data = ReadURL.getWebData(11, "jason");
		String dataString = data.toString();
		
		System.out.println("first answer = " + getAnswer(dataString, false));
		System.out.println("second answer = " + getAnswer(dataString, true));
	}

	private static int getAnswer(String dataString, boolean secondTime) {
		Scanner in = new Scanner(dataString);
		int cols = in.nextLine().length();
		int rows = dataString.length() / cols;
		in.close();

		Scanner s = new Scanner(dataString);
		// Initialize and display starting grid from text file
		beforeGrid = new char[rows][cols];
		for (int i = 0; i < rows; i++) {
			String line = s.nextLine();
			for (int j = 0; j < cols; j++) {
				beforeGrid[i][j] = line.charAt(j);
			}
		}
		afterGrid = new char[rows][cols];
		s.close();
		while (generateRound(rows, cols, secondTime)) {
		} // while grid changes
		int seatsTaken = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (beforeGrid[i][j] == '#') {
					seatsTaken++;
				}
			}
		}
		return seatsTaken;
	}

	private static boolean generateRound(int rows, int cols, boolean secondTime) {
		// Build afterGrid
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				updateSeat(i, j, rows, cols, secondTime);
			}
		}

		boolean changed = false;
		// Check if grid has changed
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (beforeGrid[i][j] != afterGrid[i][j]) {
					changed = true;
					beforeGrid[i][j] = afterGrid[i][j];
				}
			}
		}
		return changed;
	}

	private static void updateSeat(int r, int c, int rows, int cols, boolean secondTime) {
		char ch = beforeGrid[r][c];
		int neighbors = secondTime ? checkSight(r, c, rows, cols) : checkAdjacent(r, c, rows, cols);
		if (ch == 'L' && neighbors == 0) {
			afterGrid[r][c] = '#';
		} else if (ch == '#' && neighbors >= (secondTime ? 5 : 4)) {
			afterGrid[r][c] = 'L';
		} else {
			afterGrid[r][c] = beforeGrid[r][c];
		}

	}

	private static int checkSight(int r, int c, int m, int n) {
		int seatedNeighbors = 0;
		int x = 1;
		while (r - x >= 0) {
			char ch = beforeGrid[r - x][c];
			if (ch == '#') {
				seatedNeighbors++;
				break;
			}
			if (ch == 'L') {
				break;
			}
			x++;
		}
		x = 1;
		while (r - x >= 0 && c - x >= 0) {
			char ch = beforeGrid[r - x][c - x];
			if (ch == '#') {
				seatedNeighbors++;
				break;
			}
			if (ch == 'L') {
				break;
			}
			x++;
		}
		x = 1;
		while (r - x >= 0 && c + x < n) {
			char ch = beforeGrid[r - x][c + x];
			if (ch == '#') {
				seatedNeighbors++;
				break;
			}
			if (ch == 'L') {
				break;
			}
			x++;
		}
		x = 1;
		while (r + x < m) {
			char ch = beforeGrid[r + x][c];
			if (ch == '#') {
				seatedNeighbors++;
				break;
			}
			if (ch == 'L') {
				break;
			}
			x++;
		}
		x = 1;
		while (r + x < m && c - x >= 0) {
			char ch = beforeGrid[r + x][c - x];
			if (ch == '#') {
				seatedNeighbors++;
				break;
			}
			if (ch == 'L') {
				break;
			}
			x++;
		}
		x = 1;
		while (r + x < m && c + x < n) {
			char ch = beforeGrid[r + x][c + x];
			if (ch == '#') {
				seatedNeighbors++;
				break;
			}
			if (ch == 'L') {
				break;
			}
			x++;
		}
		x = 1;
		while (c - x >= 0) {
			char ch = beforeGrid[r][c - x];
			if (ch == '#') {
				seatedNeighbors++;
				break;
			}
			if (ch == 'L') {
				break;
			}
			x++;
		}
		x = 1;
		while (c + x < n) {
			char ch = beforeGrid[r][c + x];
			if (ch == '#') {
				seatedNeighbors++;
				break;
			}
			if (ch == 'L') {
				break;
			}
			x++;
		}
		return seatedNeighbors;
	}

	private static int checkAdjacent(int r, int c, int m, int n) {
		int seatedNeighbors = 0;
		if (r - 1 >= 0 && beforeGrid[r - 1][c] == '#') {
			seatedNeighbors++;
		}
		if (r - 1 >= 0 && c - 1 >= 0 && beforeGrid[r - 1][c - 1] == '#') {
			seatedNeighbors++;
		}
		if (r - 1 >= 0 && c + 1 < n && beforeGrid[r - 1][c + 1] == '#') {
			seatedNeighbors++;
		}
		if (r + 1 < m && beforeGrid[r + 1][c] == '#') {
			seatedNeighbors++;
		}
		if (r + 1 < m && c - 1 >= 0 && beforeGrid[r + 1][c - 1] == '#') {
			seatedNeighbors++;
		}
		if (r + 1 < m && c + 1 < n && beforeGrid[r + 1][c + 1] == '#') {
			seatedNeighbors++;
		}
		if (c - 1 >= 0 && beforeGrid[r][c - 1] == '#') {
			seatedNeighbors++;
		}
		if (c + 1 < n && beforeGrid[r][c + 1] == '#') {
			seatedNeighbors++;
		}
		return seatedNeighbors;
	}

}
