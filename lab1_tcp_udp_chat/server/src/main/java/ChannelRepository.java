import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ChannelRepository {
    private List<ChannelUser> channels = new ArrayList<>();

    synchronized void addChannel(ChannelUser channelUser) {
        this.channels.add(channelUser);
    }

    synchronized void sendMessage(String message, String fromUser) throws IOException {
        for (int i = 0; i < channels.size(); i++) {
            ChannelUser channel = channels.get(i);
            channel.sendMessage(message, fromUser);
        }
    }

    synchronized void removeChannelUser(ChannelUser channel){
        this.channels.remove(channel);
    }

}
