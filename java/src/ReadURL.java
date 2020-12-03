import java.net.*;
import java.io.*;

public class ReadURL {
    public static void main(String[] args) throws Exception {

        URL url = new URL("https://adventofcode.com/2020/day/2/input");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}