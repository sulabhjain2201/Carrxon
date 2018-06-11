package in.globalsoft.tasks;

import in.globalsoft.urncr.ChatActivity;
import in.globalsoft.urncr.OfficeChatActivity;
import in.globalsoft.util.Cons;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class SinglePatientDoctorTask extends AsyncTask<Void, Void, Void>
{
	Context con;
	String url;
	String res;	
	ProgressDialog pd;
	boolean isOfficeChat=false;

	public SinglePatientDoctorTask(Context con, String url, boolean isOfficeChat)
	{
		// TODO Auto-generated constructor stub
		this.con=con;
		this.url=url;
		this.isOfficeChat=isOfficeChat;
		pd=new ProgressDialog(con);
		pd.setCancelable(false);
		pd.setMessage("Loading...");
		pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		pd.show();
	}


	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub


		res=Cons.http_connection(url);

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(pd.isShowing())
			pd.dismiss();
		if(isOfficeChat)
			((OfficeChatActivity) con).ChatResult(res);
		else
		((ChatActivity) con).ChatResult(res);
			
		
	}

}
