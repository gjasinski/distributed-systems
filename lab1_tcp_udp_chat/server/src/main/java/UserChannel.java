import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class UserChannel implements Runnable{
	private final Selector selector;
//	private final ObjectMapper objectMapper = new ObjectMapper();

	public UserChannel(SocketChannel channel) throws IOException {
		selector = Selector.open();
		channel.configureBlocking(false);
		channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}

	@Override
	public void run() {
		try {
			while(true) {
				int select = selector.select();

				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iter = selectionKeys.iterator();
				while (iter.hasNext()) {
					SelectionKey ky = iter.next();
					if (ky.isReadable()) {
						SocketChannel client = (SocketChannel) ky.channel();
						ByteBuffer buffer = ByteBuffer.allocate(256);
						client.read(buffer);
						String output = new String(buffer.array()).trim();
						System.out.println("Message read from client: " + output);
						if (output.equals("Bye.")) {
							client.close();
							System.out.println("Client messages are complete; close.");
						}
					}
//					System.out.println(ky);
//					if(ky.isConnectable()){
//						System.out.println("conn");
//					}
//					if(ky.isAcceptable()){
//						System.out.println("acc");
//					}
//					if(ky.isValid()){
//						System.out.println("valid");
//					}
//					Thread.sleep(3000);
					iter.remove();
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
