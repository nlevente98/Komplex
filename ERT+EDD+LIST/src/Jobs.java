public class Jobs {
	private int ProcT;
	private int Id;
	private int StartT;
	private int EndT;
	private int Type;
	private int d;
	private int L;
	
	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public int getL() {
		return L;
	}

	public void setL(long m) {
		L = (int) m;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public int getProcT() {
		return ProcT;
	}

	public void setProcT(int procT) {
		ProcT = procT;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}

	public int getStartT() {
		return StartT;
	}

	public void setStartT(int startT) {
		StartT = startT;
	}

	public int getEndT() {
		return EndT;
	}

	public void setEndT(int endT) {
		EndT = endT;
	}

	public Jobs() {
		super();
	}

	@Override
	public String toString() {
		return Id + ".job:\nProccessing time:" + ProcT + "\tStart time:" + StartT +"\tEnd time:" + EndT + "\tDuedate:" + d + "\tLateness:" + L + "\n";
	}
	

}