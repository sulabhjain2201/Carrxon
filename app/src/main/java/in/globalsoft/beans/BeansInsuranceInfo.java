package in.globalsoft.beans;

public class BeansInsuranceInfo
{
	int code;
	String message;
	BeansAddInsuranceInfo insurance_info;
	
	public BeansAddInsuranceInfo getInsurance_info() {
		return insurance_info;
	}
	public void setInsurance_info(BeansAddInsuranceInfo insurance_info) {
		this.insurance_info = insurance_info;
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
