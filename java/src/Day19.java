import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {

	public static void main(String[] args) throws Exception {
		StringBuffer sb = ReadURL.getWebData(19, "jason");
		String data = sb.toString();

		System.out.println("first answer = " + findMatches(data, false));
		System.out.println("second answer = " + findMatches(data, true));
	}

	private static int findMatches(String data, boolean secondTime) {
		String[] rulesAndStrings = data.split("\n\n");
		String rules = rulesAndStrings[0];
		String strings = rulesAndStrings[1];
		Map<String, String> ruleMap = createRuleMap(rules);
		int maxLength = findMaxLength(strings);
		List<Pattern> patterns = createPatterns(ruleMap, secondTime, maxLength);
		return count(strings, patterns);
	}

	private static int count(String strings, List<Pattern> patterns) {
		int count = 0;
		for (String s : strings.split("\n")) {
			for (Pattern p : patterns) {
				Matcher m = p.matcher(s);
				if (m.matches()) {
					count++;
					break;
				}
			}
		}
		return count;
	}

	private static List<Pattern> createPatterns(Map<String, String> ruleMap, boolean secondTime, int maxLength) {
		List<Pattern> patterns = new ArrayList<Pattern>();
		for (int i = 1; i <= maxLength; i++) {
			patterns.add(Pattern.compile("^" + createRuleExpression(ruleMap, "0", secondTime, i) + "$"));
		}		
		return patterns;
	}

	private static int findMaxLength(String data) {
		int max = 0;
		for (String datum : data.split("\n")) {
			max = datum.length() > max ? datum.length() : max;
		}
		return max;
	}

	private static String createRuleExpression(Map<String, String> ruleMap, String startIdx, boolean secondTime, int reps) {
		if (startIdx.equals("|")) {
			return "|";
		}
		if (secondTime && startIdx.equals("8")) {
			return "(" + createRuleExpression(ruleMap, "42", secondTime, reps) + ")+";
		}
		if (secondTime && startIdx.equals("11")) {
			return "(" + elevensies(ruleMap, "42", "31", secondTime, reps) + ")";
		}
		String value = ruleMap.get(startIdx);
		if (value.equals("a") || value.equals("b")) {
			return value;
		}
		StringBuilder expression = new StringBuilder();
		expression.append("(");
		for (String key : value.split(" ")) {
			expression.append(createRuleExpression(ruleMap, key, secondTime, reps));
		}
		expression.append(")");
		return expression.toString();
	}

	private static String elevensies(Map<String, String> ruleMap, String a, String b, boolean secondTime, int reps) {
		// return createRuleExpression(ruleMap, a, secondTime, reps) + "(?R)?" + createRuleExpression(ruleMap, a, secondTime, reps);
		// return "(?<eleven>(" + createRuleExpression(ruleMap, a, secondTime, reps) + ")(?&eleven)?(" + createRuleExpression(ruleMap, b, secondTime, reps) + "))";
		
		String retStr = "";
		for (int i = 0; i < reps; i++) {
			retStr += createRuleExpression(ruleMap, a, secondTime, reps);
		}
		for (int i = 0; i < reps; i++) {
			retStr += createRuleExpression(ruleMap, b, secondTime, reps);
		}
		return retStr;
		
	}

	private static Map<String, String> createRuleMap(String rules) {
		Map<String, String> ruleMap = new HashMap<String, String>();
		for (String line : rules.split("\n")) {
			String[] keyVal = line.split(": ");
			ruleMap.put(keyVal[0], keyVal[1].replace("\"", ""));
		}
		return ruleMap;
	}

}
