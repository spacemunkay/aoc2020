import java.util.*;

public class Day13 {

	private static final long START_VAL = 1000000000L;

	public static void main(String[] args) throws Exception {
		StringBuffer sb = ReadURL.getWebData(13, "patrick");
		String data = sb.toString();
		String test = "0\n1789,37,47,1889";

		Scanner s = new Scanner(test);
		int timestamp = s.nextInt();
		s.nextLine();
		String[] busArray = s.nextLine().split(",");
		s.close();
		List<Integer> buses = new ArrayList<Integer>();
		for (String bus : busArray) {
			if (!bus.equals("x")) {
				buses.add(Integer.parseInt(bus));
			}
		}
		System.out.println("first answer = " + nextArrival(buses, timestamp));
		long start = System.currentTimeMillis();
		System.out.println("second answer = " + consecutiveMinutes(busArray));
		long end = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (end - start) + " ms");
	}

	private static long consecutiveMinutes(String[] busArray) {

		Map<Integer, Integer> buses = new HashMap<Integer, Integer>();
		for (int i = 0; i < busArray.length; i++) {
			if (!busArray[i].equals("x")) {
				buses.put(i, Integer.parseInt(busArray[i]));
			}
		}
		int maxIdx = findMaxIdx(buses);
		int max = buses.get(maxIdx);

		// Additional code to run threads
		
		List<Long> intervals = new ArrayList<Long>();
		for (int i = 0; i < 10; i++) {
			long time = START_VAL + i * START_VAL / 10;
			while (time % buses.get(0) != 0) {
				time++;
			}
			System.out.println("interval " + i + ": " + time);
			intervals.add(time);
		}

		BusRunner[] threads = new BusRunner[intervals.size() - 1];
		//List<Long> answers = new ArrayList<Long>();
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new BusRunner(intervals.get(i), intervals.get(i + 1), maxIdx, buses);
			threads[i].start();
			answers.add(threads[i].retVal);
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (long answer : answers) {
			System.out.println("answer: " + answer);
			if (answer != 0) {
				System.out.println("Used threads");
				return answer;
			}
		}
		

		// Code block not used for threads
		boolean mal = true;
		long time = max - maxIdx;
		while (mal) {
			mal = false;
			for (int key : buses.keySet()) {
				if ((time + key) % buses.get(key) != 0) {
					//System.out.println(time);
					time += max;
					mal = true;
					break;
				}
			}
		}
		System.out.println("Did not use threads");
		return time;

	}

	public static List<Long> answers = new LinkedList<Long>();

	public static class BusRunner extends Thread {
		long min;
		long max;
		int maxIdx;
		Map<Integer, Integer> buses;
		long retVal;

		public BusRunner(long min, long max, int maxIdx, Map<Integer, Integer> buses) {
			this.min = min;
			this.max = max;
			this.maxIdx = maxIdx;
			this.buses = buses;
			retVal = 0;
		}

		public void run() {
			long time = min;
			boolean mal = true;
			while (mal && time <= max) {
				mal = false;
				for (int key : buses.keySet()) {
					if ((time + key) % buses.get(key) != 0) {
						//System.out.println("running... " + time);
						time++; // += buses.get(maxIdx);
						mal = true;
						break;
					}
				}

			}
			// Wrap in mutex?
			answers.add(retVal);
			retVal = time;
			return;
		}

	}

	private static int findMaxIdx(Map<Integer, Integer> map) {
		int maxIdx = 0;
		int max = 0;
		for (int key : map.keySet()) {
			if (map.get(key) > max) {
				maxIdx = key;
				max = map.get(key);
			}
		}
		return maxIdx;
	}

	private static int nextArrival(List<Integer> buses, int timestamp) {
		int time = timestamp;
		while (true) {
			for (Integer bus : buses) {
				if (time % bus == 0) {
					return bus * (time - timestamp);
				}
			}
			time++;
		}
	}

}
