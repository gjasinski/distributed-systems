import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        channelUDP = new ChannelUDP(host, port);
    }


    @Override
    public void run() {
        int users = 0;
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(channelUDP);
        try {
            while (users < MAX_USERS) {
                Socket client = serverSocket.accept();
                System.out.println(client.getInetAddress().toString());
                ChannelUser userChannel = new ChannelUser(client, channelRepository);
                executorService.execute(userChannel);
                channelRepository.addChannel(userChannel);
                users++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
