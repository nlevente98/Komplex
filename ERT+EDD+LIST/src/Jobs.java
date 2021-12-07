
public class Jobs {
	private int OperationT;
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
	
	public Jobs() {
		super();
	}

	public int getOperationT() {
		return OperationT;
	}

	public void setOperationT(int operationT) {
		OperationT = operationT;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}

	public long getStartT() {
		return StartT;
	}

	public void setStartT(int startT) {
		StartT = startT;
	}

	public long getEndT() {
		return EndT;
	}

	public void setEndT(int endT) {
		EndT = endT;
	}

	@Override
	public String toString() {
		return "Jobs [OperationT=" + OperationT + ", Id=" + Id + ", StartT=" + StartT + ", EndT=" + EndT + ", Duedate=" + d + ", Lateness=" + L + ", Type="
				+ Type + "]";
	}
	

}
