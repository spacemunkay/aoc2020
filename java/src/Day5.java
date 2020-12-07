import java.util.HashSet;
import java.util.Set;

public class Day5 {

	public static void main(String[] args) throws Exception {
		
		StringBuffer data = ReadURL.getWebData(5, "patrick");
		String[] passes = data.toString().split("\\n");
		
		int seat, max = 0, min = 1024;
		Set<Integer> s = new HashSet<Integer>();
		for (String pass : passes) {
			seat = getSeat(pass, s);
			max = seat > max ? seat : max;
			min = seat < min ? seat : min;
		}
		System.out.println("first answer = " + max);
		for (int i = min; i < max; i ++) {
			if (!s.contains(i)) {
				System.out.println("second answer = " + i);
			}
		}
		

	}

	private static Integer getSeat(String pass, Set<Integer> s) {
		int row = 0, col = 0, len = pass.length();
		char[] c = pass.toCharArray();
		for (int i = 0 ; i < len; i++) {
			if (c[i] == 'B') {
				row += Math.pow(2, 6-i);
			}
			if (c[i] == 'R') {
				col += Math.pow(2, 9-i);
			}
		}
		Integer seat = row * 8 + col;
		s.add(seat);
		return seat;
	}

}
