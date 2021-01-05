import java.util.ArrayList;
import java.util.List;

public class Day17 {

	public static void main(String[] args) throws Exception {
		//StringBuffer sb = ReadURL.getWebData(17, "patrick");
		//String data = sb.toString();
		String test = ".#.\n" + "..#\n" + "###";
		String single = "#";
		String triple = "###\n###\n###\n\n###\n#.#\n###\n\n###\n###\n###";
		List<List<List<Character>>> cube = createCube(test);
		cube = cycleMulti(cube, 6);

	}

	private static List<List<List<Character>>> cycleMulti(List<List<List<Character>>> cube, int cycles) {
		System.out.println("Before any cycles:\n");
		printCube(cube);
		for (int i = 1; i <= cycles; i++) {
			System.out.println("After " + i + " cycles:\n");
			cube = nextCycle(cube);
			System.out.println(countActive(cube));
		}
		return cube;
	}

	private static int countActive(List<List<List<Character>>> cube) {
		int sum = 0;
		for (int i = 0; i < cube.size(); i++) {
			for (int j = 0; j < cube.get(i).size(); j++) {
				for (int k = 0; k < cube.get(i).get(j).size(); k++) {
					if (cube.get(i).get(j).get(k) == '#') {
						sum++;
					}
				}
			}
		}
		return sum;
	}

	private static List<List<List<Character>>> nextCycle(List<List<List<Character>>> cube) {
		cube = enlargenCube(cube);
		List<List<List<Character>>> newCube = new ArrayList<List<List<Character>>>();
		for (int i = 0; i < cube.size(); i++) {
			List<List<Character>> newLayer = new ArrayList<List<Character>>();
			for (int j = 0; j < cube.get(0).size(); j++) {
				List<Character> newRow = new ArrayList<Character>();
				for (int k = 0; k < cube.get(0).get(0).size(); k++) {
					int neighbors = countNeighbors(cube, i, j, k);
					if (neighbors == 3 || (neighbors == 2 && cube.get(i).get(j).get(k) == '#')) {
						newRow.add('#');
					} else {
						newRow.add('.');
					}
				}
				newLayer.add(newRow);
			}
			newCube.add(newLayer);
		}
		return newCube;
	}

	private static int countNeighbors(List<List<List<Character>>> cube, int i, int j, int k) {
		return count(cube, i, j, k - 1) +
				count(cube, i, j, k + 1) +
				count(cube, i, j - 1, k) +
				count(cube, i, j - 1, k - 1) +
				count(cube, i, j - 1, k + 1) +
				count(cube, i, j + 1, k) +
				count(cube, i, j + 1, k - 1) +
				count(cube, i, j + 1, k + 1) +
				count(cube, i - 1, j, k) +
				count(cube, i - 1, j, k - 1) +
				count(cube, i - 1, j, k + 1) +
				count(cube, i - 1, j - 1, k) +
				count(cube, i - 1, j - 1, k - 1) +
				count(cube, i - 1, j - 1, k + 1) +
				count(cube, i - 1, j + 1, k) +
				count(cube, i - 1, j + 1, k - 1) +
				count(cube, i - 1, j + 1, k + 1) +
				count(cube, i + 1, j, k) +
				count(cube, i + 1, j, k - 1) +
				count(cube, i + 1, j, k + 1) +
				count(cube, i + 1, j - 1, k) +
				count(cube, i + 1, j - 1, k - 1) +
				count(cube, i + 1, j - 1, k + 1) +
				count(cube, i + 1, j + 1, k) +
				count(cube, i + 1, j + 1, k - 1) +
				count(cube, i + 1, j + 1, k + 1);
	}

	private static int count(List<List<List<Character>>> cube, int i, int j, int k) {
		try {
			return cube.get(i).get(j).get(k) == '#' ? 1 : 0;
		} catch (IndexOutOfBoundsException e) {
			return 0;
		}
	}

	private static List<List<List<Character>>> enlargenCube(List<List<List<Character>>> cube) {
		List<List<List<Character>>> biggerCube = new ArrayList<List<List<Character>>>();
		for (int i = -1; i < cube.size() + 1; i++) {
			List<List<Character>> biggerLayer = new ArrayList<List<Character>>();
			for (int j = -1; j < cube.get(0).size() + 1; j++) {
				List<Character> biggerRow = new ArrayList<Character>();
				for (int k = -1; k < cube.get(0).get(0).size() + 1; k++) {
					try {
						biggerRow.add(cube.get(i).get(j).get(k));
					} catch (IndexOutOfBoundsException e) {
						biggerRow.add('.');
					}
				}
				biggerLayer.add(biggerRow);
			}
			biggerCube.add(biggerLayer);
		}
		return biggerCube;

	}

	private static void printCube(List<List<List<Character>>> cube) {
		for (int i = 0; i < cube.size(); i++) {
			System.out.println("z = " + (i - cube.size() / 2));
			for (int j = 0; j < cube.get(0).size(); j++) {
				for (int k = 0; k < cube.get(0).get(0).size(); k++) {
					System.out.print(cube.get(i).get(j).get(k));
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	private static List<List<List<Character>>> createCube(String data) {
		List<List<List<Character>>> cube = new ArrayList<List<List<Character>>>();
		String[] layers = data.split("\n\n");
		for (String layer : layers) {
			String[] rows = layer.split("\n");
			List<List<Character>> l = new ArrayList<List<Character>>();
			for (String row : rows) {
				char[] cells = row.toCharArray();
				List<Character> r = new ArrayList<Character>();
				for (char cell : cells) {
					r.add(cell);
				}
				l.add(r);
			}
			cube.add(l);
		}
		return cube;
	}
}
