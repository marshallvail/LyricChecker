package json;

import java.io.IOException;

public class TokenTester {
	public static void main (String[] args) {
		try {
			JSONParser p = new JSONParser(System.in);
			while (true) {
				System.out.println(p.value());
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
