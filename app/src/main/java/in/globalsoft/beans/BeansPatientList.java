package in.globalsoft.beans;

import java.util.List;

public class BeansPatientList 

{
	int code;
	String message;
	List<BeansPatientInfo> member_list;
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
	public List<BeansPatientInfo> getMember_list() {
		return member_list;
	}
	public void setMember_list(List<BeansPatientInfo> member_list) {
		this.member_list = member_list;
	}

}
