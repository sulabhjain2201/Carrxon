package in.globalsoft.tasks;

import in.globalsoft.urncr.RecentChatActivity;
import in.globalsoft.interfaces.RecentChatResult;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetRecentChatsTask extends AsyncTask<Void, Void, Void> {

	Context con;
	String response="";
	RecentChatResult result;
	ProgressDialog pd;
	AppPreferences pref;
	public GetRecentChatsTask(Context con)
	{
		this.con=con;
		result=(RecentChatResult) con;
		pref=new AppPreferences(con);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if(pref.getRecentPatientChat().equals(""))
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
		String url="http://carrxon.com/CarrxonWebServices/ws/recent_chat.php?user_id="+pref.getUserId()+"&status=patient";
		response=Cons.http_connection(url);

		return null;
	}

	@Override
	protected void onPostExecute(Void result) 
	{
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		if(pd!=null&&pd.isShowing())
			pd.dismiss();
		if(response!=null)
		{
			pref.saveRecentPatientChat(response);
		((RecentChatActivity) con).RecentChatResponse(response);
		}
	}




}
