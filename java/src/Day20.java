import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20 {

	public static void main(String[] args) throws Exception {
		StringBuffer sb = ReadURL.getWebData(20, "patrick");
		String data = sb.toString();
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
		Map<Integer, List<String>> tiles = mapTiles(data);
		System.out.println("first answer = " + findCornerProduct(tiles));
	}

	private static long findCornerProduct(Map<Integer, List<String>> tiles) {
		long product = 1;
		for (Integer key : tiles.keySet()) {
			if (countMatches(key, tiles) == 2) {
				product *= key;
			}
		}
		return product;
	}

	private static int countMatches(Integer key, Map<Integer, List<String>> tiles) {
		int count = 0;
		for (Integer tile : tiles.keySet()) {
			if (key != tile && matchesEdge(key, tile, tiles)) {
				count++;
			}
		}
		return count;
	}

	private static boolean matchesEdge(Integer key, Integer tile, Map<Integer, List<String>> tiles) {
		List<String> firstTile = tiles.get(key);
		List<String> secondTile = tiles.get(tile);
		for (String firstTileEdge : firstTile) {
			for (String secondTileEdge : secondTile) {
				String reverse = new StringBuilder(secondTileEdge).reverse().toString();
				if (firstTileEdge.equals(secondTileEdge) || firstTileEdge.equals(reverse)) {
					return true;
				}
			}
		}
		return false;
	}

	private static Map<Integer, List<String>> mapTiles(String data) {
		Map<Integer, List<String>> tiles = new HashMap<Integer, List<String>>();
		String[] tileStringsWithHeaders = data.replace("\r", "").split("\n\n");
		for (String tswh : tileStringsWithHeaders) {
			Matcher m = Pattern.compile("\\d+").matcher(tswh);
			m.find();
			Integer tileNumber = Integer.parseInt(m.group());
			String[] tileLines = tswh.split(":\n")[1].split("\n");
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
		return tiles;
	}

}
