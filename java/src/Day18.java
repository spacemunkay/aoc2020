public class Day18 {

	public static void main(String[] args) throws Exception {
		StringBuffer sb = ReadURL.getWebData(18, "jason");
		String data = sb.toString();

		System.out.println("first answer = " + sum(data, false));
		System.out.println("second answer = " + sum(data, true));

	}

	private static long sum(String data, boolean orderMatters) {
		long sum = 0;
		for (String expression : data.split("\n")) {
			sum += calculate(expression, orderMatters);
		}
		return sum;
	}

	private static long calculate(String expression, boolean orderMatters) {
		expression = expression.replace(" ", "");
		if (!expression.contains("(")) {
			return evaluate(expression, orderMatters);
		} else {
			int parenCount = 1;
			int op = expression.indexOf('(');
			int cp = op + 1;
			while (parenCount > 0 && cp < expression.length()) {
				char c = expression.charAt(cp);
				if (c == '(')
					parenCount++;
				if (c == ')')
					parenCount--;
				cp++;
			}
			return calculate(expression.substring(0, op) + calculate(expression.substring(op + 1, cp - 1), orderMatters)
					+ expression.substring(cp), orderMatters);
		}
	}

	private static long evaluate(String expression, boolean orderMatters) {
		try {
			return Long.parseLong(expression);
		} catch (NumberFormatException e) {
			if (!orderMatters) {
				int plusIdx = expression.lastIndexOf('+');
				int multIdx = expression.lastIndexOf('*');
				int lastIdx = Math.max(plusIdx, multIdx);
				long total = evaluate(expression.substring(0, lastIdx), orderMatters);
				long operand = Long.parseLong(expression.substring(lastIdx + 1));
				return expression.charAt(lastIdx) == '+' ? total + operand : total * operand;
			} else {
				long product = 1;
				for (String s : expression.split("\\*")) {
					product *= evaluate(s, false);
				}
				return product;
			}
		}
	}
}
