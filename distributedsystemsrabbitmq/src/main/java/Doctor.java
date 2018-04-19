import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Doctor {
	private final static String DEFAULT_EXCHANGE = "EXCHANGE";
	private static final String DOCTOR_TOPIC_PREFIX = "/doctor/";
	private static final String INFO_TOPIC = "/info";
	private static final String LOGS = "/logs";
	private static final String TECHNICIAN = "/technician/";
	private static final String LOCALHOST = "localhost";
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String doctorsName;
	private final Listener listener;
	private final Listener listenerInfo;
	private final Producer producer;

	Doctor(String doctorsName) throws IOException, TimeoutException {
		this.doctorsName = doctorsName;
		this.listener = new Listener(DOCTOR_TOPIC_PREFIX + doctorsName, DEFAULT_EXCHANGE);
		this.listenerInfo = new Listener(INFO_TOPIC, DEFAULT_EXCHANGE);
		this.producer = new Producer(DEFAULT_EXCHANGE);
	}

	void run() throws IOException, TimeoutException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int id = 0;
		listener.run();
		listenInfo();
		while(true){
			String patient = br.readLine();
			TestType testType = TestType.valueOf(br.readLine());
			Test test = new Test(id, doctorsName, patient, testType);
			producer.sendMessage(TECHNICIAN + testType.toString(), objectMapper.writeValueAsString(test));
			producer.sendMessage(LOGS, objectMapper.writeValueAsString(test));
		}
	}

	public static void main(String[] args) throws IOException, TimeoutException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String name = br.readLine();
		new Doctor(name).run();
	}

	private void listenInfo() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(LOCALHOST);
		Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		channel.exchangeDeclare(DEFAULT_EXCHANGE, BuiltinExchangeType.TOPIC);

		String queueName3 = channel.queueDeclare().getQueue();
		channel.basicQos(10);
		channel.queueBind(queueName3, DEFAULT_EXCHANGE, "/info");
		System.out.println("Waiting for messages...");
		channel.basicConsume(queueName3, true, createInfoConsumer(channel));
	}

	private DefaultConsumer createInfoConsumer(Channel channel){
		return new DefaultConsumer(channel) {
			private final ObjectMapper objectMapper = new ObjectMapper();
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("Received: " + message);
			}
		};
	}
}