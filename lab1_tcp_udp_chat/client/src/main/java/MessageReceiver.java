import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class MessageReceiver implements Runnable {
	private final static int BUFFER_SIZE = 1024;
	private SocketChannel channel;
	private ObjectMapper mapper = new ObjectMapper();
	private Map<Integer, MessageCreator> messageCreatorMap = new HashMap<>();

	MessageReceiver(SocketChannel channel) {
		this.channel = channel;
	}

//	Wyniki wyszukiwania
//	Znalezione obrazy dla zapytania adam mickiewicz biografia
//	eszkola.pl
//	Biografia Adama Mickiewicza. Adam Mickiewicz urodził sie 24 grudnia 1798r. w Zaosiu koło Nowogródka. Jego rodzina wywodziła się z drobnej szlachty. W tamtych okolicach spędził pierwszy okres życia, chodził do szkoły prowadzonej przez dominikanów.
	@Override
	public void run() {
		try {
			while (true) {
				ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
				channel.read(buffer);
				String output = new String(buffer.array()).trim();
				if (!output.equals("")) {
					String[] jsons = output.split("}");
					for(String json: jsons) {
						json = json + "}";
						System.out.println(json);
						Packet packet = mapper.readValue(json, Packet.class);
						addPacketToMap(packet);
						if (areAllPacketsReceived(packet.getHash())) {
							printMessage(packet.getHash());
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addPacketToMap(Packet packet) {
		MessageCreator messageCreator = messageCreatorMap.get(packet.getHash());
		if (messageCreator == null) {
			messageCreatorMap.put(packet.getHash(), new MessageCreator(packet));
		} else {
			messageCreator.addPacket(packet);
		}
	}

	private boolean areAllPacketsReceived(int hash) {
		return messageCreatorMap.get(hash).areAllPacketReceived();

	}

	private void printMessage(int hash) {
		Message message = messageCreatorMap.get(hash)
				.getMessage()
				.orElseThrow(() -> new IllegalArgumentException("cannot create message"));
		System.out.println(message);
	}
}
