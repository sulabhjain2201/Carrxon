package in.globalsoft.beans;

import java.util.List;

public class BeansDoctorList
{
	int code;
	String message;
	List<BeansDoctorInfo> doctor_list;
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
	public List<BeansDoctorInfo> getDoctor_list() {
		return doctor_list;
	}
	public void setDoctor_list(List<BeansDoctorInfo> doctor_list) {
		this.doctor_list = doctor_list;
	}

}
