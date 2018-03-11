import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Chat {
	private InetSocketAddress serverAddress;

	public Chat(InetSocketAddress inetSocketAddress) {
		serverAddress = inetSocketAddress;

	}

	void runChat() throws IOException {
		String name = getName();
		SocketChannel client = SocketChannel.open(serverAddress);

		template(client);
		client.close();

	}

	private void template(SocketChannel client) throws IOException {
		System.out.println("Client sending messages to server...");

		// Send messages to server
		String[] messages = new String[]{"Time goes fast.", "What now?", "Bye."};

		for (int i = 0; i < messages.length; i++) {

			byte[] message = new String(messages[i]).getBytes();
			ByteBuffer buffer = ByteBuffer.wrap(message);
			client.write(buffer);

			System.out.println(messages[i]);
			buffer.clear();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String getName() {
		System.out.println("Type your name: ");
		return System.console().readLine();
	}
}
