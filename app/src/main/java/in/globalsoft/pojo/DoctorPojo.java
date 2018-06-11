package in.globalsoft.pojo;

import java.io.Serializable;

public class DoctorPojo implements Serializable
{
	private String doctor_name;
	private String doctor_email;
	private String doctor_address;
	private String doctor_phone;
	private boolean isonline;
	private String doctor_id;
	private String chat_id="";
	private  String chat="";
	
	
	
	
	
	
	public String getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}
	public String getChat_id() {
		return chat_id;
	}
	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}
	public String getChat() {
		return chat;
	}
	public void setChat(String chat) {
		this.chat = chat;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getDoctor_email() {
		return doctor_email;
	}
	public void setDoctor_email(String doctor_email) {
		this.doctor_email = doctor_email;
	}
	public String getDoctor_address() {
		return doctor_address;
	}
	public void setDoctor_address(String doctor_address) {
		this.doctor_address = doctor_address;
	}
	public String getDoctor_phone() {
		return doctor_phone;
	}
	public void setDoctor_phone(String doctor_phone) {
		this.doctor_phone = doctor_phone;
	}
	public boolean isIsonline() {
		return isonline;
	}
	public void setIsonline(boolean isonline) {
		this.isonline = isonline;
	}

}
