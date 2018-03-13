import java.util.LinkedList;
import java.util.List;

public class Message {
	private String user;
	private String message;
	private static int PACKET_SIZE = 10;

	Message(String user, String message) {
		this.user = user;
		this.message = message;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	List<Packet> getMessagesList() {
		List<Packet> list = new LinkedList<>();
		int hashCode = this.hashCode();
		int parts = getNumberOfParts();
		int part = 0;
		for (int l = 0; l < message.length(); l += PACKET_SIZE) {
			part++;
			int min = Integer.min(message.length(), l + PACKET_SIZE);
			String partOfMessage = message.substring(l, min);
			list.add(new Packet(hashCode, part, parts, this.user, partOfMessage));
		}
		return list;
	}

	private int getNumberOfParts() {
		int parts = message.length() / PACKET_SIZE;
		if (parts * PACKET_SIZE < message.length()) {
			parts++;
		}
		return parts;
	}

	@Override
	public String toString() {
		return user + ": " + message;
	}
}
