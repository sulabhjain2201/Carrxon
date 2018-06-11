package in.globalsoft.urncr;

import in.globalsoft.beans.BeanSpecificHospitalDetails;
import in.globalsoft.beans.BeansHospitalSchduleDetails;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class DisplayDoctorInfo extends Activity
{

	ImageView image_hospital;
	TextView tv_address,tv_phone,tv_clinic_hours,tv_doctorsname,tv_titleText;
	Button btn_continue,btn_updateSchdule,btn_skip;
	
	String responseString;
	BeanSpecificHospitalDetails specificHospitalDetailBean;
	Bitmap bitmap;
	AppPreferences appPref;
	Button btnChat;
	private ImageView btnInvite,btn_logout;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_doctor_info);
		appPref = new AppPreferences(this);
		btn_logout = (ImageView) findViewById(R.id.logout_btn);
		btnInvite = (ImageView) findViewById(R.id.invite_btn);
		btn_updateSchdule = (Button)findViewById(R.id.update_schdule_btn);
		Button btn_updateProfessionalInfo = (Button)findViewById(R.id.update_professionalInfo);
		
		tv_titleText = (TextView) findViewById(R.id.title_text);
		
		image_hospital = (ImageView) findViewById(R.id.hospital_image);
		tv_address = (TextView) findViewById(R.id.hospital_address);
		tv_phone = (TextView) findViewById(R.id.phone_no);
		
		tv_clinic_hours = (TextView)findViewById(R.id.clinic_hours);
		btn_continue = (Button) findViewById(R.id.continue_btn);


		
		
		if(Cons.isNetworkAvailable(DisplayDoctorInfo.this))
		{
			new HospitalDescTask(DisplayDoctorInfo.this).execute();
		}
		else
			Cons.showDialog(DisplayDoctorInfo.this, "Carrxon", "Internet connection is not available.", "OK");
		
//		
		
		btnChat=(Button) findViewById(R.id.btnChat);
		btnChat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i= new Intent(DisplayDoctorInfo.this,RecentChatActivity.class);
				startActivity(i);
			}
		});
		
		
		
		btn_continue.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent i= new Intent(DisplayDoctorInfo.this,UpateStatusInfo.class);
				startActivity(i);
				
			}
		});
		btn_updateSchdule.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent i= new Intent(DisplayDoctorInfo.this,AddClinicShedule.class);
				startActivity(i);
				
			}
		});
		
		btn_updateProfessionalInfo.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent i= new Intent(DisplayDoctorInfo.this,AddProfessionalInfo.class);
				startActivity(i);
				
			}
		});
	
		btn_logout.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				
				appPref.clearPref();
				Intent i = new Intent(DisplayDoctorInfo.this,HomeScreen.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(i);
				finish();
				
			}
		});
		
		Button btnBookedAppointments = (Button) findViewById(R.id.btnBookedAppointments);
		btnBookedAppointments.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				Intent i = new Intent(DisplayDoctorInfo.this,BookedAppointments.class);
				startActivity(i);
				
			}
		});

		btnInvite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				PopupMenu popup = new PopupMenu(DisplayDoctorInfo.this, btnInvite);
				popup.getMenuInflater().inflate(R.menu.popup_share_doctor, popup.getMenu());

				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						Intent sharingIntent = new Intent(Intent.ACTION_SEND);
						sharingIntent.setType("text/html");
						if(item.getItemId() == R.id.doctor_share){
							sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text_doctor));

						}
						else if(item.getItemId() == R.id.patient_share) {

							sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text_patient));

						}
						else {

							sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text_health_care));

						}
						startActivity(Intent.createChooser(sharingIntent,"Invite using"));
						return true;
					}
				});

				popup.show();



			}
		});
		
	}
	
	public class HospitalDescTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;

		public HospitalDescTask(Context con)
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
			
			String url = Cons.url_hospitalDetail+"doctor_id="+appPref.getDoctorId();
			System.out.println(url);
			responseString = Cons.http_connection(url);
			if(responseString != null)
			System.out.println(responseString);
			if(responseString == null)
			{

			}
			else
			{
				Gson gson = new Gson();
				try
				{
				specificHospitalDetailBean = gson.fromJson(responseString, BeanSpecificHospitalDetails.class);
				System.out.println(specificHospitalDetailBean);
				
				if(specificHospitalDetailBean.getCode()==200 && specificHospitalDetailBean.getDoctor_details().getDoctor_image() !=null && !specificHospitalDetailBean.getDoctor_details().getDoctor_image().equals(""))
				{
					bitmap = getBitmapFromURL("http://www.carrxon.com/CarrxonWebServices/ws/"+specificHospitalDetailBean.getDoctor_details().getDoctor_image());
				}
				}
				catch(Exception e)
				{
					e.printStackTrace();
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
			myMessage.obj = "hospital_detail_task";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("hospital_detail_task"))
			{
				if (!isFinishing()) 
				{

					if((specificHospitalDetailBean == null)||Cons.isNetAvail==1)

					{

						Cons.isNetAvail = 0;
						Toast.makeText(DisplayDoctorInfo.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
					}

					else if(specificHospitalDetailBean.getCode()==200)
					{
						List<BeansHospitalSchduleDetails> hospitalBeans = specificHospitalDetailBean.getDoctor_details().getDoctor_schedule_details();
						if(bitmap !=null)
						{
						image_hospital.setImageBitmap(bitmap);
						}
						else
							image_hospital.setBackgroundResource(R.drawable.place_holder);
						
						
						tv_address.setText(specificHospitalDetailBean.getDoctor_details().getAddress());
						tv_phone.setText(specificHospitalDetailBean.getDoctor_details().getDoctor_phone());
						
						
						String day_schedule = "";
						for(int i=0 ;i<hospitalBeans.size();i++)
						{
							day_schedule = day_schedule+hospitalBeans.get(i).getDay()+": "+ hospitalBeans.get(i).getDay_starting_time()+"-"+hospitalBeans.get(i).getDay_ending_time();
							if(i%2==0)
							{
								day_schedule = day_schedule+"\n";
							}
							else
								day_schedule = day_schedule+"\n";
						}
						
						tv_clinic_hours.setText(day_schedule);
						TextView tvTitle = (TextView) findViewById(R.id.title_text);
						tvTitle.setText(specificHospitalDetailBean.getDoctor_details().getDoctor_name());
						
						
					
						
					

					}
					else 
					{
						
						Toast.makeText(DisplayDoctorInfo.this, "Hospital is not registered", Toast.LENGTH_LONG).show();
						finish();
					}



				}
			}


		}
	};

	public static Bitmap getBitmapFromURL(String src) {
		try {

			URL url = new URL(src);
			URLConnection connection =  url.openConnection();

			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			//Drawable myBitmap=Drawable.createFromStream(input, "sulabh");
//			System.out.println(myBitmap.toString());
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}

	}



	

}
