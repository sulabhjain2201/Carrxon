package in.globalsoft.pojo;

import java.util.List;

public class PatientMessagePojo 
{
	private String status;
	private List<PatientPojo> message;


	public List<PatientPojo> getMessage() {
		return message;
	}
	public void setMessage(List<PatientPojo> message) {
		this.message = message;
	}
	public String getStatus() 
	{
		return status;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}



}
