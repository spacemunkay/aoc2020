import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day8 {

	public static void main(String[] args) throws Exception {

		StringBuffer data = ReadURL.getWebData(8, "patrick");
		String[] answerSets = data.toString().split("\\n");

		System.out.println("first answer = " + infiniteLoop(answerSets)[0]);
		System.out.println("second answer = " + stopLoop(answerSets));

	}

	private static int stopLoop(String[] answerSets) {
		for (int i = 0; i < answerSets.length; i++) {
			String[] pr = answerSets[i].split(" ");
			if (pr[0].equals("jmp")) {
				String[] copy = Arrays.copyOf(answerSets, answerSets.length);
				copy[i] = "nop " + pr[1];
				int[] check = infiniteLoop(copy);
				if (check[1] >= copy.length) {
					return check[0];
				}
			} else if (pr[0].equals("nop")) {
				String[] copy = Arrays.copyOf(answerSets, answerSets.length);
				copy[i] = "jmp " + pr[1];
				int[] check = infiniteLoop(copy);
				if (check[1] >= copy.length) {
					return check[0];
				}
			}
		}
		return -1;
	}

	private static int[] infiniteLoop(String[] answerSets) {
		int acc = 0;
		int idx = 0;
		Set<Integer> indexes = new HashSet<Integer>();
		while (indexes.add(idx) && idx < answerSets.length) {
			String[] pair = answerSets[idx].split(" ");
			String instruction = pair[0];
			int x = Integer.parseInt(pair[1]);
			switch (instruction) {
			case "jmp":
				idx += x;
				break;
			case "acc":
				acc += x;
				idx++;
				break;
			case "nop":
				idx++;
				break;
			}
		}
		int[] pair = { acc, idx };
		return pair;
	}

}
