import java.util.HashMap;
import java.util.Map;

public class Day15 {

	private static Map<Integer, Integer[]> guesses;
	private static final int FIRST_ATTEMPT = 2020;
	private static final int SECOND_ATTEMPT = 30000000;

	public static void main(String[] args) throws Exception {
		StringBuffer sb = ReadURL.getWebData(15, "jason");
		String data = sb.toString().trim();

		String[] startNumbers = data.split(",");

		System.out.println("first answer = " + startGame(startNumbers, FIRST_ATTEMPT));
		System.out.println("second answer = " + startGame(startNumbers, SECOND_ATTEMPT));

	}

	private static int startGame(String[] startNumbers, int numGuesses) {
		guesses = new HashMap<Integer, Integer[]>();
		for (int i = 0; i < startNumbers.length; i++) {
			Integer[] a = { 0, i + 1 };
			guesses.put(Integer.parseInt(startNumbers[i]), a);
		}
		return memoryGame(0, guesses.size() + 1, numGuesses);
	}

	private static int memoryGame(int currGuess, int acc, int numGuesses) {
		int nextGuess = currGuess;
		while (acc <= numGuesses) {
			currGuess = nextGuess;
			if (guesses.get(currGuess) == null) {
				Integer[] a = { 0, acc };
				guesses.put(currGuess, a);
				nextGuess = 0;
				acc++;
			} else {
				Integer[] a = guesses.get(currGuess);
				Integer[] newArr = new Integer[2];
				newArr[0] = a[1];
				newArr[1] = acc;
				guesses.put(currGuess, newArr);
				nextGuess = acc - a[1];
				acc++;
			}
		}
		return currGuess;

	}

}
