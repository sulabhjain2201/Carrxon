package in.globalsoft.beans;


public class BeanSpecificHospitalDetails
{
	int code;
	String message;
	BeansHospitalDetails doctor_details;
	
	

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
	public BeansHospitalDetails getDoctor_details() {
		return doctor_details;
	}
	public void setDoctor_details(BeansHospitalDetails doctor_details) {
		this.doctor_details = doctor_details;
	}

	

}
