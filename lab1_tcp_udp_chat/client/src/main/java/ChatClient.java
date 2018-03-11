import java.io.IOException;
import java.net.InetSocketAddress;

public class ChatClient {

	public static void main(String[] args) throws IOException, InterruptedException {
		new Chat(getServerAddress(args));
	}

	private static InetSocketAddress getServerAddress(String[] args) {
		if (args.length != 2) {
			return new InetSocketAddress("localhost", 10000);
		} else {
			return new InetSocketAddress(args[0], Integer.valueOf(args[1]));
		}
	}
}
