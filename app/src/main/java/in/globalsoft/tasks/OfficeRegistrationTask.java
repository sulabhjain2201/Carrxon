package in.globalsoft.tasks;

import in.globalsoft.urncr.DoctorOfficeInfo;
import in.globalsoft.util.Cons;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class OfficeRegistrationTask extends AsyncTask<Void, Void, Void> {

	Context con;
	String url;
	ProgressDialog pd;
	String response;
	
	public OfficeRegistrationTask(Context con,String url)
	{
		this.con=con;
		this.url=url;
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
		
		response=Cons.http_connection(url);
		return null;
	}
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(response!=null)
		{
		((DoctorOfficeInfo) con).ChatResult(response);
		}
		
	}

}
