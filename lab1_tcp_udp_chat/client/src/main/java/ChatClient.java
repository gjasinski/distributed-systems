import java.io.IOException;
import java.net.InetSocketAddress;

public class ChatClient {

	public static void main(String[] args) {
		try {
			new Chat("localhost", 10000).runChat();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
