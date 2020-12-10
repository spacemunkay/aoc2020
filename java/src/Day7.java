import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Solution for Day 7
public class Day7 {

	public static void main(String[] args) throws Exception {

		StringBuffer data = ReadURL.getWebData(7, "patrick");
		String[] answerSets = data.toString().split("\\n");

		Set<String> oldDescriptors = new HashSet<String>();
		Set<String> newDescriptors = new HashSet<String>();
		Set<String> totalDescriptors = new HashSet<String>();
		oldDescriptors.add("shiny gold");
		boolean changed = true;
		while (changed) {
			changed = false;
			for (String answer : answerSets) {
				String[] bagPair = answer.split(" bags contain ");
				for (String d : oldDescriptors) {
					if (bagPair[1].contains(d)) {
						totalDescriptors.add(bagPair[0].trim());
						newDescriptors.add(bagPair[0].trim());
						newDescriptors.remove(d);
						changed = true;
					}
				}
			}
			oldDescriptors = Set.copyOf(newDescriptors);
		}
		System.out.println("first answer = " + totalDescriptors.size());

		List<List<String>> trimmedLines = trimLines(answerSets);
		System.out.println("second answer = " + (numBags(trimmedLines, "shiny gold") - 1));
	}

	private static List<List<String>> trimLines(String[] answerSets) {
		List<List<String>> trimmedLines = new ArrayList<List<String>>();
		for (String answer : answerSets) {
			List<String> line = new ArrayList<String>();
			answer = answer.substring(0, answer.length() - 5).trim();
			String[] bags = answer.split(" bags contain | bags, | bag, ");
			for (String bag : bags) {
				line.add(bag.trim());
			}
			trimmedLines.add(line);
		}
		return trimmedLines;
	}

	private static long numBags(List<List<String>> trimmedText, String descriptor) {
		long totalBags = 1;
		for (List<String> line : trimmedText) {
			if (line.get(0).equals(descriptor)) {
				if (line.get(1).equals("no other")) {
					return 1;
				} else {
					for (int i = 1; i < line.size(); i++) {
						long num = Long.parseLong(line.get(i).substring(0, 1));
						String newDescriptor = line.get(i).substring(2);
						totalBags += num * numBags(trimmedText, newDescriptor);
					}
				}
			}
		}
		return totalBags;
	}
}
