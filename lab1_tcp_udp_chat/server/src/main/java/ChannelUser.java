import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChannelUser implements Runnable {
    private final Socket socket;
    private final ChannelRepository channelRepository;

    private PrintWriter writer;
    private BufferedReader reader;
    private InputStreamReader inputStreamReader;
    private String name;

    ChannelUser(Socket socket, ChannelRepository channelRepository) throws IOException {
        this.socket = socket;
        this.channelRepository = channelRepository;
        try {
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            this.inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.reader = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void run() {
        try {
            receiveNameFromClient();
            receiveMessagesFromClient();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        finally {
            terminate();
        }
    }

    private void receiveMessagesFromClient() throws IOException {
        String message = reader.readLine();
        while (message != null) {
            System.out.println(message);
            channelRepository.sendMessage(message, name);
            message = reader.readLine();
        }
    }

    private void receiveNameFromClient() throws IOException {
        name = reader.readLine();
        if (name != null) {
            System.out.println(name + " joined");
            channelRepository.sendMessage(name + " joined", name);
        }
    }

    void sendMessage(String message, String fromUser) throws IOException {
        if (!fromUser.equals(this.name)) {
            writer.println(message);
        }
    }

    private synchronized void terminate(){
        try {
            channelRepository.removeChannelUser(this);
            writer.close();
            inputStreamReader.close();
            reader.close();
            socket.close();
            System.out.println(name + " left");
            channelRepository.sendMessage(name + " left", name);
        }catch (IOException ex){
            System.out.println(ex.toString());
        }
    }
}
