package in.globalsoft.urncr;

import in.globalsoft.beans.BeansCheckinInfo;
import in.globalsoft.beans.BeansContactInfo;
import in.globalsoft.beans.BeansLogin;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
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

import com.google.gson.Gson;

public class ContactInfo extends Activity
{

	EditText et_contactName,et_contactPhoneNo,et_reachTime;
	Button btn_complete;
	String responseString,responseString1;
	BeansContactInfo contactInfoBeans;
	BeansCheckinInfo checkinfoBeans;
	AppPreferences appPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_info);
		appPref = new AppPreferences(this);
		et_contactName = (EditText) findViewById(R.id.full_name);
		et_contactPhoneNo = (EditText) findViewById(R.id.contact_phone_no);
		et_reachTime = (EditText) findViewById(R.id.time_in_mins);
		btn_complete = (Button) findViewById(R.id.complete_btn);

		btn_complete.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				if(Cons.isNetworkAvailable(ContactInfo.this))
				{
					new ContactInfoTask(ContactInfo.this).execute();
				}

				else
					Cons.showDialog(ContactInfo.this, "Carrxon", "Internet connection is not available.", "OK");

			}
		});


	}

	public class ContactInfoTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;

		public ContactInfoTask(Context con)
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
			String url_checkin="";
			String msg = "";

			url = Cons.url_add_contactInfo+"name="+et_contactName.getText().toString()
					+"&phone="+et_contactPhoneNo.getText().toString()
					+"&minutes_to_reached="+et_reachTime.getText().toString();
			System.out.println("url:"+url);

			responseString = Cons.http_connection(url);
			System.out.println(responseString);
			Gson gson = new Gson();
			contactInfoBeans = gson.fromJson(responseString, BeansContactInfo.class);
			msg = "contact_info";
			if(contactInfoBeans !=null && contactInfoBeans.getCode()==200)
			{

				BeansLogin loginBeans = gson.fromJson(appPref.getUserLoginInfo(), BeansLogin.class);

				url_checkin = Cons.url_add_checkin_info + "member_id="+PatientInfo1.member_id
						+"&relationship_with_insured="+PatientInfo2.str_relation
						+"&patient_before="+PatientInfo2.str_previousPatient
						+"&insurance_id="+InsuranceInfo.insurance_id + "&contact_id="+contactInfoBeans.getContact_id()
						+"&appointment_date="+appPref.getAppointmentDate() + "&appointment_time="+appPref.getAppointmentTime()+ "&doctor_id="+appPref.getDoctorIdByPatient()
						+"&patient_id="+InfoVerification.user_id+"&employer_id="+PreviousVisitScreen.employer_id;
				System.out.println(url_checkin);
				responseString1 = Cons.http_connection(url_checkin);
				try
				{
					checkinfoBeans=gson.fromJson(responseString1, BeansCheckinInfo.class);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				msg = "checkin_info";



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
			myMessage.obj = "check_in_complete";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("check_in_complete"))
			{
				if (!isFinishing()) 
				{
					if(msg.equals("contact_info"))
					{
						if(contactInfoBeans == null || Cons.isNetAvail==1)
						{
							Cons.isNetAvail = 0;
							Toast.makeText(ContactInfo.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
						}
						else
						{
							Toast.makeText(ContactInfo.this, contactInfoBeans.getMessage(), Toast.LENGTH_LONG).show();
						}
					}
					else
					{

						if((checkinfoBeans == null)||Cons.isNetAvail==1)

						{

							Cons.isNetAvail = 0;
							Toast.makeText(ContactInfo.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
						}

						else if(checkinfoBeans.getCode()==200)
						{

							Toast.makeText(ContactInfo.this, "Checkin successful.", Toast.LENGTH_LONG).show();

                                //if appointment booked by patient
                                if(appPref.getLogintype() == -1) {
                                    Intent i = new Intent(ContactInfo.this, HomeScreen.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                }
                                //if appointment booked by doctor office
                               else  if(appPref.getLogintype() == 2) {
                                    Intent i = new Intent(ContactInfo.this, DoctorOfficeHomeActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                }
							//if patient login
							else if(appPref.getLogintype() == 0)
							{
								Intent i = new Intent(ContactInfo.this,HomeScreenWithLogin.class);
								i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(i);	
							}

						}
						else 
						{

							Toast.makeText(ContactInfo.this, checkinfoBeans.getMessage(), Toast.LENGTH_LONG).show();
						}
					}


				}
			}


		}
	};



}
