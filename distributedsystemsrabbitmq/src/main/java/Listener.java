import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Listener {
    private static final String LOCALHOST = "localhost";
    private final String topic;
    private final String exchangeName;

    Listener(String topic, String exchangeName) {
        this.topic = topic;
        this.exchangeName = exchangeName;
    }

    public void run() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(LOCALHOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);

            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, exchangeName, topic);
            channel.basicQos(10);

            Consumer consumer = new DefaultConsumer(channel) {
                private final ObjectMapper objectMapper = new ObjectMapper();
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    Test test = objectMapper.readValue(message, Test.class);
                    System.out.println("Received: " + test);
                }
            };

            // start listening
            System.out.println("Waiting for messages...");
            channel.basicConsume(queueName, true, consumer);
        } catch (Exception ex) {

        }
    }
}
