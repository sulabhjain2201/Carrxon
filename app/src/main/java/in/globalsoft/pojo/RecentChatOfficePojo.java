package in.globalsoft.pojo;

import java.util.List;

public class RecentChatOfficePojo 
{
	private String status;
	private List<MessagePatientPojo> message;


	public List<MessagePatientPojo> getMessage() {
		return message;
	}
	public void setMessage(List<MessagePatientPojo> message) {
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
