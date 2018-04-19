import java.io.DataInputStream;
import java.io.IOException;

public class Doctor {
	private final String name;

	public Doctor(String name) {
		this.name = name;
	}

	public static void main(String[] args) throws IOException {
		DataInputStream in = new DataInputStream(System.in);
		String name = in.readLine();

	}
}
