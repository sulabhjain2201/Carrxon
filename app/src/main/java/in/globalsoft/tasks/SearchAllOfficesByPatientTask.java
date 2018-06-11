package in.globalsoft.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import in.globalsoft.urncr.R;
import in.globalsoft.urncr.RecentChatActivity;
import in.globalsoft.urncr.RecentOfficeChat;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;

/**
 * Created by Jain's on 4/4/2015.
 */
public class SearchAllOfficesByPatientTask extends AsyncTask<Void, Void, Void> {

    ProgressDialog pd;
    Context con;
    String response;
    AppPreferences pref;
    boolean isOffice;
    private String speciality_id="";

    public SearchAllOfficesByPatientTask(Context con, boolean isOffice)
    {
        this.con=con;
        pref=new AppPreferences(con);
        speciality_id = con.getString(R.string.speciality_id);
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

        url="http://carrxon.com/CarrxonWebServices/ws/searchDoctorOffice.php?specaility_id="+speciality_id+"&patient_id="+pref.getUserId();

        response= Cons.http_connection(url);


        return null;
    }
    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if(response!=null)
        {
            AppPreferences pref = new AppPreferences(con);
            pref.saveSearchedOffices(response);
           try {
               ((RecentChatActivity) con).DoctorResponse(response);
           }
           catch (Exception e)
           {
               ((RecentOfficeChat) con).DoctorResponse(response);
           }


        }




    }

}
