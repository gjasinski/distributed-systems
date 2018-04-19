import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class Producer {
	private static final String LOCALHOST = "localhost";
	private final Channel channel;
	private final String exchangeName;

	Producer(String exchangeName) throws IOException, TimeoutException {
		this.exchangeName = exchangeName;
		channel = createChannel(exchangeName);
	}

	private Channel createChannel(String exchangeName) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(LOCALHOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
		return channel;
	}

	void sendMessage(String topic, String message) throws IOException {
		channel.basicPublish(exchangeName, topic, null, message.getBytes("UTF-8"));
	}

}
