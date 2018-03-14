import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class UDPMessageReceiver implements Runnable {
    private DatagramSocket socket;
    private volatile boolean terminate = false;

    UDPMessageReceiver(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            byte[] receiveBuffer = new byte[16384];
            while (!terminate) {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData());
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void terminate() {
        this.terminate = true;
    }
}
