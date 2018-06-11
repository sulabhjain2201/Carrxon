package in.globalsoft.tasks;

import in.globalsoft.urncr.RecentChatActivity;

import in.globalsoft.urncr.RecentOfficeChat;
import in.globalsoft.urncr.RecentOfficePatientsForAppointment;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class SearchAllPatients extends  AsyncTask<Void, Void, Void> {

	ProgressDialog pd;
	Context con;
	String response;
	AppPreferences pref;
	boolean isOffice=false;//true for office-patient task and book appointment by office and false for doctor-patient chat

	public SearchAllPatients(Context con, boolean isOffice)
	{
		this.con=con;
		this.isOffice=isOffice;
		pref=new AppPreferences(con);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

	}
	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub

		String url="http://carrxon.com/CarrxonWebServices/ws/searchPatient.php?doctor_id="+"110";

		response=Cons.http_connection(url);


		return null;
	}
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(response!=null)
		{
			AppPreferences pref = new AppPreferences(con);
			pref.saveSearchedPatient(response);
			if(isOffice) {

                //if context of offcie recent chat is not null then call patient response of that
                //class otherwise call recent patients for book appintments
                if(RecentOfficeChat.getInstance() != null)
                ((RecentOfficeChat) con).PatientResponse(response);
                else
                      ((RecentOfficePatientsForAppointment) con).PatientResponse(response);
            }
			else
				((RecentChatActivity) con).PatientResponse(response);
		}


	}

}
