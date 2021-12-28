
public class Workstations {
	private int id;
	private int TransT;
	private int SetT;
	private int l;
	private int C;
	private int Type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTransT() {
		return TransT;
	}
	public void setTransT(int transT) {
		TransT = transT;
	}
	public int getSetT() {
		return SetT;
	}
	public void setSetT(int setT) {
		SetT = setT;
	}
	public int getL() {
		return l;
	}
	public void setL(int l) {
		this.l = l;
	}
	public int getC() {
		return C;
	}
	public void setC(int c) {
		C = c;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	public Workstations() {
		super();
	}
	@Override
	public String toString() {
		return "Workstations [id=" + id + ", TransT=" + TransT + ", SetT=" + SetT + ", l=" + l + ", C=" + C + ", Type="
				+ Type + "]";
	}

}
