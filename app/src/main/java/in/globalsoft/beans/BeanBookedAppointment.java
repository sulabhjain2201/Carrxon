package in.globalsoft.beans;

public class BeanBookedAppointment 
{
	private String patient_name;
	private String phone;
	private String email;
	private String appointment_time;
	private String symtoms;
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAppointment_time() {
		return appointment_time;
	}
	public void setAppointment_time(String appointment_time) {
		this.appointment_time = appointment_time;
	}
	public String getSymtoms() {
		return symtoms;
	}
	public void setSymtoms(String symtoms) {
		this.symtoms = symtoms;
	}

}
