package in.globalsoft.pojo;

import java.util.List;

public class ChatListPojo 
{
	private String status;
	private List<ChatPojo> message;
	
	public List<ChatPojo> getMessage() {
		return message;
	}
	public void setMessage(List<ChatPojo> message) {
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
