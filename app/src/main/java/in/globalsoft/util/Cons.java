package in.globalsoft.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import in.globalsoft.pojo.SavedDocPojo;

public class Cons 
{
	public static String DOC_UPLOAD_URL = "http://carrxon.com/CarrxonWebServices/ws/document_upload.php";
	public static int screenWidth,screenHeight;
	public static int isNetAvail = 0;
	public static String url_login = "http://carrxon.com/CarrxonWebServices/ws/patient_login.php?";
	public static String url_register = "http://carrxon.com/CarrxonWebServices/ws/patient_registration.php?";
	public static String url_addPatient = "http://www.carrxon.com/CarrxonWebServices/ws/add_patient_family_member.php?";
	public static String url_hospitalDetail = "http://www.carrxon.com/CarrxonWebServices/ws/get_doctor_info.php?";
	public static String url_update_registration = "http://carrxon.com/CarrxonWebServices/ws/update_patient_registration_info.php?";
	public static String url_get_patients = "http://carrxon.com/CarrxonWebServices/ws/get_all_patient_family_members.php?";
	public static String url_update_patient = "http://carrxon.com/CarrxonWebServices/ws/update_patient_family_member.php?";
	public static String url_add_insurance_info = "http://www.carrxon.com/CarrxonWebServices/ws/add_insurance_info.php?";
	public static String url_update_insurance_info = "http://www.carrxon.com/CarrxonWebServices/ws/update_insurance_info.php?";
	public static String url_get_insurance_info = "http://carrxon.com/CarrxonWebServices/ws/get_patient_insurance_info.php?";
	public static String url_add_contactInfo = "http://www.carrxon.com/CarrxonWebServices/ws/add_contact_info.php?";
	public static String url_add_checkin_info = "http://www.carrxon.com/CarrxonWebServices/ws/add_checkin_info.php?";
	public static String url_add_employer_info = "http://www.carrxon.com/CarrxonWebServices/ws/add_employer_info.php?";
	public static String url_get_physicianlist = "http://carrxon.com/CarrxonWebServices/ws/get_all_doctor_list.php";
	public static String url_add_doctor_info = "http://carrxon.com/CarrxonWebServices/ws/add_doctor.php?";
	public static String url_add_doctor_image = "http://carrxon.com/CarrxonWebServices/ws/upload_doctor_image.php?";
	public static String url_add_credit_image = "http://carrxon.com/CrxyrWebServices/upload_credit_card_image.php?";
	public static String url_add_hospital_image = "http://carrxon.com/CarrxonWebServices/ws/upload_hospital_image.php?";
	public static String url_hospital_register = "http://carrxon.com/CarrxonWebServices/ws/hospital_registration.php?";
	public static String url_get_doctors_by_hospital = "http://carrxon.com/CarrxonWebServices/ws/get_doctors_by_hospital.php?";
	public static String url_get_physician_detail = "http://carrxon.com/CarrxonWebServices/ws/get_doctor_info_by_id.php?";
	public static String url_addScheduleInfo = "http://carrxon.com/CarrxonWebServices/ws/update_schedule_info.php?";
	public static String url_update_statusInfo = "http://carrxon.com/CarrxonWebServices/ws/update_status_info.php?";
	public static String url_doctorAddress = "http://carrxon.com/CarrxonWebServices/ws/search_clinic.php?";
	public static String url_booked_appointments = "http://carrxon.com/CarrxonWebServices/ws/getBookedAppointmentsByDoctorAndDate.php?";
	public static String url_available_appointments = "http://carrxon.com/CarrxonWebServices/ws/getAvailableAppointmentsByDoctorAndDate.php?";
	public static String url_doctorDetailWithAvailableAppointment = "http://carrxon.com/CarrxonWebServices/ws/getDoctorInfoWithAvailableAppointments.php?";
	public static String url_add_docor_education = "http://carrxon.com/CarrxonWebServices/ws/add_doctor_education.php?";
	public static String url_search_clinic_by_query = "http://www.carrxon.com/CarrxonWebServices/ws/search_clinic_by_query.php?query=";
	public static String url_search_doctor = "http://www.carrxon.com/CarrxonWebServices/ws/search_clinic_by_query.php?query=";
	public static String url_patient_online_status="http://www.carrxon.com/CarrxonWebServices/ws/patientStatus.php?patient_id=";
	public static String url_doctor_online_status="http://www.carrxon.com/CarrxonWebServices/ws/doctorStatus.php?doctor_id=";
    public static String url_office_online_status="http://www.carrxon.com/CarrxonWebServices/ws/officeStatus.php?office_id=";
	public static String url_doctor_office_registration="http://www.carrxon.com/CarrxonWebServices/ws/addDoctorOfficeInfo.php?doctor_id=";
    public static String url_getOfficeBydoctorId="http://www.carrxon.com/CarrxonWebServices/ws/getDoctocByOfficeId.php?doctor_id=";
	public static String url_special_doctors_by_speciality="http://carrxon.com/CarrxonWebServices/ws/list_special_doctors.php?speciality_id=";
    public static String URL_SEARCHDOCTORBY_NAME_LOCATION="http://carrxon.com/CarrxonWebServices/ws/search_by_fields.php?speciality_id=";
	public static String URL_SEARCH_DOCTORS="http://carrxon.com/CarrxonWebServices/ws/list_searched_doctors.php?";
	public static String URL_SEARCHDOCTORBY_PATIENT="http://carrxon.com/CarrxonWebServices/ws/searchDoctor.php?speciality_id=";
	public static  String url_add_admin_doctor_info = "http://carrxon.com/CarrxonWebServices/ws/add_admin_doctor.php?";
	public static final String URL_COPAY_SAVING_CARDS = "http://carrxon.com/CarrxonWebServices/ws/saving_cards.php?";
	public static  final int ADD_DOCTOR = 0;
	public static final String URL_SPECIALITIES = "http://carrxon.com/CarrxonWebServices/ws/specialities.php";
    public static final String URL_COUNTRIES = "http://carrxon.com/CarrxonWebServices/ws/get_all_countries.php";
	public static final String URL_STATES = "http://carrxon.com/CarrxonWebServices/ws/get_all_states.php";
	public static final String URL_CITIES = "http://carrxon.com/CarrxonWebServices/ws/get_all_cities.php";
	public static  final int UPDATE_DOCTOR = 1;
	public static String saved_doc_url="http://carrxon.com/CarrxonWebServices/ws/patient_docs.php?user_id=";
	public static String download_url="http://carrxon.com/CarrxonWebServices/ws/";

