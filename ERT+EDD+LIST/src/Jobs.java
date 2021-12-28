
public class Jobs {
	private int Id;
	private int ProcT;
	private int StartT;
	private int EndT;
	private int Type;
	private int d;
	private int L;
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getProcT() {
		return ProcT;
	}

	public void setProcT(int procT) {
		ProcT = procT;
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

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public int getL() {
		return L;
	}

	public void setL(int l) {
		L = l;
	}

	public Jobs() {
		super();
	}



	@Override
	public String toString() {
		return "Jobs [Id=" + Id + ", ProcT=" + ProcT + ", StartT=" + StartT + ", EndT=" + EndT + ", Duedate=" + d + ", Lateness=" + L + ", Type="
				+ Type + "]";
	}
	

}
