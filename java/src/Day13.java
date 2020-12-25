import java.util.*;
import java.util.Map.Entry;

public class Day13 {

	public static void main(String[] args) throws Exception {
		StringBuffer sb = ReadURL.getWebData(13, "patrick");
		String data = sb.toString();
				
		Scanner s = new Scanner(data);
		long timestamp = s.nextLong();
		s.nextLine();
		String[] busArray = s.nextLine().split(",");
		s.close();
		
		List<Long> buses = new ArrayList<Long>();
		for (String bus : busArray) {
			if (!bus.equals("x")) {
				buses.add(Long.parseLong(bus));
			}
		}
		
		System.out.println("first answer = " + nextArrival(buses, timestamp));		
		System.out.println("second answer = " + consecutiveMinutes(busArray));
	}

	private static long consecutiveMinutes(String[] busArray) {

		Map<Long, Long> buses = new HashMap<Long, Long>();
		for (int i = 0; i < busArray.length; i++) {
			if (!busArray[i].equals("x")) {
				long val = Long.parseLong(busArray[i]);
				buses.put(val - (i % val), val);
			}
		}

		// Implement Chinese Remainder Theorem
		long sum = 0;
		long valProduct = 1;
		for (long val : buses.values()) {
			valProduct *= val;
		}
		for (long key : buses.keySet()) {
			sum += getCRTVal(key, valProduct, buses);
		}
		return sum % valProduct;
	}

	private static long getCRTVal(long key, long valProduct, Map<Long, Long> buses) {
		long val = buses.get(key);
		long m = valProduct / val;
		long y = 1;
		while ((m * y) % val != 1) {
			y++;
		}
		return key * y * m;
	}

	private static long nextArrival(List<Long> buses, long timestamp) {
		long time = timestamp;
		while (true) {
			for (Long bus : buses) {
				if (time % bus == 0) {
					return bus * (time - timestamp);
				}
			}
			time++;
		}
	}
}
