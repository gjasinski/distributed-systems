import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ChannelRepository {
    private final List<ChannelTCP> channels = new ArrayList<>();

    synchronized void addChannelTCP(ChannelTCP channelTCP) {
        this.channels.add(channelTCP);
    }

    synchronized void sendTCPMessage(String message, String fromUser) throws IOException {
        for (int i = 0; i < channels.size(); i++) {
            ChannelTCP channel = channels.get(i);
            channel.sendMessage(message, fromUser);
        }
    }

    synchronized void removeChannelUser(ChannelTCP channel) {
        this.channels.remove(channel);
    }

}
