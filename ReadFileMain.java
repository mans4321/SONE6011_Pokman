
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileMain {
	
	public static void main(String[] args) throws IOException {
		

		FileReader file = new FileReader("C:\\Users\\Administrator\\Desktop\\deck 1.txt");
		BufferedReader reader = new BufferedReader(file);

		String text = "";
		String line = reader.readLine();
		while(line != null) {
			text += line+"\n";
			line = reader.readLine();
		}
		
		System.out.println(text);
	}

}
