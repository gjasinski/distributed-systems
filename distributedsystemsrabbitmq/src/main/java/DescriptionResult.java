public class DescriptionResult implements Result {
	private final int id;
	private final String description;

	public DescriptionResult(int id, String description) {
		this.id = id;
		this.description = description;
	}

	@Override
	public String toString() {
		return "DescriptionResult{" +
				"id=" + id +
				", description='" + description + '\'' +
				'}';
	}
}
