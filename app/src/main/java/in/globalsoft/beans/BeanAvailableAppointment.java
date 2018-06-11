package in.globalsoft.beans;

import java.util.List;

public class BeanAvailableAppointment 
{
	private int code;
	private String message;
	private List<String> available_appointments_list;
	public List<String> getAvailable_appointments_list() {
		return available_appointments_list;
	}
	public void setAvailable_appointments_list(
			List<String> available_appointments_list) {
		this.available_appointments_list = available_appointments_list;
	}
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
	

}
