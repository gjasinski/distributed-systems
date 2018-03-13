public class Packet implements Comparable<Packet>{
	private int hash;
	private int part;
	private int parts;
	private String user;
	private String message;

	public Packet() {
	}

	Packet(int hash, int part, int parts, String user, String message) {
		this.hash = hash;
		this.part = part;
		this.parts = parts;
		this.user = user;
		this.message = message;
	}

	public int getHash() {
		return hash;
	}

	public void setHash(int hash) {
		this.hash = hash;
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public int getParts() {
		return parts;
	}

	public void setParts(int parts) {
		this.parts = parts;
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

	@Override
	public int compareTo(Packet packet) {
		return Integer.compare(part, packet.getPart());
	}
}
