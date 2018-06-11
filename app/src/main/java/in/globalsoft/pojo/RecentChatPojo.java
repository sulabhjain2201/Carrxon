package in.globalsoft.pojo;

import java.util.List;



public class RecentChatPojo 
{
	private String status;
	private List<MessagePojo> message;


	public List<MessagePojo> getMessage() {
		return message;
	}
	public void setMessage(List<MessagePojo> message) {
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
