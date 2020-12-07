import java.util.HashMap;
import java.util.Map;

public class Day6 {

	public static void main(String[] args) throws Exception {

		StringBuffer data = ReadURL.getWebData(6, "patrick");
		String[] answerSets = data.toString().split("\\n\\n");

		int sumAny = 0, sumEvery = 0;
		for (String answer : answerSets) {
			char[] charArray = answer.toCharArray();
			Map<Character, Integer> chars = new HashMap<Character, Integer>();
			for (char ch : charArray) {
				if (ch != '\n') {
					if (chars.containsKey(ch)) {
						chars.put(ch, chars.get(ch) + 1);
					} else {
						chars.put(ch, 1);
					}
				}
			}
			sumAny += chars.size();
			int numAnswers = answer.split("\n").length;
			for (char ch : chars.keySet()) {
				if (chars.get(ch) == numAnswers) {
					sumEvery++;
				}
			}
		}
		System.out.println("first answer = " + sumAny);
		System.out.println("second answer = " + sumEvery);

	}

}
