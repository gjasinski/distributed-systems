import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

class ChannelRepository {
//    private final ChannelUDP channelUDP;
    private final List<ChannelTCP> channels = new ArrayList<>();
//    private final List<SocketAddress> socketAddresses = new ArrayList<>();
//
//    ChannelRepository(ChannelUDP channelUDP) {
//        this.channelUDP = channelUDP;
//    }

    synchronized void addChannelTCP(ChannelTCP channelTCP) {
        this.channels.add(channelTCP);
    }

    synchronized void sendTCPMessage(String message, String fromUser) throws IOException {
        for (int i = 0; i < channels.size(); i++) {
            ChannelTCP channel = channels.get(i);
            channel.sendMessage(message, fromUser);
        }
    }
/*

    synchronized void addUDPUser(SocketAddress socketAddress){
        this.socketAddresses.add(socketAddress);
    }

    synchronized void sendUDPMessage(String message, SocketAddress from){
        for(int i = 0; i < socketAddresses.size(); i++){
            SocketAddress address = socketAddresses.get(i);
            if(address.equals(from)){
                continue;
            }
            try {
                channelUDP.sendUDPMessage(new DatagramPacket(message.getBytes(), message.length(), address));
            } catch (IOException e) {
                System.out.println("Send UDP packet was not successful");
            }
        }
    }
*/

    synchronized void removeChannelUser(ChannelTCP channel){
        this.channels.remove(channel);
    }

}
