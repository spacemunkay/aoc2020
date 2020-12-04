
public class Day4 {

	public static void main(String[] args) throws Exception {

		StringBuffer data = ReadURL.getWebData(4, "patrick");

		String[] passports = data.toString().split("\\n\\n");
		int count = 0;
		for (String passport : passports) {
			if (isValid(passport)) {
				count++;
			}
		}
		System.out.println(count);
	}

	private static boolean isValid(String passport) {
		String[] fields = passport.split("\\s");
		if (fields.length == 8) {
			return true;
		}
		if (fields.length == 7) {
			for (String field : fields) {
				if (field.substring(0, 3).equals("cid")) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
