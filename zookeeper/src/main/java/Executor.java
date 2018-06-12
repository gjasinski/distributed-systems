import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener {
	String znode;
	DataMonitor dm;
	ZooKeeper zk;
	String exec[];
	Process child;

	public Executor(String hostPort, String znode, String exec[]) throws IOException {
		this.exec = exec;
		this.znode = znode;
		zk = new ZooKeeper(hostPort, 10000, this);
		dm = new DataMonitor(zk, znode, null, this);
	}

	public static void main(String[] args) {
		if (args.length < 2) {
			System.err
					.println("USAGE: Executor host:Port program [args ...]");
			System.exit(2);
		}
		String hostPort = args[0];
		String znode = "/znode_testowy";
		String exec[] = new String[args.length - 1];
		System.arraycopy(args, 1, exec, 0, exec.length);
		try {
			new Executor(hostPort, znode, exec).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void process(WatchedEvent event) {
		dm.process(event);
	}

	public void run() {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			switch (line) {
				case "tree":
					try {
						printTree(znode);
					} catch (KeeperException | InterruptedException e) {
						e.printStackTrace();
					}
					break;
			}
		}
	}

	private void printTree(String path) throws KeeperException, InterruptedException {
		List<String> children = zk.getChildren(path, dm);
		System.out.println(path);
		children.forEach(c -> {
			try {
				printTree(path + "/" + c);
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
		});

	}


	public void closing(int rc) {
		synchronized (this) {
			notifyAll();
		}
	}

	static class StreamWriter extends Thread {
		OutputStream os;

		InputStream is;

		StreamWriter(InputStream is, OutputStream os) {
			this.is = is;
			this.os = os;
			start();
		}

		public void run() {
			byte b[] = new byte[80];
			int rc;
			try {
				while ((rc = is.read(b)) > 0) {
					os.write(b, 0, rc);
				}
			} catch (IOException e) {
			}

		}
	}

	public void exists(byte[] data) {
		if (data == null) {
			if (child != null) {
				System.out.println("Killing process");
				child.destroy();
				try {
					child.waitFor();
				} catch (InterruptedException e) {
				}
			}
			child = null;
		} else {
			if (child != null) {
				System.out.println("Stopping child");
				child.destroy();
				try {
					child.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				zk.getChildren(znode, this);
				System.out.println("Starting child");
				child = Runtime.getRuntime().exec(exec);
				new StreamWriter(child.getInputStream(), System.out);
				new StreamWriter(child.getErrorStream(), System.err);
			} catch (IOException | InterruptedException | KeeperException e) {
				e.printStackTrace();
			}
		}
	}
}
