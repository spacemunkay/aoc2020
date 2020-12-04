import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.*;

public class Day2 {

	static boolean part1 = false; // Toggle true to solve for part 1 and false to solve for part 2

	public static void main(String[] args) throws Exception {
		
		StringBuffer data = ReadURL.getWebData(2, "patrick");

		int count = 0;
		
		String[] lines = data.toString().split("\\n");
		for(String s: lines){
			if (parseLine(s)) {
				count++;
			}
		}
		System.out.println(count);
	}

	private static boolean parseLine(String line) {
		String[] split = line.split(" ");
		String[] limits = split[0].split("-");
		int num1 = Integer.parseInt(limits[0]);
		int num2 = Integer.parseInt(limits[1]);
		String letter = split[1].substring(0, 1);
		String password = split[2];
		return validPassword(num1, num2, letter, password);
	}

	private static boolean validPassword(int num1, int num2, String letter, String password) {
		if (part1) {
			Matcher m = Pattern.compile(letter).matcher(password);
			int matches = 0;
			while (m.find()) {
				matches++;
			}
			if (matches >= num1 && matches <= num2) {
				return true;
			}
			return false;
		} else {
			char c = letter.charAt(0);
			if (password.charAt(num1 - 1) == c && password.charAt(num2 - 1) != c
					|| password.charAt(num1 - 1) != c && password.charAt(num2 - 1) == c) {
				return true;
			}
			return false;
		}
	}

}
