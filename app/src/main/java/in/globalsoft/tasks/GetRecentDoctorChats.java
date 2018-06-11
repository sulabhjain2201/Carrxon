package in.globalsoft.tasks;

import in.globalsoft.urncr.RecentChatActivity;
import in.globalsoft.urncr.RecentOfficeChat;
import in.globalsoft.interfaces.RecentChatResult;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetRecentDoctorChats extends AsyncTask<Void, Void, Void> {

	Context con;
	String response="";
	RecentChatResult result;
	ProgressDialog pd;
	AppPreferences pref;
	public GetRecentDoctorChats(Context con)
	{
		this.con=con;
		result=(RecentChatResult) con;
		pref=new AppPreferences(con);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if(pref.getRecentDocotsChat().equals(""))
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
       // String url="http://carrxon.com/CarrxonWebServices/ws/office_recent_chat.php?user_id="+pref.getUserId()+"&status=patient";
        String url="http://carrxon.com/CarrxonWebServices/ws/recent_chat.php?user_id="+pref.getDoctorId()+"&status=doctor";

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
			pref.saveRecentDocotsChat(response);
        if(RecentOfficeChat.getInstance() != null )
            ((RecentOfficeChat) con).RecentChatResponse(response);
            else
		((RecentChatActivity) con).RecentChatResponse(response);
	}




}
