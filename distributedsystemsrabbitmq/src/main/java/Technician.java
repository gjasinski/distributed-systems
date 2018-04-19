import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Technician {
	private final static String DEFAULT_EXCHANGE = "EXCHANGE";
	private final static String TECHNICIAN_TOPIC_PREFIX = "/technician/";
	private final static String LOCALHOST = "LOCALHOST";
	private final static String DOCTOR_PREFIX = "/doctor/";
	private final TestType testType1;
	private final TestType testType2;

	Technician(TestType type1, TestType type2) {
		this.testType1 = type1;
		this.testType2 = type2;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		TestType type1 = TestType.valueOf(br.readLine());
		TestType type2 = TestType.valueOf(br.readLine());
		new Technician(type1, type2).run();
	}

	public void run() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(LOCALHOST);
			Connection connection = factory.newConnection();
			final Channel channel = connection.createChannel();

			channel.exchangeDeclare(DEFAULT_EXCHANGE, BuiltinExchangeType.TOPIC);

			String queueName1 = channel.queueDeclare( TECHNICIAN_TOPIC_PREFIX + testType1.toString(),false, false, false, null).getQueue();
			String queueName2 = channel.queueDeclare( TECHNICIAN_TOPIC_PREFIX + testType2.toString(),false, false, false, null).getQueue();
			channel.queueBind(queueName1, DEFAULT_EXCHANGE, queueName1);
			channel.queueBind(queueName2, DEFAULT_EXCHANGE, queueName2);
			channel.basicQos(1, false);
			Consumer consumer = createConsumer(channel);

			// start listening
			System.out.println("Waiting for messages...");
			channel.basicConsume(queueName1, true, consumer);
			channel.basicConsume(queueName2, true, consumer);
		} catch (Exception ex) {

		}
	}

	private DefaultConsumer createConsumer(final Channel channel) throws IOException, TimeoutException {
		return new DefaultConsumer(channel) {
			private final ObjectMapper objectMapper = new ObjectMapper();
			private final Producer producer = new Producer(DEFAULT_EXCHANGE);

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				Test test = objectMapper.readValue(message, Test.class);
				System.out.println("Received: " + test);
				test.setDone(true);
				producer.sendMessage(DOCTOR_PREFIX + test.getDoctorsName(), objectMapper.writeValueAsString(test));
			}
		};
	}
}
