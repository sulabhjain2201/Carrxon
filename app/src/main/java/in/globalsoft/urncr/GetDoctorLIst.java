package in.globalsoft.urncr;

import in.globalsoft.beans.BeansDoctorsByHospitalList;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.AdapterDoctorList;
import in.globalsoft.util.Cons;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

public class GetDoctorLIst extends Activity 
{
	String responseString;
	BeansDoctorsByHospitalList doctorByHospitalList;
	ListView listDoctorsByHospital;
	AppPreferences appPref;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		appPref = new AppPreferences(this);
		setContentView(R.layout.activity_get_doctor_list);
		listDoctorsByHospital = (ListView) findViewById(R.id.list_doctor);
		if(Cons.isNetworkAvailable(this))
		{
			new GetDoctorListTask(this).execute();
		}
	}
	
	public class GetDoctorListTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;

		public GetDoctorListTask(Context con)
		{
			this.con = con;
		}

		@Override
		protected void onPreExecute() 
		{
			pd = ProgressDialog.show(con, null, "Loading...");
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... params)
		{
			
			String url = Cons.url_get_doctors_by_hospital+"hospital_id="+appPref.getDoctorId();
			System.out.println(url);
			responseString = Cons.http_connection(url);
			System.out.println(responseString);
			Gson gson = new Gson();
			
			doctorByHospitalList = gson.fromJson(responseString, BeansDoctorsByHospitalList.class);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			if(pd.isShowing())
			{
				pd.dismiss();
			}
			Message myMessage = new Message(); 
			myMessage.obj = "all_doctor_list";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("all_doctor_list"))
			{
				if (!isFinishing()) 
				{

					if((doctorByHospitalList == null)||Cons.isNetAvail==1)

					{

						Cons.isNetAvail = 0;
						Toast.makeText(GetDoctorLIst.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
					}

					else if(doctorByHospitalList.getCode()==200)
					{
						
						listDoctorsByHospital.setAdapter(new AdapterDoctorList(GetDoctorLIst.this,doctorByHospitalList));
					}
					else 
					{
						
						Toast.makeText(GetDoctorLIst.this, doctorByHospitalList.getMessage(), Toast.LENGTH_LONG).show();
					}



				}
			}


		}
	};


	
}
