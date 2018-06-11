	package in.globalsoft.tasks;

import in.globalsoft.urncr.R;
import in.globalsoft.urncr.RecentChatActivity;
import in.globalsoft.urncr.RecentOfficeChat;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class SearchAllDoctorsByPatientTask extends AsyncTask<Void, Void, Void>{

	ProgressDialog pd;
	Context con;
	String response;
	AppPreferences pref;
    boolean isOffice;
    private String speciality_id="";

	public SearchAllDoctorsByPatientTask(Context con, boolean isOffice)
	{
		this.con=con;
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
        String url = "";

		 url=Cons.URL_SEARCHDOCTORBY_PATIENT+ con.getString(R.string.speciality_id)+"&patient_id="+pref.getUserId();

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
		pref.saveSearchedDoctor(response);
            if(RecentOfficeChat.getInstance() == null)
		((RecentChatActivity) con).DoctorResponse(response);
            else
                ((RecentOfficeChat) con).DoctorResponse(response);
		}
		



	}

}
