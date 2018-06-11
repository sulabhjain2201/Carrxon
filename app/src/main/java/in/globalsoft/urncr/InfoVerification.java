package in.globalsoft.urncr;

import in.globalsoft.beans.BeansLogin;
import in.globalsoft.beans.BeansPatientList;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import in.globalsoft.util.ParseInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class InfoVerification extends Activity
{
	AppPreferences appPref;
	Button btn_nextstep;
	
	AlertDialog dialog_state;
	
	EditText et_firstname,et_lastname,et_address,et_cityname,et_zip,et_homephone,et_workcellphone,et_email,etState;
	public static String str_firstname,str_lastname,str_address,str_cityname,str_state,str_zip,str_homephone,str_workcellphone,str_email,str_verifyEmail;
	BeansLogin loginBeans;
	public static String user_id="0";
	String responseString1,responseString2;
	BeansLogin registerBeans;
	String message_obj="";
	public static BeansPatientList patientListBeans;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_verification);
		appPref = new AppPreferences(this);
		defineLayouts();

		if(appPref.getLoginState() == 1)
		{
			setPrefilledValues();

		}

		btn_nextstep.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				getEdittextValues();
				checkForBlank();

			}
		});
		
	}

	public void defineLayouts()
	{
		btn_nextstep= (Button)findViewById(R.id.next_step_btn);
		et_firstname = (EditText)findViewById(R.id.first_name);
		et_lastname= (EditText) findViewById(R.id.last_name);
		et_address = (EditText) findViewById(R.id.address);
		et_cityname = (EditText) findViewById(R.id.city_name);
		et_zip = (EditText) findViewById(R.id.zip);
		et_homephone = (EditText) findViewById(R.id.home_phone);
		et_workcellphone= (EditText) findViewById(R.id.work_cellphone);
		et_email = (EditText) findViewById(R.id.preferred_mail);

		etState = (EditText) findViewById(R.id.etState);

	}

	
	public void getEdittextValues()
	{
		str_firstname = et_firstname.getText().toString();
		str_lastname = et_lastname.getText().toString();
		str_address = et_address.getText().toString();
		str_cityname = et_cityname.getText().toString();
		str_zip = et_zip.getText().toString();
		str_homephone = et_homephone.getText().toString();
		str_workcellphone = et_workcellphone.getText().toString();
		str_email = et_email.getText().toString();

		str_state =  etState.getText().toString();

	}
	public void checkForBlank()
	{
		if(str_firstname.equals(""))
		{
			Toast.makeText(InfoVerification.this, "First name can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_lastname.equals(""))
		{
			Toast.makeText(InfoVerification.this, "Last name can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_address.equals(""))
		{
			Toast.makeText(InfoVerification.this, "Address can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_cityname.equals(""))
		{
			Toast.makeText(InfoVerification.this, "City name can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_state.equals(""))
		{
			Toast.makeText(InfoVerification.this, "State can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_zip.equals(""))
		{
			Toast.makeText(InfoVerification.this, "Zip can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_homephone.equals(""))
		{
			Toast.makeText(InfoVerification.this, "Home Phone can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_workcellphone.equals(""))
		{
			Toast.makeText(InfoVerification.this, "Work/Cell can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_email.equals(""))
		{
			Toast.makeText(InfoVerification.this, "Email can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(!Cons.isValidEmail(str_email))
		{
			Toast.makeText(InfoVerification.this, "Please provide valid email id", Toast.LENGTH_LONG).show();
		}

		else
		{
			if(Cons.isNetworkAvailable(InfoVerification.this))
			{
				new RegisterUpdateTask(InfoVerification.this).execute();
			}
			else
				Cons.showDialog(InfoVerification.this, "Carrxon", "Internet connection is not available.", "OK");
		}
	}

	public void setPrefilledValues()
	{
		String userLoginInfo = appPref.getUserLoginInfo();
		Gson gson = new Gson();
		loginBeans = gson.fromJson(userLoginInfo, BeansLogin.class);

		et_firstname.setText(loginBeans.getFirst_name());
		et_lastname.setText(loginBeans.getLast_name());
		et_address.setText(loginBeans.getAddress());
		et_zip.setText(loginBeans.getZip());
		et_cityname.setText(loginBeans.getCity());
		et_homephone.setText(loginBeans.getHome_phone());
		et_workcellphone.setText(loginBeans.getWork_phone());
		et_email.setText(loginBeans.getEmail());
		etState.setText(loginBeans.getState());



	}

	public class RegisterUpdateTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;

		public RegisterUpdateTask(Context con)
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
			int status = 0;
			user_id = "0";
			Gson gson = new Gson();
			BeansLogin loginBeans = gson.fromJson(appPref.getUserLoginInfo(), BeansLogin.class);
			if(loginBeans !=null)
			user_id = loginBeans.getPatient_id();
			if(!user_id.equals("0"))
			{
				status =1;
			}

			String url = Cons.url_update_registration+"first_name="+InfoVerification.str_firstname
					+"&last_name="+InfoVerification.str_lastname
					+"&address="+InfoVerification.str_address
					+"&state="+InfoVerification.str_state
					+"&city="+InfoVerification.str_cityname
					+"&zip="+InfoVerification.str_zip
					+"&home_phone="+InfoVerification.str_homephone
					+"&work_phone="+InfoVerification.str_workcellphone
					+"&email="+InfoVerification.str_email
					+"&patient_id="+user_id;
			System.out.println(url);
			responseString1 = Cons.http_connection(url);
			ParseInfo parseInfo = new ParseInfo();
			registerBeans = parseInfo.parseRegister(responseString1,InfoVerification.this,status);

			if(registerBeans !=null && registerBeans.getCode()==200)
			{
				user_id = registerBeans.getPatient_id();
				message_obj = "get_patient_task";
				String getPatients_url = Cons.url_get_patients+"patient_id="+registerBeans.getPatient_id();
				responseString2 = Cons.http_connection(getPatients_url);

				patientListBeans = gson.fromJson(responseString2, BeansPatientList.class);
			}
			else
				message_obj = "register_update_task";
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
			myMessage.obj = message_obj;
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("register_update_task"))
			{
				if (!isFinishing()) 
				{

					if((registerBeans == null)||Cons.isNetAvail==1)

					{

						Cons.isNetAvail = 0;
						Toast.makeText(InfoVerification.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
					}

					else if(registerBeans.getCode()==200)
					{

						Intent i = new Intent(InfoVerification.this,SelectPatient.class);
						startActivity(i);


					}
					else 
					{

						Toast.makeText(InfoVerification.this, registerBeans.getMessage(), Toast.LENGTH_LONG).show();
					}



				}
			}
			else if (msg.obj.toString().equalsIgnoreCase("get_patient_task"))
			{
				if (!isFinishing()) 
				{

					if((patientListBeans == null)||Cons.isNetAvail==1)

					{

						Cons.isNetAvail = 0;
						Toast.makeText(InfoVerification.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
					}


					else if(patientListBeans.getCode()==200)
					{

						Intent i = new Intent(InfoVerification.this,SelectPatient.class);
						startActivity(i);
					}
					else if(patientListBeans.getCode()==401)
					{

						Intent i = new Intent(InfoVerification.this,PatientInfo1.class);
						startActivity(i);
					}



				}
			}



		}
	};
	
	


}
