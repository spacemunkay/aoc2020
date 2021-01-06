import java.util.ArrayList;
import java.util.List;

public class Day17 {

	public static void main(String[] args) throws Exception {
		StringBuffer sb = ReadURL.getWebData(17, "jason");
		String data = sb.toString();
		List<List<List<Character>>> cube = createCube(data);
		List<List<List<List<Character>>>> tesseract = createHypercube(data);
		System.out.println("first answer = " + cycleMulti(cube, 6));
		System.out.println("second answer = " + cycleMulti4(tesseract, 6));

	}

	private static int cycleMulti4(List<List<List<List<Character>>>> tesseract, int cycles) {
		for (int i = 1; i <= cycles; i++) {
			tesseract = nextCycle4(tesseract);
		}
		return countActive4(tesseract);
	}

	private static List<List<List<List<Character>>>> nextCycle4(List<List<List<List<Character>>>> tesseract) {
		tesseract = enlargenTesseract(tesseract);
		List<List<List<List<Character>>>> newTess = new ArrayList<List<List<List<Character>>>>();
		for (int h = 0; h < tesseract.size(); h++) {
			List<List<List<Character>>> newCube = new ArrayList<List<List<Character>>>();
			for (int i = 0; i < tesseract.get(0).size(); i++) {
				List<List<Character>> newLayer = new ArrayList<List<Character>>();
				for (int j = 0; j < tesseract.get(0).get(0).size(); j++) {
					List<Character> newRow = new ArrayList<Character>();
					for (int k = 0; k < tesseract.get(0).get(0).get(0).size(); k++) {
						int neighbors = countNeighbors(tesseract, h, i, j, k);
						if (neighbors == 3 || (neighbors == 2 && tesseract.get(h).get(i).get(j).get(k) == '#')) {
							newRow.add('#');
						} else {
							newRow.add('.');
						}
					}
					newLayer.add(newRow);
				}
				newCube.add(newLayer);
			}
			newTess.add(newCube);
		}
		return newTess;
	}

	private static int countNeighbors(List<List<List<List<Character>>>> tesseract, int h, int i, int j, int k) {
		int sum = 0;
		List<List<Integer>> offsets = new ArrayList<List<Integer>>();
		offsets = getOffsets(h, i, j, k);
		for (List<Integer> offset : offsets) {
			sum += count(tesseract, offset.get(0), offset.get(1), offset.get(2), offset.get(3));
		}
		return sum;
	}

	private static int count(List<List<List<List<Character>>>> tesseract, int h, int i, int j, int k) {
		try {
			return tesseract.get(h).get(i).get(j).get(k) == '#' ? 1 : 0;
		} catch (IndexOutOfBoundsException e) {
			return 0;
		}
	}

	private static List<List<Integer>> getOffsets(int w, int x, int y, int z) {
		List<List<Integer>> offsets = new ArrayList<List<Integer>>();
		for (int h = w - 1; h <= w + 1; h++)
			for (int i = x - 1; i <= x + 1; i++) {
				for (int j = y - 1; j <= y + 1; j++) {
					for (int k = z - 1; k <= z + 1; k++) {
						if (h != w || i != x || j != y || k != z) {
							List<Integer> coords = new ArrayList<Integer>();
							coords.add(h);
							coords.add(i);
							coords.add(j);
							coords.add(k);
							offsets.add(coords);
						}
					}
				}
			}
		return offsets;
	}

	private static List<List<List<List<Character>>>> enlargenTesseract(List<List<List<List<Character>>>> tesseract) {
		List<List<List<List<Character>>>> biggerTess = new ArrayList<List<List<List<Character>>>>();
		for (int h = -1; h < tesseract.size() + 1; h++) {
			List<List<List<Character>>> biggerCube = new ArrayList<List<List<Character>>>();
			for (int i = -1; i < tesseract.get(0).size() + 1; i++) {
				List<List<Character>> biggerLayer = new ArrayList<List<Character>>();
				for (int j = -1; j < tesseract.get(0).get(0).size() + 1; j++) {
					List<Character> biggerRow = new ArrayList<Character>();
					for (int k = -1; k < tesseract.get(0).get(0).get(0).size() + 1; k++) {
						try {
							biggerRow.add(tesseract.get(h).get(i).get(j).get(k));
						} catch (IndexOutOfBoundsException e) {
							biggerRow.add('.');
						}
					}
					biggerLayer.add(biggerRow);
				}
				biggerCube.add(biggerLayer);
			}
			biggerTess.add(biggerCube);
		}
		return biggerTess;
	}

	private static int countActive4(List<List<List<List<Character>>>> tesseract) {
		int sum = 0;
		for (List<List<List<Character>>> cube : tesseract) {
			sum += countActive(cube);
		}
		return sum;
	}

	private static List<List<List<List<Character>>>> createHypercube(String data) {
		List<List<List<List<Character>>>> tesseract = new ArrayList<List<List<List<Character>>>>();
		List<List<List<Character>>> cube = createCube(data);
		tesseract.add(cube);
		return tesseract;
	}

	private static int cycleMulti(List<List<List<Character>>> cube, int cycles) {
		for (int i = 1; i <= cycles; i++) {
			cube = nextCycle(cube);
		}
		return countActive(cube);
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
		int sum = 0;
		List<List<Integer>> offsets = new ArrayList<List<Integer>>();
		offsets = getOffsets(i, j, k);
		for (List<Integer> offset : offsets) {
			sum += count(cube, offset.get(0), offset.get(1), offset.get(2));
		}
		return sum;
	}

	private static List<List<Integer>> getOffsets(int x, int y, int z) {
		List<List<Integer>> offsets = new ArrayList<List<Integer>>();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				for (int k = z - 1; k <= z + 1; k++) {
					if (i != x || j != y || k != z) {
						List<Integer> coords = new ArrayList<Integer>();
						coords.add(i);
						coords.add(j);
						coords.add(k);
						offsets.add(coords);
					}
				}
			}
		}
		return offsets;
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
