package in.globalsoft.beans;

import java.util.List;

public class BeansHospitalList
{
	int code;
	String message;
	List<BeansHospitalInfo> hospital_list;
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
	public List<BeansHospitalInfo> getHospital_list() {
		return hospital_list;
	}
	public void setHospital_list(List<BeansHospitalInfo> hospital_list) {
		this.hospital_list = hospital_list;
	}

}
