import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;
import java.util.List;

public class DataMonitor implements Watcher, StatCallback {
	ZooKeeper zk;
	String znode;
	Watcher chainedWatcher;
	boolean dead;
	DataMonitorListener listener;
	byte prevData[];

	public DataMonitor(ZooKeeper zk, String znode, Watcher chainedWatcher,
	                   DataMonitorListener listener) {
		this.zk = zk;
		this.znode = znode;
		this.chainedWatcher = chainedWatcher;
		this.listener = listener;
		zk.exists(znode, true, this, null);
	}

	public interface DataMonitorListener {
		void exists(byte data[]);

		void closing(int rc);
	}

	public void process(WatchedEvent event) {
		String path = event.getPath();
		if (event.getType() == Event.EventType.None) {
			switch (event.getState()) {
				case SyncConnected:
					break;
				case Expired:
					dead = true;
					listener.closing(KeeperException.Code.SessionExpired);
					break;
			}
		} else if(event.getType() == Event.EventType.NodeChildrenChanged) {
				System.out.println(count(znode) + 1);
		} else {
			if (path != null && path.equals(znode)) {
				// Something has changed on the node, let's find out
				zk.exists(znode, true, this, null);
			}
		}
		if (chainedWatcher != null) {
			chainedWatcher.process(event);
		}
	}

	private long count(String path) {
		try {
			List<String> children = zk.getChildren(path, this);
			return children.size() + children.stream().mapToLong(c -> count(path+"/"+c)).sum();
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void processResult(int rc, String path, Object ctx, Stat stat) {
		boolean exists;
		switch (rc) {
			case Code.Ok:
				exists = true;
				break;
			case Code.NoNode:
				exists = false;
				break;
			case Code.SessionExpired:
			case Code.NoAuth:
				dead = true;
				listener.closing(rc);
				return;
			default:
				// Retry errors
				zk.exists(znode, true, this, null);
				return;
		}

		byte b[] = null;
		if (exists) {
			try {
				b = zk.getData(znode, false, null);
			} catch (KeeperException e) {
				// We don't need to worry about recovering now. The watch
				// callbacks will kick off any exception handling
				e.printStackTrace();
			} catch (InterruptedException e) {
				return;
			}
		}
		if ((b == null && b != prevData)
				|| (b != null && !Arrays.equals(prevData, b))) {
			listener.exists(b);
			prevData = b;
		}
	}
}