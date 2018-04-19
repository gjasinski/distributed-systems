import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

class Admin {
	private static final String DEFAULT_EXCHANGE = "EXCHANGE";
	private static final String LOGS = "/logs";
	private static final String INFO = "/info";
	private final Producer producer;

	Admin() throws IOException, TimeoutException {
		new Listener(LOGS, DEFAULT_EXCHANGE).run();
		producer = new Producer(DEFAULT_EXCHANGE);
	}

	void run() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true){
			String message = br.readLine();
			producer.sendMessage(INFO, message);
		}
	}

	public static void main(String[] args) throws IOException, TimeoutException {
		new Admin().run();
	}
}
