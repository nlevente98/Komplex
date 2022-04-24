public class Goals {
	String name;
	double tMax;
	double eMax;
	double tSum;
	double eSum;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getTmax() {
		return tMax;
	}
	
	public void setTmax(double tmax) {
		this.tMax = tmax;
	}
	
	public double getEmax() {
		return eMax;
	}
	
	public void setEmax(double emax) {
		this.eMax = emax;
	}

	public double getTsum() {
		return tSum;
	}

	public void setTsum(double tsum) {
		this.tSum = tsum;
	}

	public double getEsum() {
		return eSum;
	}

	public void setEsum(double esum) {
		this.eSum = esum;
	}

	public Goals() {
		super();
	}

	@Override
	public String toString() {
		return "Goals [name=" + name + ", tMax=" + tMax + ", eMax=" + eMax + ", Tsum=" + tSum + ", Esum=" + eSum + "]";
	}
	
}
