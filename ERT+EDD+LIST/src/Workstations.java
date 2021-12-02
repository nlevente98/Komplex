
public class Workstations {
	private int id;
	private int l;
	private long C;

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

	@Override
	public String toString() {
		return "Workstations [id=" + id + ", l=" + l + ", C=" + C + "]";
	}


	public Workstations() {
		super();
	}

}
