package in.globalsoft.beans;

public class BeansHospitalInfo 
{
	String lat;
	String lon;
	String name;
	String vicinity;
	String doctor_id;
	private String speciality_id;
	public String getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}
	String open_status;
	String wating_time;
	String next_appointment_time;
	String phone;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVicinity() {
		return vicinity;
	}
	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public String getOpen_status() {
		return open_status;
	}
	public void setOpen_status(String open_status) {
		this.open_status = open_status;
	}
	public String getWating_time() {
		return wating_time;
	}
	public void setWating_time(String wating_time) {
		this.wating_time = wating_time;
	}
	public String getNext_appointment_time() {
		return next_appointment_time;
	}
	public void setNext_appointment_time(String next_appointment_time) {
		this.next_appointment_time = next_appointment_time;
	}

	public String getSpeciality_id() {
		return speciality_id;
	}

	public void setSpeciality_id(String speciality_id) {
		this.speciality_id = speciality_id;
	}
}
