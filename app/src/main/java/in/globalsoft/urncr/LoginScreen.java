package in.globalsoft.urncr;

import in.globalsoft.beans.BeansAddDoctorResponse;
import in.globalsoft.beans.BeansLogin;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.CommonUtilities;
import in.globalsoft.util.Cons;
import in.globalsoft.util.ParseInfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

public class LoginScreen extends Activity 
{
	EditText et_username,et_password;
	String responseString;
	BeansLogin loginBeans;
	AppPreferences appPref;
	RelativeLayout rl_login_type;
	TextView tv_login_type;
	AlertDialog dialog_loginType;
	int str_loginType;
	BeansAddDoctorResponse doctorLoginBeans;
	GoogleCloudMessaging gcm;
	String regid;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Button btn_login;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);
		gcm = GoogleCloudMessaging.getInstance(this);
		new RegisterBackground().execute();	
		//GCMRegistrar.unregister(this);

		btn_login = (Button) findViewById(R.id.login_btn);
		et_username = (EditText) findViewById(R.id.user_name);
		et_password = (EditText) findViewById(R.id.password);
		rl_login_type = (RelativeLayout)findViewById(R.id.login_type_layout);
		tv_login_type = (TextView)findViewById(R.id.login_type_txt);

		appPref = new AppPreferences(this);

		btn_login.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if(str_loginType == 0)
				{
					Toast.makeText(LoginScreen.this, "Select login type", Toast.LENGTH_LONG).show();
				}

				else if(Cons.isNetworkAvailable(LoginScreen.this))
				{
					new LoginTask(LoginScreen.this).execute();
				}
				else
					Cons.showDialog(LoginScreen.this, "Carrxon", "Internet connection is not available.", "OK");
			}
		});

		rl_login_type.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				dialogLoginType();
				dialog_loginType.show();

			}
		});
	}


	public class LoginTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;
		String userName;
		String password;

		public LoginTask(Context con)
		{
			this.con = con;
		}

		@Override
		protected void onPreExecute() 
		{
			pd = ProgressDialog.show(con, null, "Loading...");
			 userName = et_username.getText().toString();
			 password = et_password.getText().toString();
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... params)
		{

			String url = null;
			try {
				url = Cons.url_login+"username="+URLEncoder.encode(userName,"utf-8")+"&password="+URLEncoder.encode(password,"utf-8")+"&type="+str_loginType+"&device_id="+regid;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			responseString = Cons.http_connection(url);
			if(responseString == null)
			{

			}
			else
			{
				if(str_loginType == 1)
				{
					ParseInfo parseInfo = new ParseInfo();
					loginBeans = parseInfo.parseLogin(responseString,LoginScreen.this);
				}

				else if(str_loginType==2)
				{
					Gson gson = new Gson();
					doctorLoginBeans = gson.fromJson(responseString, BeansAddDoctorResponse.class);
				}
			}

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
			myMessage.obj = "login_task";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("login_task"))
			{
				if (!isFinishing()) 
				{
					if(str_loginType == 1)
					{

						if((loginBeans == null))

						{

							Cons.isNetAvail = 0;
							Toast.makeText(LoginScreen.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
						}

						else if(loginBeans.getCode()==200)
						{
                            appPref.setLoginType(0);
							appPref.saveLoginState(1);
							Intent travelexpense=new Intent(LoginScreen.this,HomeScreenWithLogin.class);
							startActivity(travelexpense);
							finish();


						}
						else 
						{
							et_username.setText("");
							et_password.setText("");
							Toast.makeText(LoginScreen.this, loginBeans.getMessage(), Toast.LENGTH_LONG).show();
						}
					}
					
					else if(str_loginType==3)
					{
						try {
							JSONObject obj=new JSONObject(responseString);
							String code=obj.getString("code");
							
							if(code.equals("200"))
							{
								String doctorId=obj.getString("doctor_id");
								String officeId=obj.getString("office_id");
								appPref.saveDoctorId(doctorId);
								appPref.saveOfficeId(officeId);
                                appPref.setLoginType(2);
                                appPref.saveLoginState(1);
								Intent travelexpense=new Intent(LoginScreen.this,DoctorOfficeHomeActivity.class);
								startActivity(travelexpense);
								finish();
								
							}
							
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
					else
					{
						if((doctorLoginBeans == null))

						{

							Cons.isNetAvail = 0;
							Toast.makeText(LoginScreen.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
						}

						else if(doctorLoginBeans.getCode()==200)
						{

							appPref.saveLoginState(1);
							appPref.setLoginType(1);
							appPref.saveDoctorId(doctorLoginBeans.getDoctor_id());
							Intent travelexpense=new Intent(LoginScreen.this,DisplayDoctorInfo.class);
							startActivity(travelexpense);
							finish();


						}
						else 
						{
							et_username.setText("");
							et_password.setText("");
							Toast.makeText(LoginScreen.this, doctorLoginBeans.getMessage(), Toast.LENGTH_LONG).show();
						}
					}



				}
			}


		}
	};

	public void dialogLoginType() 
	{
		final ArrayList<String> list_login = new ArrayList<String>();
		list_login.add("Patient");
		list_login.add("Doctor");
		list_login.add("Doctor Office");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, list_login);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Login As:");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (item == 0) {


					dialog.cancel();
				} 
				else if (item == 1) {


					dialog.cancel();

				}
				else if (item == 2) {

					appPref.setDoctorOffice(true);
					dialog.cancel();

				}

				tv_login_type.setText(list_login.get(item));
				str_loginType = item+1;


			}
		});

		dialog_loginType = builder.create();
	}

	private void checkNotNull(Object reference, String name) {
		if (reference == null) {
			throw new NullPointerException(
					"error");
		}
	}

	class RegisterBackground extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String msg = "";
			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(LoginScreen.this);
				}
				regid = gcm.register(CommonUtilities.SENDER_ID);
				msg = "Dvice registered, registration ID=" + regid;
				Log.d("111", msg);
				//  ServerUtilities.register(getApplicationContext(), regid);

			} catch (IOException ex) {
				msg = "Error :" + ex.getMessage();
			}
			return msg;
		}

		@Override
		protected void onPostExecute(String msg) {


		}
	}




}
