	public class Resources {
	private int id;
	private TimeWindows[] time;
	private int tw;
	private int number;
	private int startT;
	private int endT;
	private int type;
	private int l;
	private int c;
	private String sch;

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

	public TimeWindows[] getTime() {
		return time;
	}

	public void setTime(TimeWindows[] time) {
		this.time = time;
	}

	public int getTw() {
		return tw;
	}

	public void setTw(int tw) {
		this.tw = tw;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public String getSch() {
		return sch;
	}

	public void setSch(String sch) {
		this.sch = sch;
	}

	public Resources() {
		super();
	}

	public Resources(int a) {
		super();
		time = new TimeWindows[a];
	}

	@Override
	public String toString() {
		return id + ".Resource:\nId: " + getId() + "\tComputing time:" + getC() + "\tWorks done:" + getL()
				+ "\tTimeWindows: " + getTw();
	}

}