import java.net.*;
import java.io.*;

public class ReadURL {
	public static StringBuffer getWebData(int day, String user) throws Exception {

		String urlString = "https://adventofcode.com/2020/day/" + day + "/input";
		String keyLocation = "input//sessionkey" + (user.toLowerCase().equals("patrick") ? "1" : "2");

		File file = new File(keyLocation);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String sessionKey = br.readLine();
		br.close();

		URL urlObject = new URL(urlString);
		URLConnection connection = urlObject.openConnection();
		connection.setRequestProperty("Cookie", "session=" + sessionKey);
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		String in;
		StringBuffer rString = new StringBuffer();
		while ((in = reader.readLine()) != null)
			rString.append(in + "\n");
		reader.close();
		return rString;
	}
}