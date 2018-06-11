package in.globalsoft.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;

import com.google.gson.Gson;

import in.globalsoft.beans.BeansListSpecialities;
import in.globalsoft.preferences.AppPreferences;

public class BackgroungTask extends AsyncTask<Void, Void, Void>
{
	ProgressDialog pd;
	Context con;

	public BackgroungTask(Context con)
	{
		this.con = con;
	}

//	@Override
//	protected void onPreExecute() 
//	{
//		pd = ProgressDialog.show(con, null, "Loading...");
//		super.onPreExecute();
//	}
	@Override
	protected Void doInBackground(Void... params)
	{


		try
		{
			String url = Cons.URL_SPECIALITIES;
			System.out.println(url);
			String responseString = Cons.http_connection(url);
			if(responseString != null) {
				try {
					BeansListSpecialities beansListSpecialities = new Gson().fromJson(responseString, BeansListSpecialities.class);
					if(beansListSpecialities.getCode().equals("200")){
						new AppPreferences(con).saveListSpecialities(responseString);
					}

				}
				catch (final Exception e){
					e.printStackTrace();
					String specialitiesData = Utility.readFromAsset("specialities.json",con);
					new AppPreferences(con).saveListSpecialities(specialitiesData);

				}
			}
			else {
				String specialitiesData = Utility.readFromAsset("specialities.json",con);
				new AppPreferences(con).saveListSpecialities(specialitiesData);
			}

			Thread.sleep(2000);

		}
		catch(Exception ae)
		{

		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) 
	{
//		if(pd.isShowing())
//		{
//			pd.dismiss();
//		}
		Handlers handle = new Handlers(con);
		Message myMessage = new Message(); 
		myMessage.obj = "waiting_task";
		handle.sendMessage(myMessage);
		super.onPostExecute(result);

	}

}

