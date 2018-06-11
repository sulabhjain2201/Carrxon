package in.globalsoft.pojo;

import java.io.Serializable;

public class MessagePatientPojo implements Serializable
{

private	String user_id;
private String friend_id;
private String chat_id;
private String chat;
private PatientPojo patient_detail;



public PatientPojo getPatient() {
	return patient_detail;
}
public void setPatient(PatientPojo patient) {
	this.patient_detail = patient;
}
public String getUser_id() {
	return user_id;
}
public void setUser_id(String user_id) {
	this.user_id = user_id;
}
public String getFriend_id() {
	return friend_id;
}
public void setFriend_id(String friend_id) {
	this.friend_id = friend_id;
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
	



}
