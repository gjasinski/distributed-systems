import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ChannelRepository {
	private List<ChannelUser> channels = new LinkedList<>();
	private Map<Integer, Thread> threads = new HashMap<>();

	void addChannel(ChannelUser channelUser, Thread thread){
		this.channels.add(channelUser);
		this.threads.put(channelUser.hashCode(), thread);
	}

	void sendMessage(String message, String fromUser) {
		this.channels.forEach(channel -> {
			try {
				channel.sendMessage(message, fromUser);
			} catch (IOException e) {
				terminate(channel);
			}
		});
	}

	private void terminate(ChannelUser channel){
		try {
			channel.terminate();
			Thread thread = threads.get(channel.hashCode());
			thread.join();
			channels.remove(channel);
			threads.remove(channel.hashCode());
			System.out.println(channel.getName() + " closed connection");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
