import java.io.IOException;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ChannelRepository {
	private List<ChannelUser> channels = new ArrayList<>();
	private Map<Integer, Thread> threads = new HashMap<>();

	synchronized void addChannel(ChannelUser channelUser, Thread thread){
		this.channels.add(channelUser);
		this.threads.put(channelUser.hashCode(), thread);
	}

	synchronized void sendMessage(String message, String fromUser) {
		for(int i = 0; i < channels.size(); i++){
			ChannelUser channel = channels.get(i);
			try {
				channel.sendMessage(message, fromUser);
			} catch (IOException e) {
				terminate(channel);
			}
		}
	}

	private void terminate(ChannelUser channel){
		try {
			System.out.println(channel.getName() + "start closing");
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
