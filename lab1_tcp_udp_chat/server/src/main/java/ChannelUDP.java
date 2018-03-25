import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChannelUDP implements Runnable{
    private final DatagramSocket socket;
    private final List<SocketAddress> socketAddresses = new ArrayList<>();
    private volatile boolean terminate = false;

    ChannelUDP(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    @Override
    public void run() {
        try {
            byte[] receiveBuffer = new byte[16384];
            while (!terminate) {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData()).trim();
                System.out.println(message);
                sendUDPMessage(message, receivePacket.getSocketAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized void addUDPUser(SocketAddress socketAddress){
        this.socketAddresses.add(socketAddress);
    }

    synchronized private void sendUDPMessage(String message, SocketAddress from){
        for(int i = 0; i < socketAddresses.size(); i++){
            SocketAddress address = socketAddresses.get(i);
            if(address.equals(from)){
                continue;
            }
            try {
                socket.send(new DatagramPacket(message.getBytes(), message.length(), address));
            } catch (IOException e) {
                System.out.println("Send UDP packet was not successful");
            }
        }
    }

    void terminate() {
        this.terminate = true;
        this.socket.close();
    }
}
