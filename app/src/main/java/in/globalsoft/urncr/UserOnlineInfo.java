package in.globalsoft.urncr;

import in.globalsoft.beans.BeansLogin;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;

public class UserOnlineInfo extends BroadcastReceiver
{
	private String id;
	private String type;
	private String url;
	
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Gson gson = new Gson();
		AppPreferences pref = new AppPreferences(context);
		if(pref.getLogintype() == 0)
		{
			String loginInfo = pref.getUserLoginInfo();
			if(loginInfo !=null && !loginInfo.equals(""))
			{
				//patient login
				BeansLogin beanLogin = gson.fromJson(loginInfo, BeansLogin.class);
				id = beanLogin.getPatient_id();
				type="patient";
				url=Cons.url_patient_online_status+id;
				
				
			}
		
			
			
		}
		else if(pref.getLogintype()==1)
		{
			//docotr login
			id = pref.getDoctorId();
			type = "doctor";
			url=Cons.url_doctor_online_status+id;
		}

        else if(pref.getLogintype()==2)
        {
            //docotr login
            id = pref.getOfficeId();
            type = "doctor_office";
            url=Cons.url_office_online_status+id;
        }
		new OnlineStatusTask(context, url).execute();
		
	}
	
	
	//Task for sending online status
	public class OnlineStatusTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;
		String url;

		public OnlineStatusTask(Context con,String url)
		{
			this.con = con;
			this.url = url;
		}

		
		
		@Override
		protected Void doInBackground(Void... params)
		{
			Cons.http_connection(url);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			
			super.onPostExecute(result);

		}

	}

}
