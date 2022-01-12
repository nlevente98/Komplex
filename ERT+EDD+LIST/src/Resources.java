public class Resources {
	private int id;
	private int l;
	private int C;
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


	public int getC() {
		return C;
	}


	public void setC(int c) {
		this.C = c;
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

	public Resources() {
		super();
	}

	@Override
	public String toString() {
		return id +".workstations\nStart time:" + start + "\tEnd time:" + end + "\tComputing time:" + C + "\tWorks done:" + l + "\n";
	}

}