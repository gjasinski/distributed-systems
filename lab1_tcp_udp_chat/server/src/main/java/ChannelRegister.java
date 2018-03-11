import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class ChannelRegister implements Runnable {
	private final SocketAddress localAddress;
	private final ServerSocketChannel tcpSocketChannel;
	private Selector selector;
	private List<UserChannel> channels;

	ChannelRegister() throws IOException {
		tcpSocketChannel = ServerSocketChannel.open();
		localAddress = tcpSocketChannel.getLocalAddress();
		selector = createSelector();
		channels = new LinkedList<>();
	}

	ChannelRegister(String host, int port) throws IOException {
		tcpSocketChannel = ServerSocketChannel.open();
		localAddress = new InetSocketAddress(host, port);
		tcpSocketChannel.bind(localAddress);
		selector = createSelector();
		channels = new LinkedList<>();
	}


	@Override
	public void run() {
		ByteBuffer inputBuffer = ByteBuffer.allocate(1024);
		try {
			while(true) {

				int noOfKeys = selector.select();

				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iter = selectionKeys.iterator();

				while (iter.hasNext()) {

					SelectionKey ky = iter.next();

					if (ky.isAcceptable()) {

						// Accept the new client connection
						ServerSocketChannel channel = (ServerSocketChannel) ky.channel();
						SocketChannel client = channel.accept();
						UserChannel userChannel = new UserChannel(client);
						Thread t = new Thread(userChannel);
						t.start();
//						System.out.println(client);
//						client.configureBlocking(false);
//
//						// Add the new connection to the selector
//						client.register(selector, SelectionKey.OP_READ);
//
//						System.out.println("Accepted new connection from client: " + client);
					} /*else if (ky.isReadable()) {

						// Read the data from client

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
					if(ky.isConnectable()){
						System.out.println("connectable");
					}
*/
					iter.remove();

				} // end while loop

			} // end for loop
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	private Selector createSelector() throws IOException {
		Selector selector = Selector.open();

		tcpSocketChannel.configureBlocking(false);

		SelectionKey key = tcpSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

//		Selector selector = Selector.open();
//		tcpSocketChannel.configureBlocking(false);
//		tcpSocketChannel.register(selector, SelectionKey.OP_READ);
		return selector;
	}
}
