import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MessageReceiver implements Runnable {
	private final static int BUFFER_SIZE = 1024;
	private SocketChannel channel;

	MessageReceiver(SocketChannel channel) {
		this.channel = channel;
	}

	@Override
	public void run() {
		try {
			while (true) {
				ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
				channel.read(buffer);
				String input = new String(buffer.array()).trim();
				if (!input.equals("")) {
					System.out.println(input);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
