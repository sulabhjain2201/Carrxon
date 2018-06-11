package in.globalsoft.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences
{
	
	private static final String APP_SHARED_PREFS = "in.globalsoft.carxon.userInfo"; //  Name of the file -.xml
	private static final String APP_SHARED_PREFS_PERMANENT = "in.globalsoft.urncr";
	private SharedPreferences appSharedPrefs,appSharePrefPer;
	 private Editor prefsEditor,prefEditorPerm;
	 private Context context;

	 public AppPreferences(Context context)
	 {
	  this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
	  this.prefsEditor = appSharedPrefs.edit();

		 this.appSharePrefPer = context.getSharedPreferences(APP_SHARED_PREFS_PERMANENT, Activity.MODE_PRIVATE);
		 this.prefEditorPerm = appSharePrefPer.edit();


	  this.context=context;
	 }



	
	 
	 public String getUserLoginInfo()
	 {
	  return appSharedPrefs.getString("userLoginInfo", "");
	 }
	 
	 public void saveUserLoginInfo(String text)
	 {
	  prefsEditor.putString("userLoginInfo", text);
	  prefsEditor.commit();

	 }
	 
	 public String getUserId()
	 {
	  return appSharedPrefs.getString("user_id", "");
	 }
	 
	 public void setUserId(String id)
	 {
	  prefsEditor.putString("user_id", id);
	  prefsEditor.commit();

	 }


	 public String getOfficeId()
	 {
	  return appSharedPrefs.getString("office_id", "");
	 }
	 
	 public void saveOfficeId(String text)
	 {
	  prefsEditor.putString("office_id", text);
	  prefsEditor.commit();

	 }

	 
	 
	 
	 public String getDoctorId()
	 {
	  return appSharedPrefs.getString("doctor_id", "");
	 }
	 
	 public void saveDoctorId(String text)
	 {
	  prefsEditor.putString("doctor_id", text);
	  prefsEditor.commit();

	 }
	 

	 public boolean isDoctorOffice()
	 {
	  return appSharedPrefs.getBoolean("doctor_office", false);
	 }
	 
	 public void setDoctorOffice(Boolean doctorOffice)
	 {
	  prefsEditor.putBoolean("doctor_office", doctorOffice);
	  prefsEditor.commit();

	 }

	 
	 
	 
	 
	 
	 
	 public String getCurrentChatFriendId()
	 {
	  return appSharedPrefs.getString("frnd_id", "");
	 }
	 
	 public void setCurrentChatFriendId(String id)
	 {
	  prefsEditor.putString("frnd_id", id);
	  prefsEditor.commit();

	 }
	 
	 public String getMapType()
	 {
	  return appSharedPrefs.getString("mapType", "");
	 }
	 
	 public void saveMapType(String text)
	 {
	  prefsEditor.putString("mapType", text);
	  prefsEditor.commit();

	 }
	 
	 public int getLoginState()
	 {
	  return appSharedPrefs.getInt("loginState", 0);
	 }
	 
	 public void saveLoginState(int text)
	 {
	  prefsEditor.putInt("loginState", text);
	  prefsEditor.commit();

	 }
	 
	 public int getInsuranceState()
	 {
	  return appSharedPrefs.getInt("insuranceState", 0);
	 }
	 
	 public void saveInsuranceState(int text)
	 {
	  prefsEditor.putInt("insuranceState", text);
	  prefsEditor.commit();

	 }
	 
	 public int getInsuranceScreen()
	 {
	  return appSharedPrefs.getInt("insuranceScreen", 0);
	 }
	 
	 public void isInsuranceScreen(int text)
	 {
	  prefsEditor.putInt("insuranceScreen", text);
	  prefsEditor.commit();

	 }

    //login type -1=no login,0=patient login,1=doctor login and 2=doctor-office login
    public void setLoginType(int type)
    {
        prefsEditor.putInt("logintype",type);
        prefsEditor.commit();
    }

    public int getLogintype()
    {
        return appSharedPrefs.getInt("logintype",-1);
    }


	 
//	 public int getDoctorLogin()
//	 {
//	  return appSharedPrefs.getInt("doctor_login", 0);
//	 }
	 
//	 public void isDoctorLogin(int text)
//	 {
//	  prefsEditor.putInt("doctor_login", text);
//	  prefsEditor.commit();
//
//	 }
	 
	 public void clearPref()
	 {
		 
		 prefsEditor.clear();
		 prefsEditor.commit();
	 }
	 
	 public String getDoctorIdByPatient()
	 {
	  return appSharedPrefs.getString("doctorId_Patient", "");
	 }
	 
	 public void saveDoctorIdByPatient(String text)
	 {
	  prefsEditor.putString("doctorId_Patient", text);
	  prefsEditor.commit();

	 }
	 
	 public String getAppointmentDate()
	 {
	  return appSharedPrefs.getString("appointment_date", "");
	 }
	 
	 public void saveAppointmentDate(String text)
	 {
	  prefsEditor.putString("appointment_date", text);
	  prefsEditor.commit();

	 }
	 
	 public String getAppointmentTime()
	 {
	  return appSharedPrefs.getString("appointment_time", "");
	 }
	 
	 public void saveAppointmentTime(String text)
	 {
	  prefsEditor.putString("appointment_time", text);
	  prefsEditor.commit();

	 }
	 
	 public String getRegId()
	 {
	  return appSharedPrefs.getString("reg_id", "");
	 }
	 
	 public void saveRegId(String text)
	 {
	  prefsEditor.putString("reg_id", text);
	  prefsEditor.commit();

	 }
	 
	 public String getSearchedPatient()
	 {
	  return appSharedPrefs.getString("search_patient", "");
	 }
	 
	 public void saveSearchedPatient(String text)
	 {
	  prefsEditor.putString("search_patient", text);
	  prefsEditor.commit();

	 }
	 
	 public String getSearchedDoctor()
	 {
	  return appSharedPrefs.getString("search_doctor", "");
	 }
	 
	 public void saveSearchedDoctor(String text)
	 {
	  prefsEditor.putString("search_doctor", text);
	  prefsEditor.commit();

	 }

	public boolean getStoragePermission()
	{
		return appSharedPrefs.getBoolean("storage_permission", false);
	}

	public void setStoragePermission(boolean isStoragePermission)
	{
		prefsEditor.putBoolean("storage_permission", isStoragePermission);
		prefsEditor.commit();

	}
	 
	 public String getRecentDocotsChat()
	 {
	  return appSharedPrefs.getString("recent_doc_chat", "");
	 }
	 
	 public void saveRecentDocotsChat(String text)
	 {
	  prefsEditor.putString("recent_doc_chat", text);
	  prefsEditor.commit();

	 }

    public String getSearchedOffices()
    {
        return appSharedPrefs.getString("searched_offices", "");
    }

    public void saveSearchedOffices(String text)
    {
        prefsEditor.putString("searched_offices", text);
        prefsEditor.commit();

    }
	 
	 public String getRecentPatientChat()
	 {
	  return appSharedPrefs.getString("recent_pat_chat", "");
	 }
	 
	 public void saveRecentPatientChat(String text)
	 {
	  prefsEditor.putString("recent_pat_chat", text);
	  prefsEditor.commit();

	 }
   
    public String getRecentOfficeChat()
    {
        return appSharedPrefs.getString("recent_office_chat", "");
    }

    public void saveRecentOfficeChat(String text)
    {
        prefsEditor.putString("recent_office_chat", text);
        prefsEditor.commit();

    }

    public String getRecentPatientChatByOffice()
    {
        return appSharedPrefs.getString("recent_pat_chat_by_office", "");
    }

    public void saveRecentPatientChatByOffice(String text)
    {
        prefsEditor.putString("recent_pat_chat_by_office", text);
        prefsEditor.commit();

    }

	public String getListSpecialities()
	{
		return appSharePrefPer.getString("specialities", "");
	}

	public void saveListSpecialities(String text)
	{
		prefEditorPerm.putString("specialities", text);
		prefEditorPerm.commit();

	}


	public String getCountryList()
	{
		return appSharePrefPer.getString("countries", "");
	}

	public void saveCountriesList(String text)
	{
		prefEditorPerm.putString("countries", text);
		prefEditorPerm.commit();

	}







}
