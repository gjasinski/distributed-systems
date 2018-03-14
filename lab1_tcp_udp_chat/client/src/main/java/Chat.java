import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Chat {
	private BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

	public Chat(InetSocketAddress inetSocketAddress) {
	}

	void runChat() throws IOException {
		InetSocketAddress hostAddress = new InetSocketAddress("localhost", 10000);
		SocketChannel client = SocketChannel.open(hostAddress);
		MessageReceiver messageReceiver = new MessageReceiver(client);
		Thread t = new Thread(messageReceiver);
		t.start();
		try {
			String name = getName();
			sendString(client, name);
			while (true) {
				String input = consoleReader.readLine();
				sendString(client, name + ": " + input);
			}
		} finally {
			client.close();
		}
	}

	private void sendString(SocketChannel client, String message) throws IOException {
		ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
		client.write(buffer);
	}

	private String getName() throws IOException {
		System.out.println("Type your name: ");
		return consoleReader.readLine();
	}
}
