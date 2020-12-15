import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day10 {
	
	public static List<Long> buffer = new ArrayList<Long>();
	
	public static void main(String[] args) throws Exception {

		StringBuffer data = ReadURL.getWebData(10, "patrick");
		String dataString = data.toString();
		List<Integer> numbers = new ArrayList<Integer>();
		Scanner s = new Scanner(dataString);
		while (s.hasNextInt()) {
			numbers.add(s.nextInt());
		}
		s.close();
		numbers.add(0);
		numbers.sort(null);
		numbers.add(numbers.get(numbers.size() - 1) + 3);

		int oneJolts = 0;
		int threeJolts = 0;
		for (int i = 0; i < numbers.size() - 1; i++) {
			int diff = numbers.get(i + 1) - numbers.get(i);
			if (diff == 1) {
				oneJolts++;
			}
			if (diff == 3) {
				threeJolts++;
			}
		}

		System.out.println("first answer = " + (oneJolts * threeJolts));
		for (int i = 0; i < numbers.size(); i++) {
			buffer.add((long) -1);
		}
		System.out.println("second answer = " + findOptions(numbers, 0));
	}

	private static long findOptions(List<Integer> numbers, int idx) {
		long options = 0;
		
		// Check if answer is already stored in buffer
		if (buffer.get(idx) != -1) {
			return buffer.get(idx);
		}
		
		// Leaf nodes only count as themselves
		if (idx >= numbers.size() - 1) {
			buffer.set(idx, (long) 1);
			return 1;
		}
		
		// Include next highest value in numbers
		options += findOptions(numbers, idx + 1);

		// If idx is penultimate index in numbers, return
		if (idx >= numbers.size() - 2) {
			buffer.set(idx, options); // Set buffer to reduce computations
			return options;
		}
		// Otherwise, check if next next value is within 3 and add branch if so
		if (numbers.get(idx + 2) - numbers.get(idx) <= 3) {
			options += findOptions(numbers, idx + 2);
		}

		// If idx is antepenultimate index in numbers, return
		if (idx >= numbers.size() - 3) {
			buffer.set(idx, options); // Set buffer to reduce computations
			return options;
		}
		// Otherwise, check if next next next value is within 3 and add branch if so
		if (numbers.get(idx + 3) - numbers.get(idx) <= 3) {
			options += findOptions(numbers, idx + 3);
		}
		buffer.set(idx, options);  // Set buffer to reduce computations
		return options;
	}
}
