
import java.util.HashMap;
import java.util.Map;

public class Day14 {

	private static Map<Long, Long> memValues;

	public static void main(String[] args) throws Exception {
		StringBuffer sb = ReadURL.getWebData(14, "patrick");
		String data = sb.toString();
		String test = "mask = 000000000000000000000000000000X1001X\n" + 
				"mem[42] = 100\n" + 
				"mask = 00000000000000000000000000000000X0XX\n" + 
				"mem[26] = 1";
		String[] lines = test.split("\n");

		
		clearMemValues();
		applyBitmask(lines, true);
		System.out.println("first answer = " + sumAddresses());
		
		clearMemValues();
		applyBitmask(lines, false);
		System.out.println("second answer = " + sumAddresses());
		//1342803601300 = "too low"
		

	}


	private static void clearMemValues() {
		memValues = new HashMap<Long, Long>();
	}

	private static long sumAddresses() {
		long sum = 0;
		for (long value : memValues.values()) {
			sum += value;
		}
		return sum;
	}

	private static void applyBitmask(String[] lines, boolean first) {
		String mask = "";
		for (String line : lines) {
			String[] splitLine = line.split(" = ");
			if (splitLine[0].equals("mask")) {
				mask = splitLine[1];
			} else {
				int address = Integer.parseInt(splitLine[0].substring(4, splitLine[0].length() - 1));
				long value = Long.parseLong(splitLine[1]);
				if (first) setMemory(address, value, mask);
				else setMemory2(address, value, mask);
			}
		}

	}

	private static void setMemory2(long address, long value, String mask) {
		mask = combine(address, mask);
		//System.out.println(mask);
		if (!mask.contains("X")) {
			//address |= convertBinary(mask);
			//System.out.println("Setting " + value + " at " + address);
			memValues.put(convertBinary(mask), value);
		} else {
			setMemory2(address, value, mask.replaceFirst("X", "0"));
			setMemory2(address, value, mask.replaceFirst("X", "1"));
		}
		return;
	}

	private static String combine(long address, String mask) {
		StringBuilder combined = new StringBuilder();
		String binAddress = Long.toBinaryString(address);
		String revBinAddress = new StringBuilder(binAddress).reverse().toString();
		String revMask = new StringBuilder(mask).reverse().toString();
		for (int i = 0; i < revBinAddress.length(); i++) {
			combined.append(revMask.charAt(i) != '0' ? revMask.charAt(i) : revBinAddress.charAt(i));
		}
		if (revMask.length() > revBinAddress.length()) {
			combined.append(revMask.substring(revBinAddress.length()));
		}
		return combined.reverse().toString();
	}

	private static long convertBinary(String mask) {
		long decVal = 0;
		String reverse = new StringBuilder(mask).reverse().toString();
		for (int i = 0; i < reverse.length(); i++) {
			if (reverse.charAt(i) == '1') {
				decVal += Math.pow(2, i);
			}
		}
		return decVal;
	}

	private static void setMemory(long address, long value, String mask) {
		long andMask = 0;
		long orMask = 0;
		String reverse = new StringBuilder(mask).reverse().toString();
		for (int i = 0; i < reverse.length(); i++) {
			switch (reverse.charAt(i)) {
			case '1':
				andMask += Math.pow(2, i);
				orMask += Math.pow(2, i);
				break;
			case '0':
				break;
			case 'X':
				andMask += Math.pow(2, i);
				break;
			}
		}
		value &= andMask;
		value |= orMask;
		memValues.put(address, value);

	}

}
