package in.globalsoft.tasks;

import in.globalsoft.urncr.RecentOfficeChat;
import in.globalsoft.urncr.RecentOfficePatientsForAppointment;
import in.globalsoft.interfaces.RecentChatResult;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class RecentPatientsByOfficeTask extends AsyncTask<Void, Void, Void> {

    Context con;
    String response="";
    RecentChatResult result;
    ProgressDialog pd;
    AppPreferences pref;
    String url;
    public RecentPatientsByOfficeTask(Context con,String url)
    {
        this.con=con;
        this.url = url;
        result=(RecentChatResult) con;
        pref=new AppPreferences(con);
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        if(pref.getRecentPatientChatByOffice().equals(""))
        {
            pd=new ProgressDialog(con);
            pd.setCancelable(false);
            pd.setMessage("Loading...");
            pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            pd.show();
        }


    }
    @Override
    protected Void doInBackground(Void... arg0) {
        // TODO Auto-generated method stub
     //   String url="http://carrxon.com/CarrxonWebServices/ws/office_recent_chat.php?user_id="+pref.getUserId()+"&status=doctor_office";
       // String url=" http://carrxon.com/CarrxonWebServices/ws/recent_chat.php?user_id="+pref.getUserId()+"&status=doctor";

        response=Cons.http_connection(url);

        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

        if(pd!=null && pd.isShowing())
            pd.dismiss();
        if(response!=null)
            pref.saveRecentPatientChatByOffice(response);
        if(RecentOfficeChat.getInstance() != null )
            ((RecentOfficeChat) con).RecentChatResponse(response);
        else
            ((RecentOfficePatientsForAppointment) con).RecentChatResponse(response);

    }




}
