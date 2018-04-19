import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Doctor {
	private final static String DEFAULT_EXCHANGE = "EXCHANGE";
	private static final String DOCTOR_TOPIC_PREFIX = "/doctor/";
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String doctorsName;
	private final Listener listener;
	private final Producer producer;

	Doctor(String doctorsName) throws IOException, TimeoutException {
		this.doctorsName = doctorsName;
		this.listener = new Listener(DOCTOR_TOPIC_PREFIX + doctorsName, DEFAULT_EXCHANGE);
		this.producer = new Producer(DEFAULT_EXCHANGE);
	}

	void run() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int id = 0;
		listener.run();
		while(true){
			String patient = br.readLine();
			TestType testType = TestType.valueOf(br.readLine());
			Test test = new Test(id, doctorsName, patient, testType);
			producer.sendMessage("/technician/" + testType.toString(), objectMapper.writeValueAsString(test));
		}
	}

	public static void main(String[] args) throws IOException, TimeoutException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String name = br.readLine();
		new Doctor(name).run();
	}
}
