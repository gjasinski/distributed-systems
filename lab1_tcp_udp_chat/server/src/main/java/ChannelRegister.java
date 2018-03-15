import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ChannelRegister implements Runnable {
    private final static int MAX_USERS = 100;
    private final ServerSocket serverSocket;
    private final ChannelRepository channelRepository;
    private final ChannelUDP channelUDP;

    ChannelRegister(String host, int port) throws IOException {
        serverSocket = new ServerSocket(port);
        channelRepository = new ChannelRepository();
        channelUDP = new ChannelUDP(port);
    }


    @Override
    public void run() {
        int users = 0;
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(channelUDP);
        try {
            while (users < MAX_USERS) {
                Socket client = serverSocket.accept();
                SocketAddress remoteSocketAddress = client.getRemoteSocketAddress();
                ChannelTCP userChannel = new ChannelTCP(client, channelRepository);
                executorService.execute(userChannel);
                channelRepository.addChannelTCP(userChannel);
                channelUDP.addUDPUser(remoteSocketAddress);
                users++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        finally {
            channelUDP.terminate();
            executorService.shutdownNow();
        }
    }
}
