import java.util.*;
import java.util.Map.Entry;

public class Day13 {

	//private static final long START_VAL = 100000000000000L;

	public static void main(String[] args) throws Exception {
		StringBuffer sb = ReadURL.getWebData(13, "patrick");
		String data = sb.toString();
		String test = "1000655\n" + 
				"17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,571,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,23,x,x,x,x,x,29,x,401,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19";

		Scanner s = new Scanner(test);
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
		long start = System.currentTimeMillis();
		System.out.println("second answer = " + consecutiveMinutes(busArray));
		long end = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (end - start) + " ms");
	}

	private static long consecutiveMinutes(String[] busArray) {

		Map<Long, Long> buses = new HashMap<Long, Long>();
		for (int i = 0; i < busArray.length; i++) {
			if (!busArray[i].equals("x")) {
				long val = Long.parseLong(busArray[i]);
				buses.put((val - i) % val, val);
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

		/**
		 * int maxIdx = findMaxIdx(buses); int max = buses.get(maxIdx);
		 * 
		 * // Additional code to run threads
		 * 
		 * List<Long> intervals = new ArrayList<Long>(); for (int i = 0; i < 10; i++) {
		 * long time = START_VAL + i * START_VAL / 10; while (time % max != maxIdx) {
		 * time++; } time -= maxIdx; System.out.println("interval " + i + ": " + time);
		 * intervals.add(time); }
		 * 
		 * BusRunner[] threads = new BusRunner[intervals.size() - 1]; //List<Long>
		 * answers = new ArrayList<Long>(); for (int i = 0; i < threads.length; i++) {
		 * threads[i] = new BusRunner(intervals.get(i), intervals.get(i + 1), maxIdx,
		 * buses); threads[i].start(); } for (int i = 0; i < threads.length; i++) { try
		 * { threads[i].join(); } catch (InterruptedException e) { e.printStackTrace();
		 * } }
		 * 
		 * for (long answer : answers) { System.out.println("answer: " + answer); if
		 * (answer != -1) { System.out.println("Used threads"); return answer; } }
		 * 
		 * 
		 * // Code block not used for threads boolean mal = true; long time = max -
		 * maxIdx; while (mal) { mal = false; for (int key : buses.keySet()) { if ((time
		 * + key) % buses.get(key) != 0) { //System.out.println(time); time += max *
		 * 1000; mal = true; break; } } } System.out.println("Did not use threads");
		 * return time;
		 **/
	}

	private static long getCRTVal(long key, long valProduct, Map<Long, Long> buses) {
		// return ai*Mi*yi
		long val = buses.get(key);
		long m = valProduct / val;
		// m*y = 1 (mod value)
		long y = 1;
		while ((m * y) % val != 1) {
			y++;
		}
		
		return key * y * m;
	}

	

	/**
	public static List<Long> answers = new LinkedList<Long>();

	public static class BusRunner extends Thread {
		long rngMin;
		long rngMax;
		int maxIdx;
		long maxVal;
		Map<Integer, Integer> buses;
		long retVal;

		public BusRunner(long min, long max, int maxIdx, Map<Integer, Integer> buses) {
			this.rngMin = min;
			this.rngMax = max;
			this.maxIdx = maxIdx;
			this.buses = buses;
			this.maxVal = buses.get(maxIdx);
			retVal = 0;
		}

		public void run() {
			// System.out.println("This thread ran.");
			long time = rngMin;
			boolean mal = true;
			while (mal && time <= rngMax) {
				// System.out.println("This thread entered the while loop.");
				mal = false;
				for (int key : buses.keySet()) {
					if ((time + key) % buses.get(key) != 0) {
						// System.out.println("running... " + time);
						time += maxVal * 1000;
						mal = true;
						break;
					}
				}

			}
			// Wrap in mutex?
			answers.add(time >= rngMax ? -1 : time);
		}

	}
	**/

	private static long findMaxIdx(Map<Long, Long> map) {
		long maxIdx = 0;
		long max = 0;
		for (long key : map.keySet()) {
			if (map.get(key) > max) {
				maxIdx = key;
				max = map.get(key);
			}
		}
		return maxIdx;
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
