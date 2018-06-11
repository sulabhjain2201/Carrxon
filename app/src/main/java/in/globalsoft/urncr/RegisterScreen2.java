package in.globalsoft.urncr;

import in.globalsoft.beans.BeansLogin;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import in.globalsoft.util.ParseInfo;
import android.app.Activity;
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

import java.net.URLEncoder;

public class RegisterScreen2 extends Activity 
{
	EditText et_username,et_password,et_verifyPassword;
	String str_username,str_password,str_verifyPassword;
	Button btn_register;
	String responseString;
	BeansLogin registerBeans;
	AppPreferences appPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_screen2);
		appPref = new AppPreferences(this);
		defineLayouts();

		btn_register.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				checkValidations();

			}
		});
	}
	public void defineLayouts()
	{
		et_username = (EditText) findViewById(R.id.user_name);
		et_password = (EditText) findViewById(R.id.password);
		et_verifyPassword = (EditText) findViewById(R.id.verify_password);
		btn_register = (Button) findViewById(R.id.register_btn);

	}
	public void checkValidations()
	{
		str_username = et_username.getText().toString();
		str_password = et_password.getText().toString();
		str_verifyPassword = et_verifyPassword.getText().toString();
		if(str_username.equals(""))
		{
			Toast.makeText(RegisterScreen2.this, "User name can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_username.equals(""))
		{
			Toast.makeText(RegisterScreen2.this, "Password can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_verifyPassword.equals(""))
		{
			Toast.makeText(RegisterScreen2.this, "Verify password can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(!str_password.equals(str_verifyPassword))
		{
			Toast.makeText(RegisterScreen2.this, "Both Passwords must be same.", Toast.LENGTH_LONG).show();
		}
		else
		{
			if(Cons.isNetworkAvailable(RegisterScreen2.this))
			{
				new RegisterTask(RegisterScreen2.this).execute();
			}
			else
				Cons.showDialog(RegisterScreen2.this, "Carrxon", "Internet connection is not available.", "OK");
			
		}
	}
		public class RegisterTask extends AsyncTask<Void, Void, Void>
		{
			ProgressDialog pd;
			Context con;

			public RegisterTask(Context con)
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
				try {
					String url = Cons.url_register + "first_name=" + URLEncoder.encode(RegisterScreen1.str_firstname, "utf-8")
							+ "&last_name=" + URLEncoder.encode(RegisterScreen1.str_lastname, "utf-8")
							+ "&address=" +  URLEncoder.encode(RegisterScreen1.str_address, "utf-8")
							+ "&state=" +  URLEncoder.encode(RegisterScreen1.str_state, "utf-8")
							+ "&city=" +  URLEncoder.encode(RegisterScreen1.str_cityname, "utf-8")
							+ "&zip=" +  URLEncoder.encode(RegisterScreen1.str_zip, "utf-8")
							+ "&home_phone=" +  URLEncoder.encode(RegisterScreen1.str_homephone, "utf-8")
							+ "&work_phone=" +  URLEncoder.encode(RegisterScreen1.str_workcellphone, "utf-8")
							+ "&email=" + RegisterScreen1.str_email
							+ "&username=" +  URLEncoder.encode(str_username, "utf-8")
							+ "&password=" +  URLEncoder.encode(str_password, "utf-8");
					responseString = Cons.http_connection(url);
					ParseInfo parseInfo = new ParseInfo();
					registerBeans = parseInfo.parseRegister(responseString, RegisterScreen2.this, 1);
				}
				catch (Exception e){
					e.printStackTrace();
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
				myMessage.obj = "register_task";
				myHandler.sendMessage(myMessage);
				super.onPostExecute(result);

			}

		}
		private Handler myHandler = new Handler() 
		{

			public void handleMessage(Message msg)
			{


				if (msg.obj.toString().equalsIgnoreCase("register_task"))
				{
					if (!isFinishing()) 
					{

						if((registerBeans == null)||Cons.isNetAvail==1)

						{
							
							Cons.isNetAvail = 0;
							Toast.makeText(RegisterScreen2.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
						}
					
						else if(registerBeans.getCode()==200)
						{
							appPref.saveLoginState(1);
							Intent i = new Intent(RegisterScreen2.this,HomeScreenWithLogin.class);
							startActivity(i);
							RegisterScreen1.reg1_obj.finish();
							finish();
							
						}
						else 
						{
							
							Toast.makeText(RegisterScreen2.this, registerBeans.getMessage(), Toast.LENGTH_LONG).show();
						}



					}
				}


			}
		};

	


}
