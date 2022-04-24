public class Scheduler {
	
	private int jobID;
	private int resourceID;

	public int getJobID() {
		return jobID;
	}

	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	public int getResourceID() {
		return resourceID;
	}

	public void setResourceID(int resourceID) {
		this.resourceID = resourceID;
	}
	
	public Scheduler() {
		super();
	}

	@Override
	public String toString() {
		return "Job ID: " + getJobID() + " is assigned to Resource ID:" + getResourceID();
	}	
	
}