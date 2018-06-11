package in.globalsoft.beans;

import java.util.List;

public class BeansDoctorGoogleAddressList extends BeansResponse
{
	List<BeansDoctorGoogleInfo> clinic_addresses_list;

	public List<BeansDoctorGoogleInfo> getClinic_addresses_list() {
		return clinic_addresses_list;
	}

	public void setClinic_addresses_list(
			List<BeansDoctorGoogleInfo> clinic_addresses_list) {
		this.clinic_addresses_list = clinic_addresses_list;
	}

}
