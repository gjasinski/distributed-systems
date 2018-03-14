import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ChannelRepository {
    private List<ChannelUser> channels = new ArrayList<>();

    synchronized void addChannel(ChannelUser channelUser) {
        this.channels.add(channelUser);
    }

    synchronized void sendMessage(String message, String fromUser) {
        for (int i = 0; i < channels.size(); i++) {
            ChannelUser channel = channels.get(i);
            try {
                channel.sendMessage(message, fromUser);
            } catch (IOException e) {
                terminate(channel);
            }
        }
    }

    private void terminate(ChannelUser channel) {
        System.out.println(channel.getName() + "start closing");
        channel.terminate();
        channels.remove(channel);
        System.out.println(channel.getName() + " closed connection");
    }
}
