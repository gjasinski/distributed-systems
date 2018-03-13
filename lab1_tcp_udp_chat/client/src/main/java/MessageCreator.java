import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MessageCreator {
	private List<Packet> packets = new LinkedList<>();
	private int hash;
	private int parts;
	private String user;

	public MessageCreator(Packet packet) {
		packets.add(packet);
		hash = packet.getHash();
		parts = packet.getParts();
		user = packet.getUser();
	}

	void addPacket(Packet packet) {
		System.out.println("add packet " + packet);
		if (hash != packet.getHash() || isPacketDuplicate(packet)) {
			throw new IllegalArgumentException("Packet hash is different than messagecreators");
		}
		packets.add(packet);
	}

	private boolean isPacketDuplicate(Packet packet){
		return !isPacketNotDuplicate(packet);
	}

	private boolean isPacketNotDuplicate(Packet packet) {
		return packets.stream()
				.noneMatch(p -> p.getPart() == packet.getPart());
	}

	Optional<Message> getMessage() {
		if (packets.size() < parts){
			return Optional.empty();
		}
		StringBuilder builder = new StringBuilder();
		packets.sort(Packet::compareTo);
		packets.forEach(packet -> builder.append(packet.getMessage()));
		return Optional.of(new Message(user, builder.toString()));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MessageCreator that = (MessageCreator) o;
		return hash == that.hash;
	}

	@Override
	public int hashCode() {
		return hash;
	}

	public boolean areAllPacketReceived() {
		System.out.println(parts + " " + packets.size());
		return parts == packets.size();
	}
}
