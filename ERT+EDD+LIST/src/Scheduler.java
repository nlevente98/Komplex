public class Scheduler {
	
	private int jobID;
	private int workstationID;
	
	public Scheduler() {
		super();
	}

	@Override
	public String toString() {
		return "Job ID: " + getJobID() + " is assigned to Workstation ID:" + getWorkstationID();
	}	

	public int getJobID() {
		return jobID;
	}


	public void setJobID(int jobID) {
		this.jobID = jobID;
	}


	public int getWorkstationID() {
		return workstationID;
	}


	public void setWorkstationID(int workstationID) {
		this.workstationID = workstationID;
	}
	
}