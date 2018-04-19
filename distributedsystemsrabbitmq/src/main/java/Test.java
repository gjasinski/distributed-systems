class Test {
	private int id;
	private String doctorsName;
	private String name;
	private TestType testType;
	private boolean done;

	public Test() {
	}

	public Test(int id, String doctorsName, String name, TestType testType) {
		this.id = id;
		this.doctorsName = doctorsName;
		this.name = name;
		this.testType = testType;
		this.done = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDoctorsName() {
		return doctorsName;
	}

	public void setDoctorsName(String doctorsName) {
		this.doctorsName = doctorsName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TestType getTestType() {
		return testType;
	}

	public void setTestType(TestType testType) {
		this.testType = testType;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		return "Test{" +
				"id=" + id +
				", doctorsName='" + doctorsName + '\'' +
				", name='" + name + '\'' +
				", testType=" + testType +
				", done=" + done +
				'}';
	}
}
