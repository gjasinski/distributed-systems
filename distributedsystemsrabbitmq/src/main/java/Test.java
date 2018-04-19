class Test {
	private final int id;
	private final TestType testType;
	private final String description;
	private boolean done;

	Test(int id, TestType testType, String description) {
		this.id = id;
		this.testType = testType;
		this.description = description;
		this.done = false;
	}

	void makeTest(){
		done = true;
	}

	@Override
	public String toString() {
		return "Test{" +
				"id=" + id +
				", testType=" + testType +
				", description='" + description + '\'' +
				", done=" + done +
				'}';
	}
}
