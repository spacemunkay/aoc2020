public class Day3 {

	public static void main(String[] args) throws Exception {

		StringBuffer data = ReadURL.getWebData(3, "jason");
		System.out.println("first answer = " + treeCounter(data, 3, 1));
		System.out.println("second answer = " + (treeCounter(data, 1, 1) * treeCounter(data, 3, 1)
				* treeCounter(data, 5, 1) * treeCounter(data, 7, 1) * treeCounter(data, 1, 2)));
	}

	private static long treeCounter(StringBuffer data, int horiChange, int vertChange) {
		int horiPos = 0;
		int treeCount = 0;

		String[] lines = data.toString().split("\\n");
		for (int vertPos = 0; vertPos < lines.length; vertPos += vertChange) {
			if (lines[vertPos].charAt(horiPos % lines[vertPos].length()) == '#') {
				treeCount++;
			}
			horiPos += horiChange;
		}
		return treeCount;
	}
}
