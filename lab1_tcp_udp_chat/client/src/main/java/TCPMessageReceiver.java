import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPMessageReceiver implements Runnable {
    private final BufferedReader reader;
    private final InputStreamReader inputStreamReader;
    private volatile boolean terminate = false;

    TCPMessageReceiver(Socket socket) throws IOException {
        inputStreamReader = new InputStreamReader(socket.getInputStream());
        reader = new BufferedReader(inputStreamReader);
    }

    @Override
    public void run() {
        try {
            String message = reader.readLine();
            while (!terminate && message != null) {
                System.out.println(message);
                message = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStreamReader.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void terminate() {
        this.terminate = true;
    }
}
