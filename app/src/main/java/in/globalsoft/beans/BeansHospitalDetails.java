package in.globalsoft.beans;

import java.util.List;

public class BeansHospitalDetails 
{
	String address;
	String doctor_phone;
	String doctor_image;
	String fees;
	public String getFees() {
		return fees;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}
	List<BeansDoctorDetails> doctor_details;
	List<BeansHospitalSchduleDetails> doctor_schedule_details;
	private List<String> available_appointments_list;
	private String education;
	private String services;
	private List<String> doc_insurance_name;
	private String speciality;
	private String experience;
	private List<String> language;
	private String doctor_name;
	public List<String> getAvailable_appointments_list() {
		return available_appointments_list;
	}
	public void setAvailable_appointments_list(
			List<String> available_appointments_list) {
		this.available_appointments_list = available_appointments_list;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDoctor_phone() {
		return doctor_phone;
	}
	public void setDoctor_phone(String doctor_phone) {
		this.doctor_phone = doctor_phone;
	}
	public String getDoctor_image() {
		return doctor_image;
	}
	public void setDoctor_image(String doctor_image) {
		this.doctor_image = doctor_image;
	}
	public List<BeansDoctorDetails> getDoctor_details() {
		return doctor_details;
	}
	public void setDoctor_details(List<BeansDoctorDetails> doctor_details) {
		this.doctor_details = doctor_details;
	}
	public List<BeansHospitalSchduleDetails> getDoctor_schedule_details() {
		return doctor_schedule_details;
	}
	public void setDoctor_schedule_details(
			List<BeansHospitalSchduleDetails> doctor_schedule_details) {
		this.doctor_schedule_details = doctor_schedule_details;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public List<String> getLanguage() {
		return language;
	}
	public void setLanguage(List<String> language) {
		this.language = language;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public List<String> getDoc_insurance_name() {
		return doc_insurance_name;
	}
	public void setDoc_insurance_name(List<String> doc_insurance_name) {
		this.doc_insurance_name = doc_insurance_name;
	}
	
	
}
