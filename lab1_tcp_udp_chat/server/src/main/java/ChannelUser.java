import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA.SetOverrideType;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
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
			name = readName();
			System.out.println(name + " joined");
			channelRepository.sendMessage(name + " joined", name);
			while (!terminate) {
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iter = selectionKeys.iterator();

				while (iter.hasNext()) {
					SelectionKey ky = iter.next();
					if (ky.isReadable()) {
						SocketChannel client = (SocketChannel) ky.channel();
						ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
						client.read(buffer);
						String output = new String(buffer.array()).trim();
						if (!output.equals("")) {
							channelRepository.sendMessage(output, name);
						}
					}
				}
				iter.remove();
			}
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private String readName() throws IOException {
		while (!terminate) {
			selector.select();
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectionKeys.iterator();
			SelectionKey ky = iter.next();
			if (ky.isReadable()) {
				SocketChannel client = (SocketChannel) ky.channel();
				ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
				client.read(buffer);
				String output = new String(buffer.array()).trim();
				if (!output.equals("")) {
					iter.remove();
					return output;
				}
			}
			iter.remove();
		}
		return "";
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
