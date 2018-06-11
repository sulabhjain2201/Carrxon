package in.globalsoft.urncr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import in.globalsoft.beans.BeansAddDoctorResponse;
import in.globalsoft.beans.BeansResponse;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;

public class HospitalRegistration2 extends Activity
{

	EditText et_username,et_password,et_verifyPassword;
	String str_username,str_password,str_verifyPassword;
	Button btn_register;
	String responseString;
	String path = "";
	int serverResponseCode;
	String serverResponseMessage;
	String ts;
	BeansAddDoctorResponse registerBeans;
	String hospitalId;
	AppPreferences appPref;
	String rs="";
	String msg_res="";
	BeansResponse responseBeans;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital_registration2);


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
			Toast.makeText(HospitalRegistration2.this, "User name can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_username.equals(""))
		{
			Toast.makeText(HospitalRegistration2.this, "Password can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_verifyPassword.equals(""))
		{
			Toast.makeText(HospitalRegistration2.this, "Verify password can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(!str_password.equals(str_verifyPassword))
		{
			Toast.makeText(HospitalRegistration2.this, "Both Passwords must be same.", Toast.LENGTH_LONG).show();
		}
		else
		{
			if(Cons.isNetworkAvailable(HospitalRegistration2.this))
			{
				new RegisterTask(HospitalRegistration2.this).execute();
			}
			else
				Cons.showDialog(HospitalRegistration2.this, "Carrxon", "Internet connection is not available.", "OK");

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
				Gson gson = new Gson();
				String schdule = gson.toJson(AddClinicShedule.scheduleListBeans);

				String url = Cons.url_add_admin_doctor_info + "doctor_name=" + AddDoctor.str_doctorName
						+ "&doctor_phone=" + Uri.encode(AddDoctor.str_doctorPhone)
						+ "&doctor_speciality=" + AddDoctor.str_speciality
						+ "&doctor_address=" + AddDoctor.str_doctorAddress
						+ "&doctor_email=" + AddDoctor.str_doctorEmail
						+ "&username=" + str_username
						+ "&password=" + URLEncoder.encode(str_password, "utf-8");

				System.out.println(url);
				responseString = Cons.http_connection(url);
				if (responseString != null)
					System.out.println(responseString);
				registerBeans = gson.fromJson(responseString, BeansAddDoctorResponse.class);

				msg_res = "hospital_info";
				if (registerBeans != null && registerBeans.getCode() == 200) {
					appPref.saveDoctorId(registerBeans.getDoctor_id());

					msg_res = "hospital_image";
					upload_image(registerBeans.getDoctor_id());


				}


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

					if(msg_res.equals("hospital_info"))
					{
						if(registerBeans == null)
						{

							Toast.makeText(HospitalRegistration2.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
						}
						else
						{
							Toast.makeText(HospitalRegistration2.this, registerBeans.getMessage(), Toast.LENGTH_LONG).show();
						}
					}
					else
					{

						if((serverResponseCode == 0))

						{
							Intent i = new Intent(HospitalRegistration2.this,DoctorOfficeInfo.class);
							i.putExtra("entry_type", Cons.ADD_DOCTOR);
							i.putExtra("doctorId",registerBeans.getDoctor_id() );

							startActivity(i);

							Toast.makeText(HospitalRegistration2.this, "Doctor added successfully.", Toast.LENGTH_LONG).show();
						}

						else if(responseBeans.getCode() == 200)
						{
							Intent i = new Intent(HospitalRegistration2.this,DoctorOfficeInfo  .class);
							i.putExtra("entry_type", Cons.ADD_DOCTOR);
							i.putExtra("doctorId", registerBeans.getDoctor_id());
							startActivity(i);
							Toast.makeText(HospitalRegistration2.this, responseBeans.getMessage(), Toast.LENGTH_LONG).show();

							Toast.makeText(HospitalRegistration2.this, "Doctor added successfully.", Toast.LENGTH_LONG).show();

						}
						else
						{

							Toast.makeText(HospitalRegistration2.this, responseBeans.getMessage(), Toast.LENGTH_LONG).show();
						}
					}



				}
			}


		}
	};

	public void upload_image(String doctor_id)
	{
		HttpURLConnection connection = null;
		DataOutputStream outputStream = null;
		DataInputStream inputStream = null;

		String pathToOurFile = AddDoctor.path;
		String urlServer = Cons.url_add_doctor_image+"doctor_id="+doctor_id;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary =  "*****";

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1*1024*1024;

		try
		{
			FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );

			URL url = new URL(urlServer);
			connection = (HttpURLConnection) url.openConnection();

			// Allow Inputs & Outputs
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			// Enable POST method
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

			outputStream = new DataOutputStream( connection.getOutputStream() );
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			Long tsLong = System.currentTimeMillis();
			ts = tsLong.toString();
			outputStream.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\""+ts+".jpg"+"\"" + lineEnd);
			outputStream.writeBytes(lineEnd);

			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// Read file
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0)
			{
				outputStream.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}

			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);



			// Responses from the server (code and message)
			serverResponseCode = connection.getResponseCode();
			serverResponseMessage = connection.getResponseMessage();
			System.out.println("response:"+rs);
			try
			{
				rs= http_connect(connection);
				Gson gson = new Gson();
				responseBeans=  gson.fromJson(rs, BeansResponse.class);
			}
			catch(Exception ae)
			{
				ae.printStackTrace();
			}

			fileInputStream.close();
			outputStream.flush();
			outputStream.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			Cons.isNetAvail = 1;
		}
	}
	public static String http_connect(HttpURLConnection con)
	{

		String str = null;
		try
		{
			//

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			StringBuffer buffer = new StringBuffer("");

			String line = "";
			while ((line = br.readLine()) != null)
			{
				buffer.append(line);
			}
			str = buffer.toString();
			System.out.println(str);
			// xpp.setInput(br);
		}
		catch (Exception ae)
		{
			//isNetAvail = 1;

		}
		return str;

	}



}
