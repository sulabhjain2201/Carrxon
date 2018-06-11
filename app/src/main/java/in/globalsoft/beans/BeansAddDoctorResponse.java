package in.globalsoft.beans;

public class BeansAddDoctorResponse
{
	int code;
	String message;
	String doctor_id;
	public String getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}
	public int getCode() 
	{
		return code;
	}
	public void setCode(int code) 
	{
		this.code = code;
	}
	public String getMessage() 
	{
		return message;
	}
	public void setMessage(String message) 
	{
		this.message = message;
	}
	

}
