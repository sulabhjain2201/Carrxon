package in.globalsoft.urncr;

import in.globalsoft.beans.BeansPhysianInfoResponse;
import in.globalsoft.urncr.R;
import in.globalsoft.util.Cons;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class PhysicainDetail extends Activity 
{
	String responseString;
	BeansPhysianInfoResponse physicianInfoResponse;
	TextView tv_doctor_name,tv_doctor_address,tv_doctor_email,tv_doctor_phone;
	TextView tv_hospital_name,tv_hospital_address,tv_hospital_phone,tv_hospital_image,tv_email;
String doctor_id;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physicain_detail);
		doctor_id = getIntent().getExtras().getString("position");
		defineLayouts();
		new PhysianInfoTask(this).execute();

	}

	public class PhysianInfoTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;

		public PhysianInfoTask(Context con)
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
			String url = "";

			Gson gson = new Gson();
			url = Cons.url_get_physician_detail+"doctor_id="+doctor_id;
			System.out.println("url::"+url);
			responseString = Cons.http_connection(url);	
			System.out.println(responseString);
			physicianInfoResponse = gson.fromJson(responseString, BeansPhysianInfoResponse.class);

			//				insuranceInfoBeans=gson.fromJson(responseString, BeansInsuranceInfo.class);

			//			}
			//			else
			//			{
			//				appPreferences.saveInsuranceState(0);
			//			}




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
			myMessage.obj = "get_physician_info";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("get_physician_info"))
			{
				if (!isFinishing()) 
				{

					if((physicianInfoResponse == null)||Cons.isNetAvail==1)

					{

						Cons.isNetAvail = 0;
						Toast.makeText(PhysicainDetail.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
					}

					else if(physicianInfoResponse.getCode()==200)
					{

						tv_doctor_name.setText(physicianInfoResponse.getDoctor_details().getDoctor_name());
						tv_doctor_address.setText(physicianInfoResponse.getDoctor_details().getDoctor_address());
						tv_doctor_email.setText(physicianInfoResponse.getDoctor_details().getDoctor_email());
						tv_doctor_phone.setText(physicianInfoResponse.getDoctor_details().getDoctor_phone());
						tv_hospital_address.setText(physicianInfoResponse.getDoctor_details().getAddress());
						tv_hospital_name.setText(physicianInfoResponse.getDoctor_details().getHospital_name());
						tv_hospital_phone.setText(physicianInfoResponse.getDoctor_details().getHospital_phone());
						tv_email.setText(physicianInfoResponse.getDoctor_details().getEmail());


					}
					else 
					{
//						appPreferences.saveInsuranceState(0);
//						Intent i=new Intent(PatientInfo2.this,InsuredPersonInfo.class);
//						startActivity(i);
						Toast.makeText(PhysicainDetail.this, physicianInfoResponse.getMessage(), Toast.LENGTH_LONG).show();
					}
				}



			}
		}



	};
	public void defineLayouts()
	{
		tv_doctor_name = (TextView) findViewById(R.id.doctor_name);
		tv_doctor_phone = (TextView) findViewById(R.id.doctor_phone);
		tv_doctor_email = (TextView) findViewById(R.id.doctor_email);
		tv_doctor_address = (TextView) findViewById(R.id.doctor_address);
		
		tv_hospital_name = (TextView) findViewById(R.id.hospital_name);
		tv_hospital_phone = (TextView) findViewById(R.id.hospital_phone );
		tv_hospital_address = (TextView) findViewById(R.id.hospital_address);
		tv_email = (TextView) findViewById(R.id.doctor_email);
	}

}


