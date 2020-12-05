import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Day4 {

	static boolean detailedPassports = true; // Toggle true for part 2

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
			return detailedPassports ? validDetails(fields) : true;
		}
		if (fields.length == 7) {
			for (String field : fields) {
				if (field.substring(0, 3).equals("cid")) {
					return false;
				}
			}
			return detailedPassports ? validDetails(fields) : true;
		}
		return false;
	}

	private static boolean validDetails(String[] fields) {
		Map<String, String> fieldsMap = new HashMap<String, String>();
		for (String field : fields) {
			String[] kvPair = field.split(":");
			fieldsMap.put(kvPair[0].trim(), kvPair[1].trim());
		}
		int byr, iyr, eyr, measure;
		String hgt, hcl, ecl, pid;
		try {
			byr = Integer.parseInt(fieldsMap.get("byr"));
			iyr = Integer.parseInt(fieldsMap.get("iyr"));
			eyr = Integer.parseInt(fieldsMap.get("eyr"));
			hgt = fieldsMap.get("hgt");
			hcl = fieldsMap.get("hcl");
			ecl = fieldsMap.get("ecl");
			pid = fieldsMap.get("pid");
		} catch (Exception e) {
			return false;
		}
		// Check height
		String units = hgt.substring(hgt.length() - 2);
		try {
			measure = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
		} catch (Exception e) {
			return false;
		}
		if (units.equals("cm")) {
			if (measure < 150 || measure > 193) {
				return false;
			}
		} else if (units.equals("in")) {
			if (measure < 59 || measure > 76) {
				return false;
			}
		} else {
			return false;
		}

		// Check hair color
		if (!Pattern.matches("^#[0-9a-f]{6}$", hcl)) {
			return false;
		}

		// Check eye color
		if (!ecl.equals("amb") && !ecl.equals("blu") && !ecl.equals("brn") && !ecl.equals("gry") && !ecl.equals("grn")
				&& !ecl.equals("hzl") && !ecl.equals("oth")) {
			return false;
		}

		return (byr >= 1920 && byr <= 2002 && iyr >= 2010 && iyr <= 2020 && eyr >= 2020 && eyr <= 2030
				&& pid.length() == 9);
	}
}
