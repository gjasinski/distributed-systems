import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

class ChannelRepository {
	private List<ChannelUser> channels = new LinkedList<>();

	void addChannel(ChannelUser channelUser){
		this.channels.add(channelUser);
	}

	void sendMessage(Message message){
		this.channels.forEach(channel -> {
			try {
				channel.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void sendPacket(Packet packet) {
		this.channels.forEach(channel -> {
			try {
				channel.sendPacket(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
