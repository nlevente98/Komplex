public class Jobs {
	private int id;
	private int procT;
	private int startT;
	private int endT;
	private int type;
	private int d;
	private int l;
	private int setUp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProcT() {
		return procT;
	}

	public void setProcT(int procT) {
		this.procT = procT;
	}

	public int getStartT() {
		return startT;
	}

	public void setStartT(int startT) {
		this.startT = startT;
	}

	public int getEndT() {
		return endT;
	}

	public void setEndT(int endT) {
		this.endT = endT;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public int getSetUp() {
		return setUp;
	}

	public void setSetUp(int setUp) {
		this.setUp = setUp;
	}

	public Jobs() {
		super();
	}

	@Override
	public String toString() {
		return id + ".job:\nProccessing time:" + procT + "\tStart time:" + startT + "\tSet up time:" + setUp
				+ "\tEnd time:" + endT + "\tDuedate:" + d + "\tLateness:" + l + "\n";
	}

}