import java.io.IOException;

public class Chat {
	private final static int PORT = 10000;
	private final static String HOST = "localhost";
	private final ChannelRegister registerChannel;

	public Chat(String host, int port) throws IOException {
		this.registerChannel = new ChannelRegister(host, port);
		this.registerChannel.run();
	}

	public static void main(String[] args) {
		try {
			Chat chat = new Chat(HOST, PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
