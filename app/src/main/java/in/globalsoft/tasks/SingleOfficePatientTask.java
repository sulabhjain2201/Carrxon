package in.globalsoft.tasks;

import in.globalsoft.urncr.ChatActivity;
import in.globalsoft.urncr.OfficeChatActivity;

import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class SingleOfficePatientTask extends AsyncTask<Void, Void, Void>{

	Context con;
	String url;
	String res;	
	ProgressDialog pd;

    AppPreferences appPref;

	public SingleOfficePatientTask(Context con, String url) {
		// TODO Auto-generated constructor stub
		this.con=con;
		this.url=url;
        appPref = new AppPreferences(con);

	}


	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

           pd = new ProgressDialog(con);
           pd.setCancelable(false);
           pd.setMessage("Loading...");
           pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
           pd.show();

	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		res=Cons.http_connection(url);
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(pd != null && pd.isShowing())
			pd.dismiss();

        if(OfficeChatActivity.getInstance() != null)
		((OfficeChatActivity) con).ChatResult(res);
        else
            ((ChatActivity) con).ChatResult(res);
	}




}
