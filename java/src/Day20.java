import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20 {

	public static void main(String[] args) throws Exception {
		//StringBuffer sb = ReadURL.getWebData(20, "jason");
		//String data = sb.toString();
		String test = "Tile 2311:\r\n" + 
				"..##.#..#.\r\n" + 
				"##..#.....\r\n" + 
				"#...##..#.\r\n" + 
				"####.#...#\r\n" + 
				"##.##.###.\r\n" + 
				"##...#.###\r\n" + 
				".#.#.#..##\r\n" + 
				"..#....#..\r\n" + 
				"###...#.#.\r\n" + 
				"..###..###\r\n" + 
				"\r\n" + 
				"Tile 1951:\r\n" + 
				"#.##...##.\r\n" + 
				"#.####...#\r\n" + 
				".....#..##\r\n" + 
				"#...######\r\n" + 
				".##.#....#\r\n" + 
				".###.#####\r\n" + 
				"###.##.##.\r\n" + 
				".###....#.\r\n" + 
				"..#.#..#.#\r\n" + 
				"#...##.#..\r\n" + 
				"\r\n" + 
				"Tile 1171:\r\n" + 
				"####...##.\r\n" + 
				"#..##.#..#\r\n" + 
				"##.#..#.#.\r\n" + 
				".###.####.\r\n" + 
				"..###.####\r\n" + 
				".##....##.\r\n" + 
				".#...####.\r\n" + 
				"#.##.####.\r\n" + 
				"####..#...\r\n" + 
				".....##...\r\n" + 
				"\r\n" + 
				"Tile 1427:\r\n" + 
				"###.##.#..\r\n" + 
				".#..#.##..\r\n" + 
				".#.##.#..#\r\n" + 
				"#.#.#.##.#\r\n" + 
				"....#...##\r\n" + 
				"...##..##.\r\n" + 
				"...#.#####\r\n" + 
				".#.####.#.\r\n" + 
				"..#..###.#\r\n" + 
				"..##.#..#.\r\n" + 
				"\r\n" + 
				"Tile 1489:\r\n" + 
				"##.#.#....\r\n" + 
				"..##...#..\r\n" + 
				".##..##...\r\n" + 
				"..#...#...\r\n" + 
				"#####...#.\r\n" + 
				"#..#.#.#.#\r\n" + 
				"...#.#.#..\r\n" + 
				"##.#...##.\r\n" + 
				"..##.##.##\r\n" + 
				"###.##.#..\r\n" + 
				"\r\n" + 
				"Tile 2473:\r\n" + 
				"#....####.\r\n" + 
				"#..#.##...\r\n" + 
				"#.##..#...\r\n" + 
				"######.#.#\r\n" + 
				".#...#.#.#\r\n" + 
				".#########\r\n" + 
				".###.#..#.\r\n" + 
				"########.#\r\n" + 
				"##...##.#.\r\n" + 
				"..###.#.#.\r\n" + 
				"\r\n" + 
				"Tile 2971:\r\n" + 
				"..#.#....#\r\n" + 
				"#...###...\r\n" + 
				"#.#.###...\r\n" + 
				"##.##..#..\r\n" + 
				".#####..##\r\n" + 
				".#..####.#\r\n" + 
				"#..#.#..#.\r\n" + 
				"..####.###\r\n" + 
				"..#.#.###.\r\n" + 
				"...#.#.#.#\r\n" + 
				"\r\n" + 
				"Tile 2729:\r\n" + 
				"...#.#.#.#\r\n" + 
				"####.#....\r\n" + 
				"..#.#.....\r\n" + 
				"....#..#.#\r\n" + 
				".##..##.#.\r\n" + 
				".#.####...\r\n" + 
				"####.#.#..\r\n" + 
				"##.####...\r\n" + 
				"##..#.##..\r\n" + 
				"#.##...##.\r\n" + 
				"\r\n" + 
				"Tile 3079:\r\n" + 
				"#.#.#####.\r\n" + 
				".#..######\r\n" + 
				"..#.......\r\n" + 
				"######....\r\n" + 
				"####.#..#.\r\n" + 
				".#...#.##.\r\n" + 
				"#.#####.##\r\n" + 
				"..#.###...\r\n" + 
				"..#.......\r\n" + 
				"..#.###...";
		Map<Integer, Tile> tiles = mapTiles(test);
		for (Tile tile : tiles.values()) {
			System.out.println("Tile: " + tile.id);
			for(char[] line : tile.rows) {
				System.out.println(new String(line));
			}
			System.out.println("Borders: " + tile.getBorders());
		}
		System.out.println("first answer = " + findCornerProduct(tiles));
		//System.out.println("second answer = " + part2(data));
	}

	private static String part2(Map<Integer, Tile> tiles) {
		List<Tile> orderedTiles = orderTiles(tiles);
		String image = removeBorders(orderedTiles);
		image = removeMonsters(image);
		return countWaves(image);
	}

	// Not implemented
	private static String countWaves(String image) {
		// TODO Auto-generated method stub
		return null;
	}

	// Not implemented
	private static String removeMonsters(String image) {
		// TODO Auto-generated method stub
		return null;
	}

	// Not implemented
	private static String removeBorders(List<Tile> orderedTiles) {
		// TODO Auto-generated method stub
		return null;
	}

	// Not implemented
	private static List<Tile> orderTiles(Map<Integer, Tile> tiles) {
		// TODO Auto-generated method stub
		return null;
	}


	private static class Tile {
		private int id;
		private char[][] rows;
		private char[] top;
		private char[] left;
		private char[] right;
		private char[] bottom;
		
		public Tile(int id, String tileChars) {
			this.id = id;
			rows = parseRows(tileChars);
			setSides(rows);
		}
		
		public List<String> getBorders() {
			List<String> borders = new ArrayList<String>();
			borders.add(new String(top));
			borders.add(new String(left));
			borders.add(new String(right));
			borders.add(new String(bottom));
			return borders;
		}

		private void setSides(char[][] rows) {
			this.top = rows[0];
			this.bottom = rows[rows.length - 1];
			char[] rt = new char[rows[0].length], lft = new char[rows[0].length];
			for (int i = 0; i < rows.length; i++) {
				rt[i] = rows[i][0];
				lft[i] = rows[i][rows[i].length - 1];
			}
			this.right = rt;
			this.left = lft;
		}

		private char[][] parseRows(String rows) {
			String[] splitRows = rows.split("\n");
			char[][] retRows = new char[splitRows.length][];
			for (int i = 0; i < splitRows.length; i++) {
				retRows[i] = splitRows[i].toCharArray();
			}
			return retRows;
		}
		
		private void rotate() {
			if (rows.length == 0 || rows[0].length == 0) {
				System.out.println("Nothing to rotate");
				return;
			}
			char[][] rotatedTile = new char[rows.length][rows[0].length];
			for (int i = 0; i < rows.length; i++) {
				for (int j = 0; j < rows[i].length; j++) {
					int col = rows.length - 1 - i;
					int row = j;
				    rotatedTile[row][col] = rows[i][j];
				}
		    }
			rows = rotatedTile;
			setSides(rows);
		}
		
		private void flip() {
			if (rows.length == 0 || rows[0].length == 0) {
				System.out.println("Nothing to flip");
				return;
			}
			char[][] flippedTile = new char[rows.length][rows[0].length];
		    for (int i = 0; i < rows.length; i++) {
		        for (int j = 0; j < rows[i].length; j++) {
		            flippedTile[i][j] = rows[i][rows[i].length - 1 - j];
		        }
		    }
		    rows = flippedTile;
		    setSides(rows);
		}
		
		private void removeBorders() {
			char[][] borderless = new char[rows.length - 2][rows[0].length - 2];
		    for (int i = 1; i < rows.length - 1; i++) {
		        for (int j = 1; j < rows[i].length - 1; j++) {
		            borderless[i - 1][j - 1] = rows[i][j];
		        }
		    }
		    rows = borderless;
		    setSides(rows);
		}
	}

	private static long findCornerProduct(Map<Integer, Tile> tiles) {
		long product = 1;
		for (Integer key : tiles.keySet()) {
			if (countMatches(key, tiles) == 2) {
				product *= key;
			}
		}
		return product;
	}

	private static int countMatches(Integer key, Map<Integer, Tile> tiles) {
		int count = 0;
		for (Integer tile : tiles.keySet()) {
			if (key != tile && matchesEdge(key, tile, tiles)) {
				count++;
			}
		}
		return count;
	}

	private static boolean matchesEdge(Integer key, Integer tile, Map<Integer, Tile> tiles) {
		Tile firstTile = tiles.get(key);
		Tile secondTile = tiles.get(tile);
		for (String firstTileEdge : firstTile.getBorders()) {
			for (String secondTileEdge : secondTile.getBorders()) {
				String reverse = new StringBuilder(secondTileEdge).reverse().toString();
				if (firstTileEdge.equals(secondTileEdge) || firstTileEdge.equals(reverse)) {
					return true;
				}
			}
		}
		return false;
	}

	private static Map<Integer, Tile> mapTiles(String data) {
		Map<Integer, Tile> tiles = new HashMap<Integer, Tile>();
		String[] tileStringsWithHeaders = data.replace("\r", "").split("\n\n");
		for (String tswh : tileStringsWithHeaders) {
			Matcher m = Pattern.compile("\\d+").matcher(tswh);
			m.find();
			Integer tileNumber = Integer.parseInt(m.group());
			String tileLines = tswh.split(":\n")[1];
			tiles.put(tileNumber, new Tile(tileNumber, tileLines));
		}
		
		/**
			List<String> borders = new ArrayList<String>();
			borders.add(tileLines[0]);
			StringBuilder lb = new StringBuilder(), rb = new StringBuilder();
			for (int i = 0; i < tileLines.length; i++) {
				lb.append(tileLines[i].charAt(0));
				rb.append(tileLines[i].charAt(tileLines[i].length() - 1));
			}
			borders.add(lb.toString());
			borders.add(rb.toString());
			borders.add(tileLines[tileLines.length - 1]);
			tiles.put(tileNumber, borders);
		}
		**/
		return tiles;
	}

}