	public static void getScreen_Height(Activity c)
	{
		DisplayMetrics dm = new DisplayMetrics();
		c.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}

	public static void showDialog(final Context context, String title, String message, final String click_button)
	{
		System.out.println(context.getClass());
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
		alt_bld.setMessage(message).setCancelable(false)
		.setPositiveButton(click_button, new DialogInterface.OnClickListener() 
		{

			public void onClick(DialogInterface dialog, int id) 
			{
				dialog.cancel();
			}
		});

		AlertDialog alert = alt_bld.create();
		alert.setTitle("\t" + title);
		//		alert.setIcon(AlertDialog.BUTTON_NEGATIVE);
		alert.show();
	}

	public static boolean isNetworkAvailable(Context context) 
	{
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		// ARE WE CONNECTED TO THE NET

		if (connMgr.getActiveNetworkInfo() != null && connMgr.getActiveNetworkInfo().isAvailable() && connMgr.getActiveNetworkInfo().isConnected()) 
			return true;
		else
		{
			Log.v("PIPA", "Internet Connection Not Present");
			return false;
		}

	}

	public static String http_connection(String url_string) 
	{

		String str = null;
		try 
		{
			isNetAvail = 0;
			//			
			url_string = url_string.replace(" ", "%20");
			URL url = new URL(url_string);

			URLConnection connection = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			StringBuffer buffer = new StringBuffer("");

			String line = "";
			while ((line = br.readLine()) != null) 
			{
				buffer.append(line);
			}
			str = buffer.toString();
			// xpp.setInput(br);
		} 
		catch (Exception ae)
		{
			ae.printStackTrace();

		}
		return str;

	}

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}// Convert a date String from one format to another
	public static String changeDateFormat(String time, String fromFormat,
			String toFormat) {

		SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SimpleDateFormat sdf1 = new SimpleDateFormat(toFormat);
		String s = sdf1.format(date.getTime());
		return s;
	}

	// convert miliseconds(in long) to a string date with required format
	public static String convertMiliSecondsToString(long milliSeconds,
			String dateFormat) {
		// Create a DateFormatter object for displaying date in specified
		// format.
		DateFormat formatter = new SimpleDateFormat(dateFormat);

		// Create a calendar object that will convert the date and time value in
		// milliseconds to date.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}

	// convert util date into String with specific format
	public static String convertDateToString(Date date, String format) {

		DateFormat df = new SimpleDateFormat(format);
		String str = df.format(date);
		return str;

	}

	// convert String to Date
	public static Date convertStringToDate(String dateString, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date convertedDate = new Date();
		try {
			convertedDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertedDate;
	}

	//convert String date to calendar
	public static Calendar convertStringToCal(String dateString,String format)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			cal.setTime(sdf.parse(dateString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// all done
		return cal;
	}

	public static boolean isNullOrEmpty( final Collection< ? > c ) {
		return c == null || c.isEmpty();
	}

	public static boolean isNullOrEmpty( final Map< ?, ? > m ) {
		return m == null || m.isEmpty();
	}


	public static void downLoad(Context ctx, SavedDocPojo savedDocPojo) {
		try {


			DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Cons.download_url+savedDocPojo.getDoc_url()));
			request.setDescription("file downloading..");
			request.setTitle("NCR Carrxon");
// in order for this if to run, you must use the android 3.2 to compile your app
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				request.allowScanningByMediaScanner();
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			}
			request.setDestinationInExternalPublicDir("/URNCR_FILES",savedDocPojo.getAttach_name());

// get download service and enqueue file
			DownloadManager manager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
			manager.enqueue(request);
		} catch (Exception e) {
			Log.e("Document Error", e.getMessage());
		}
	}


    public static  String readFileFromRawDirectory(Context context , int resourceId){
        InputStream iStream = context.getResources().openRawResource(resourceId);
        ByteArrayOutputStream byteStream = null;
        try {
            byte[] buffer = new byte[iStream.available()];
            iStream.read(buffer);
            byteStream = new ByteArrayOutputStream();
            byteStream.write(buffer);
            byteStream.close();
            iStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteStream.toString();
    }
}
