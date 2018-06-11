package in.globalsoft.pojo;

import java.util.List;

public class DoctorMessagePojo 
{
	private String status;
	private List<DoctorPojo> message;


	public List<DoctorPojo> getMessage() {
		return message;
	}
	public void setMessage(List<DoctorPojo> message) {
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
