import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ChannelUser implements Runnable {
	private final static int BUFFER_SIZE = 256;
	private final Selector selector;
	private final SocketChannel channel;
	private final ChannelRepository channelRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

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
			while (true) {
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
							String[] jsons = output.split("}{");
							for(String json: jsons) {
								json = json +"}";
								System.out.println("Message read from client: " + json);
//								Packet packet = objectMapper.readValue(json, Packet.class);
//								System.out.println(packet);
//								channelRepository.sendPacket(packet);
//							Message message = objectMapper.readValue(output, Message.class);
//							System.out.println(message);
//							channelRepository.sendMessage(message);
							}
						}
					}
				}
				iter.remove();
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	void sendMessage(Message message) throws IOException {
		String s = objectMapper.writeValueAsString(message);
		ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
		channel.write(buffer);
	}

	public void sendPacket(Packet packet) throws IOException {
		String s = objectMapper.writeValueAsString(packet);
		ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
		channel.write(buffer);
	}
}
