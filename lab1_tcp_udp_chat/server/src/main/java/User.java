public class User {
	private final String username;
	private final ChannelRegister tcpChannel;

	public User(String username, ChannelRegister tcpChannel) {
		this.username = username;
		this.tcpChannel = tcpChannel;
	}

	public String getUsername() {
		return username;
	}
}
