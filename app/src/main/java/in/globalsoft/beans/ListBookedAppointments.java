package in.globalsoft.beans;

import java.util.List;

public class ListBookedAppointments 
{
	private int code;
	private String message;
	private List<BeanBookedAppointment> appointment_list;
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
	public List<BeanBookedAppointment> getAppointment_list() {
		return appointment_list;
	}
	public void setAppointment_list(List<BeanBookedAppointment> appointment_list) {
		this.appointment_list = appointment_list;
	}

}
