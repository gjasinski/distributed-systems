import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class ChannelUser implements Runnable {
	private final static int BUFFER_SIZE = 1024;
	private final Selector selector;
	private final SocketChannel channel;
	private final ChannelRepository channelRepository;
	private String name;
	private volatile boolean terminate = false;

	ChannelUser(SocketChannel channel, ChannelRepository channelRepository) throws IOException {
		this.channel = channel;
		this.channelRepository = channelRepository;
		selector = Selector.open();
		channel.configureBlocking(false);
		channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}

	@Override
	public void run() {
		try {
			receiveNameFromClient();
			receiveMessagesFromClient();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private void receiveMessagesFromClient() throws IOException {
		while (!terminate) {
			selector.select();
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				extractMessage(iterator.next())
						.ifPresent(message -> channelRepository.sendMessage(message, name));
			}
			iterator.remove();
		}
	}

	private String readName() throws IOException {
		while (!terminate) {
			selector.select();
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectionKeys.iterator();
			Optional<String> message = extractMessage(iter.next());
			if(message.isPresent()){
				return message.get();
			}
//			iter.remove();
		}
		return "";
	}

	private Optional<String> extractMessage(SelectionKey key) throws IOException {
		String result = null;
		if (key.isReadable()) {
			SocketChannel client = (SocketChannel) key.channel();
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
			client.read(buffer);
			String output = new String(buffer.array()).trim();
			if (!output.equals("")) {
				result = output;
			}
		}
		return Optional.ofNullable(result);
	}

	private void receiveNameFromClient() throws IOException {
		name = readName();
		System.out.println(name + " joined");
		channelRepository.sendMessage(name + " joined", name);
	}

	void sendMessage(String message, String fromUser) throws IOException {
		if (!fromUser.equals(this.name)) {
			ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
			channel.write(buffer);
		}
	}

	synchronized void terminate() {
		this.terminate = true;
	}

	public String getName() {
		return name;
	}
}
