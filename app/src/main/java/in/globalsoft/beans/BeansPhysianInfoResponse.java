package in.globalsoft.beans;

public class BeansPhysianInfoResponse 
{
	int code;
	String message;
	BeansPhysicianInfo doctor_details;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BeansPhysicianInfo getDoctor_details() {
		return doctor_details;
	}
	public void setDoctor_details(BeansPhysicianInfo doctor_details) {
		this.doctor_details = doctor_details;
	}

}
