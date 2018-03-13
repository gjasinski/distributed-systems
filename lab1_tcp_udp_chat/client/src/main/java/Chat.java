import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;

public class Chat {
	private InetSocketAddress serverAddress;
	private ObjectMapper objectMapper = new ObjectMapper();
	private BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

	public Chat(InetSocketAddress inetSocketAddress) {
		serverAddress = inetSocketAddress;

	}

	void runChat() throws IOException {
		//Socket client = new Socket(serverAddress.getAddress().getHostAddress(), serverAddress.getPort());
		InetSocketAddress hostAddress = new InetSocketAddress("localhost", 10000);
		SocketChannel client = SocketChannel.open(hostAddress);
		MessageReceiver messageReceiver = new MessageReceiver(client);
		Thread t = new Thread(messageReceiver);
		t.start();
		try {
			String name = getName();
			while (true) {
				String input = consoleReader.readLine();
				Message message = new Message(name, input);
				List<Packet> packets = message.getMessagesList();

				packets.forEach(packet -> {
					try {
						sendPacket(client, packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
//				String s = objectMapper.writeValueAsString(message);
//				System.out.println(s);
//				ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
//				client.write(buffer);
			}
		} finally {

			client.close();
		}
	}

	private void sendPacket(SocketChannel client, Packet packet) throws IOException {
		String dataToSend = objectMapper.writeValueAsString(packet);
		ByteBuffer buffer = ByteBuffer.wrap(dataToSend.getBytes());
		client.write(buffer);
	}

	private String getName() throws IOException {
		System.out.println("Type your name: ");
		return consoleReader.readLine();
	}
}
