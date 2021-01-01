import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day16 {

	public static void main(String[] args) throws Exception {
		StringBuffer sb = ReadURL.getWebData(16, "patrick");
		String data = sb.toString();
		String test = "class: 0-1 or 4-19\n" + 
				"row: 0-5 or 8-19\n" + 
				"seat: 0-13 or 16-19\n" + 
				"\n" + 
				"your ticket:\n" + 
				"11,12,13\n" + 
				"\n" + 
				"nearby tickets:\n" + 
				"3,9,18\n" + 
				"15,1,5\n" + 
				"5,14,9";

		String[] info = test.split("\n\n");
		String[] ranges = info[0].split("\n");
		String myTicket = info[1].split("\n")[1];
		int numCols = myTicket.split(",").length;
		String[] nearbyTicketsWithHeader = info[2].split("\n");
		String[] nearbyTickets = Arrays.copyOfRange(nearbyTicketsWithHeader, 1, nearbyTicketsWithHeader.length);
		Set<String> validTickets = new HashSet<String>();
		Set<Integer> totalRange = setRange(ranges);
		System.out.println("first answer = " + findErrorRate(totalRange, nearbyTickets, validTickets));
		System.out.println("second answer = " + validateTicket(myTicket, ranges, validTickets, numCols, "class"));

	}

	private static int validateTicket(String myTicket, String[] ranges, Set<String> validTickets, int numCols, String keyword) {
		List<Integer> ticketValues = new ArrayList<Integer>();
		String[] ticketValuesString = myTicket.split(",");
		for (String value : ticketValuesString) {
			ticketValues.add(Integer.parseInt(value));
		}

		int product = 1;
		Map<String, Integer> fieldsByIndex = getFields(ranges, validTickets, numCols);
		for (String key : fieldsByIndex.keySet()) {
			if (key.contains(keyword)) {
				product *= ticketValues.get(fieldsByIndex.get(key));
			}
		}
		return product;
	}

	private static Map<String, Integer> getFields(String[] ranges, Set<String> validTickets, int numCols) {
		Map<String, Integer> fieldsByIndex = new HashMap<String, Integer>();
		Map<String, List<List<Integer>>> fieldsValidColumns = new HashMap<String, List<List<Integer>>>();
		List<List<Integer>> columns = splitTickets(validTickets, numCols);
		for (String field : fieldsByIndex.keySet()) {
			mapColumns(field, columns, fieldsValidColumns);
		}
		reduceColumns(fieldsValidColumns, fieldsByIndex, columns);
		return fieldsByIndex;
	}

	private static void reduceColumns(Map<String, List<List<Integer>>> fieldsValidColumns,
			Map<String, Integer> fieldsByIndex, List<List<Integer>> columns) {
		while (fieldsByIndex.size() < columns.size()) {
			for (String key : fieldsValidColumns.keySet()) {
				if (fieldsValidColumns.get(key).size() == 1) {
					List<Integer> singleCol = fieldsValidColumns.get(key).get(0);
					for (String k: fieldsValidColumns.keySet()) {
						if (key != k && fieldsValidColumns.get(k).contains(singleCol)) {
							fieldsValidColumns.get(k).remove(singleCol);
						}
					}
					fieldsByIndex.put(key, columns.indexOf(singleCol));
				}
			}
		}
	}

	private static void mapColumns(String field, List<List<Integer>> columns, Map<String, List<List<Integer>>> fieldsValidColumns) {
		fieldsValidColumns.put(field, new ArrayList<List<Integer>>());
		String[] s = {field};
		Set<Integer> range = setRange(s);
		for (List<Integer> column : columns) {
			boolean addColumn = true;
			for (Integer value : column) {
				if (!range.contains(value)) {
					addColumn = false;
				}
			}
			if (addColumn) fieldsValidColumns.get(field).add(column);
		}
	}

	private static List<List<Integer>> splitTickets(Set<String> validTickets, int numCols) {
		List<List<Integer>> columns = new ArrayList<List<Integer>>();
		for (int i = 0; i < numCols; i++) {
			columns.add(new ArrayList<Integer>());
		}
		for (String ticket : validTickets) {
			String[] values = ticket.split(",");
			for (int i = 0; i < values.length; i++) {
				columns.get(i).add(Integer.parseInt(values[i]));
			}
		}
		return columns;
	}

	private static Set<Integer> setRange(String[] ranges) {
		Set<Integer> totalRange = new HashSet<Integer>();
		for (String line : ranges) {
			String[] halves = line.split(": ")[1].split(" or ");
			for (String half : halves) {
				String[] bounds = half.split("-");
				int min = Integer.parseInt(bounds[0]);
				int max = Integer.parseInt(bounds[1]);
				for (int i = min; i <= max; i++) {
					totalRange.add(i);
				}
			}
		}
		return totalRange;
	}

	private static int findErrorRate(Set<Integer> totalRange, String[] nearbyTickets, Set<String> validTickets) {
		// System.out.println(totalRange.toString());
		int errorRateSum = 0;
		for (String ticket : nearbyTickets) {
			boolean valid = true;
			String[] values = ticket.split(",");
			for (String value : values) {
				int val = Integer.parseInt(value);
				if (!totalRange.contains(val)) {
					errorRateSum += val;
					valid = false;
				}
			}
			if (valid)
				validTickets.add(ticket);
		}
		return errorRateSum;
	}

}
