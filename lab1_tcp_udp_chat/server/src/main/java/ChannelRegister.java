import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

class ChannelRegister implements Runnable {
	private Selector selector;
	private final ServerSocketChannel tcpSocketChannel;
	private final ChannelRepository channelRepository;

	ChannelRegister(String host, int port) throws IOException {
		tcpSocketChannel = ServerSocketChannel.open();
		SocketAddress localAddress = new InetSocketAddress(host, port);
		tcpSocketChannel.bind(localAddress);
		selector = createSelector();
		channelRepository = new ChannelRepository();
	}


	@Override
	public void run() {
		try {
			while (true) {
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iter = selectionKeys.iterator();
				while (iter.hasNext()) {

					SelectionKey ky = iter.next();

					if (ky.isAcceptable()) {

						ServerSocketChannel channel = (ServerSocketChannel) ky.channel();
						SocketChannel client = channel.accept();
						ChannelUser userChannel = new ChannelUser(client, channelRepository);
						Thread t = new Thread(userChannel);
						t.start();
						channelRepository.addChannel(userChannel, t);
					}

				}
				iter.remove();

			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	private Selector createSelector() throws IOException {
		Selector selector = Selector.open();
		tcpSocketChannel.configureBlocking(false);
		tcpSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		return selector;
	}
}
