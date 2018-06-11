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
import java.util.Calendar;
import java.util.Date;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class HospitalDescription extends Activity 
{
	RelativeLayout relative_left_continer;
	ImageView image_hospital;
	TextView tv_address,tv_phone,tv_clinic_hours,tv_doctorsname,tv_titleText;
	Button btn_continue;
	int width,height;
	int position;
	String responseString;
	BeanSpecificHospitalDetails specificHospitalDetailBean;
	Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital_description);
		position = getIntent().getIntExtra("position", 0);

		tv_titleText = (TextView) findViewById(R.id.title_text);
		tv_titleText.setText(HospitalsMap1.hospitalListBeans.getHospital_list().get(position).getName());
		image_hospital = (ImageView) findViewById(R.id.hospital_image);
		tv_address = (TextView) findViewById(R.id.hospital_address);
		tv_phone = (TextView) findViewById(R.id.phone_no);

		tv_clinic_hours = (TextView)findViewById(R.id.clinic_hours);
		btn_continue = (Button) findViewById(R.id.continue_btn);

		if(Cons.isNetworkAvailable(HospitalDescription.this))
		{
			new HospitalDescTask(HospitalDescription.this).execute();
		}
		else
			Cons.showDialog(HospitalDescription.this, "Carrxon", "Internet connection is not available.", "OK");

		//		
		btn_continue.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent i= new Intent(HospitalDescription.this,AvailableAppointments.class);
				startActivity(i);

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
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			int dayId = calendar.get(Calendar.DAY_OF_WEEK)-1;
			String selectedGridDate = Cons.convertDateToString(new Date(), "yyyy-MM-dd");
			String url = Cons.url_doctorDetailWithAvailableAppointment+"doctor_id="+HospitalsMap1.hospitalListBeans.getHospital_list().get(position).getDoctor_id()
					+"&date="+selectedGridDate+"&day_id="+dayId;
			System.out.println("docotrInfo::"+url);
			AppPreferences appPef = new AppPreferences(HospitalDescription.this);
			appPef.saveDoctorIdByPatient(HospitalsMap1.hospitalListBeans.getHospital_list().get(position).getDoctor_id());

			responseString = Cons.http_connection(url);
			if(responseString == null)
			{

			}
			else
			{
				System.out.println("responseString::"+responseString);	
				Gson gson = new Gson();
				try
				{
					specificHospitalDetailBean = gson.fromJson(responseString, BeanSpecificHospitalDetails.class);
					System.out.println(specificHospitalDetailBean);

					if(specificHospitalDetailBean.getCode()==200 && !specificHospitalDetailBean.getDoctor_details().getDoctor_image().equals(""))
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

					if((specificHospitalDetailBean == null))

					{


						Toast.makeText(HospitalDescription.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
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
						TextView tvSpeciality = (TextView) findViewById(R.id.specialty);
						//tvSpeciality.setText(specificHospitalDetailBean.getDoctor_details().getSpeciality());
						AppPreferences appPref = new AppPreferences(HospitalDescription.this);

						tvSpeciality.setText(appPref.getMapType());

						TextView tvExperience = (TextView) findViewById(R.id.experience);
						tvExperience.setText(specificHospitalDetailBean.getDoctor_details().getExperience()+" years");

						TextView tvFees = (TextView) findViewById(R.id.fees);
						tvFees.setText(specificHospitalDetailBean.getDoctor_details().getFees());
						
						TextView tvEducation = (TextView) findViewById(R.id.education);
						if(!specificHospitalDetailBean.getDoctor_details().getEducation().equals(""))
							tvEducation.setText(specificHospitalDetailBean.getDoctor_details().getEducation());
						else
							tvEducation.setText("No Education details found.");	
						
						TextView tvServices = (TextView) findViewById(R.id.services);
						if(specificHospitalDetailBean.getDoctor_details().getServices()!= null && !specificHospitalDetailBean.getDoctor_details().getServices().equals(""))
							tvServices.setText(specificHospitalDetailBean.getDoctor_details().getServices());
						else
							tvServices.setText("No Service details found.");	
						

						TextView tvLanguages = (TextView) findViewById(R.id.languages);
						List<String> langList = specificHospitalDetailBean.getDoctor_details().getLanguage();
						String strLang = "";
						for(int i=0;i<langList.size();i++)
						{
							strLang = strLang + langList.get(i) + "\n";
						}
						if(strLang.equals(""))
							strLang = "No Language details found ";
						tvLanguages.setText(strLang);

						TextView tvInsurances = (TextView) findViewById(R.id.insurances);
						List<String> insuranceList = specificHospitalDetailBean.getDoctor_details().getDoc_insurance_name();
						String strInsurances = "";
						if(insuranceList != null)
						{
							for(int i=0;i<insuranceList.size();i++)
							{
								strInsurances = strInsurances + insuranceList.get(i) + "\n";
							}
							if(strInsurances.equals(""))
								strInsurances = "No Insurance details found ";
							
						}
						else if(strInsurances.equals(""))
							strInsurances = "No Insurance details found ";
						tvInsurances.setText(strInsurances);

						String day_schedule = "";
						for(int i=0 ;i<hospitalBeans.size();i++)
						{
							day_schedule = day_schedule+hospitalBeans.get(i).getDay()+": "+ Cons.changeDateFormat(hospitalBeans.get(i).getDay_starting_time(),"hh:mm:ss","hh:mm a")+"-"+ Cons.changeDateFormat(hospitalBeans.get(i).getDay_ending_time(),"hh:mm:ss","hh:mm a");
							if(i%2==0)
							{
								day_schedule = day_schedule+"\n";
							}
							else
								day_schedule = day_schedule+"\n";
						}

						tv_clinic_hours.setText(day_schedule);





					}
					else 
					{

						Toast.makeText(HospitalDescription.this, "Hospital is not registered", Toast.LENGTH_LONG).show();
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
			System.out.println(myBitmap.toString());
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();

			return null;
		}

	}



}
