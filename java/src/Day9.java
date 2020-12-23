import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day9 {

	public static void main(String[] args) throws Exception {

		StringBuffer data = ReadURL.getWebData(9, "jason");
		String dataString = data.toString();

		long firstAnswer = checkSums(dataString);
		System.out.println("first answer = " + firstAnswer);

		long secondAnswer = contiguousSum(firstAnswer, dataString);
		System.out.println("second answer = " + secondAnswer);

	}

	private static long contiguousSum(long firstAnswer, String dataString) {
		Scanner s = new Scanner(dataString);
		List<Long> sumBuffer = new ArrayList<Long>();

		while (s.hasNextLong()) {
			sumBuffer.add(s.nextLong());
		}
		s.close();
		return findRange(sumBuffer, firstAnswer);
	}

	private static long findRange(List<Long> sumBuffer, long firstAnswer) {
		int sum = 0;
		for (int i = 0; i < sumBuffer.size(); i++) {
			sum += sumBuffer.get(i);
			if (sum == firstAnswer) {
				List<Long> range = new ArrayList<Long>();
				for (int k = 0; k <= i; k++) {
					range.add(sumBuffer.get(k));
				}
				range.sort(null);
				return range.get(0) + range.get(range.size() - 1);
			}
			if (sum > firstAnswer) {
				sumBuffer.remove(0);
				return findRange(sumBuffer, firstAnswer);
			}
		}
		return -1;
	}

	private static long checkSums(String dataString) {
		Scanner s = new Scanner(dataString);
		List<Long> sumBuffer = new ArrayList<Long>();

		for (int i = 0; i < 25; i++) {
			sumBuffer.add(s.nextLong());
		}

		while (s.hasNextLong()) {
			long next = s.nextLong();
			if (sumsUp(sumBuffer, next)) {
				sumBuffer.remove(0);
				sumBuffer.add(next);
			} else {
				s.close();
				return next;
			}
		}
		s.close();
		return -1;
	}

	private static boolean sumsUp(List<Long> sumBuffer, long next) {
		for (long i : sumBuffer) {
			for (long j : sumBuffer) {
				if (i != j && i + j == next) {
					return true;
				}
			}
		}
		return false;
	}
}
