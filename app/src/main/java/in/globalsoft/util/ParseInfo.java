package in.globalsoft.util;

import in.globalsoft.beans.BeansAddPatient;
import in.globalsoft.beans.BeansHospitalInfo;
import in.globalsoft.beans.BeansHospitalList;
import in.globalsoft.beans.BeansLogin;
import in.globalsoft.preferences.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;

public class ParseInfo
{
	AppPreferences userInfoPref;
	BeansLogin loginBeans;
	Context con;

	public BeansLogin parseLogin(String res,Context con)
	{
		userInfoPref = new AppPreferences(con);
		 loginBeans = new BeansLogin();
		try {
			JSONObject obj = new JSONObject(res);
			int code = Integer.parseInt(obj.getString("code"));
			loginBeans.setCode(code);
			loginBeans.setMessage(obj.getString("message"));
			if(code==200)
			{
				JSONObject obj_patient = obj.getJSONObject("patient_details");
				loginBeans.setPatient_id(obj_patient.getString("patient_id"));
				loginBeans.setFirst_name(obj_patient.getString("first_name"));
				
				loginBeans.setLast_name(obj_patient.getString("last_name"));
				loginBeans.setAddress(obj_patient.getString("address"));
				loginBeans.setZip(obj_patient.getString("zip"));
				loginBeans.setCity(obj_patient.getString("city"));
				loginBeans.setState(obj_patient.getString("state"));
				loginBeans.setHome_phone(obj_patient.getString("home_phone"));
				loginBeans.setWork_phone(obj_patient.getString("work_phone"));
				loginBeans.setEmail(obj_patient.getString("email"));
				
				saveUserInfo();
				
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginBeans;
		
	}
	
	public BeansLogin parseRegister(String res,Context con,int isSaveRequired)
	{
		userInfoPref = new AppPreferences(con);
		 loginBeans = new BeansLogin();
		try {
			JSONObject obj = new JSONObject(res);
			int code = Integer.parseInt(obj.getString("code"));
			loginBeans.setCode(code);
			loginBeans.setMessage(obj.getString("message"));
			if(code==200)
			{
				JSONObject obj_patient = obj.getJSONObject("patient_details");
				loginBeans.setPatient_id(obj_patient.getString("patient_id"));
				loginBeans.setFirst_name(obj_patient.getString("first_name"));
				
				loginBeans.setLast_name(obj_patient.getString("last_name"));
				loginBeans.setAddress(obj_patient.getString("address"));
				loginBeans.setZip(obj_patient.getString("zip"));
				loginBeans.setCity(obj_patient.getString("city"));
				loginBeans.setState(obj_patient.getString("state"));
				loginBeans.setHome_phone(obj_patient.getString("home_phone"));
				loginBeans.setWork_phone(obj_patient.getString("work_phone"));
				loginBeans.setEmail(obj_patient.getString("email"));
				if(isSaveRequired == 1)
				saveUserInfo();
				
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginBeans;
	}
	
	public BeansAddPatient parseAddPatient(String res)
	{
		BeansAddPatient addPatientBeans = new BeansAddPatient();
		try {
			JSONObject obj = new JSONObject(res);
			int code = Integer.parseInt(obj.getString("code"));
			addPatientBeans.setCode(code);
			addPatientBeans.setMessage(obj.getString("message"));
			addPatientBeans.setMember_id(obj.getString("member_id"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addPatientBeans;
		
	}
	
	public BeansHospitalList parseHospitalInfo(String res)
	{
		BeansHospitalList hospitalListBeans = new BeansHospitalList();
		List<BeansHospitalInfo> list_HospitalInfo = new ArrayList<BeansHospitalInfo>();
		try
		{
			JSONObject obj = new JSONObject(res);
			int code = Integer.parseInt(obj.getString("code"));
			hospitalListBeans.setCode(code);
			hospitalListBeans.setMessage(obj.getString("message"));
			if(code == 200) {
				JSONArray array = obj.getJSONArray("hospital_list");
				for (int i = 0; i < array.length(); i++) {
					BeansHospitalInfo hospitalInfoBeans = new BeansHospitalInfo();
					JSONObject obj_info = array.getJSONObject(i);
					hospitalInfoBeans.setDoctor_id(obj_info.getString("doctor_id"));

					hospitalInfoBeans.setLat(obj_info.optString("lat"));
					hospitalInfoBeans.setLon(obj_info.optString("lon"));
					hospitalInfoBeans.setName(obj_info.getString("name"));
					hospitalInfoBeans.setNext_appointment_time(obj_info.getString("next_appointment_time"));
					hospitalInfoBeans.setOpen_status(obj_info.getString("open_status"));
					hospitalInfoBeans.setVicinity(obj_info.getString("vicinity"));
					hospitalInfoBeans.setWating_time(obj_info.getString("waiting_time"));
					hospitalInfoBeans.setPhone(obj_info.getString("phone"));
					hospitalInfoBeans.setSpeciality_id(obj_info.optString("speciality_id"));
					list_HospitalInfo.add(hospitalInfoBeans);
				}
				hospitalListBeans.setHospital_list(list_HospitalInfo);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hospitalListBeans;
	}
	
	public void saveUserInfo()
	{
		Gson gson = new Gson();
		String userLoginInfo = gson.toJson(loginBeans);
		userInfoPref.saveUserLoginInfo(userLoginInfo);
		userInfoPref.setUserId(loginBeans.getPatient_id());
	}
}
