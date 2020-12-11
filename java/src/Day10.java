
public class Day10 {
	public static void main(String[] args) throws Exception {

		StringBuffer data = ReadURL.getWebData(10, "patrick");
		String dataString = data.toString();

		System.out.println(dataString);
	}
}
