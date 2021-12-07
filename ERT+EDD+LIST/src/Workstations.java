
public class Workstations {
	private int id;
	private int l;
	private long C;
	private int start;
	private int end;
	private int Type;

	public int getType() {
		return Type;
	}


	public void setType(int type) {
		Type = type;
	}


	public int getStart() {
		return start;
	}


	public void setStart(int start) {
		this.start = start;
	}


	public int getEnd() {
		return end;
	}


	public void setEnd(int end) {
		this.end = end;
	}


	public long getC() {
		return C;
	}


	public void setC(long c) {
		C = c;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public Workstations() {
		super();
	}


	@Override
	public String toString() {
		return "Workstations [id=" + id + ", l=" + l + ", C=" + C + ", start=" + start + ", end=" + end + ", Type="
				+ Type + "]";
	}

	

}
