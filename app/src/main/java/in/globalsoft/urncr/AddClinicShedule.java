package in.globalsoft.urncr;

import in.globalsoft.beans.BeansResponse;
import in.globalsoft.beans.BeansScheduleInfo;
import in.globalsoft.beans.BeansScheduleList;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

public class AddClinicShedule extends Activity 
{
	TextView tv_monday_from_time,tv_tuesday_from_time,tv_wednesday_from_time,tv_thursday_from_time,tv_friday_from_time,tv_saturday_from_time,tv_sunday_from_time;
	TextView tv_monday_to_time,tv_tuesday_to_time,tv_wednesday_to_time,tv_thursday_to_time,tv_friday_to_time,tv_saturday_to_time,tv_sunday_to_time;


	RelativeLayout rl_monday_from_time,rl_tuesday_from_time,rl_wednesday_from_time,rl_thursday_from_time,rl_friday_from_time,rl_saturday_from_time,rl_sunday_from_time;
	RelativeLayout rl_monday_to_time,rl_tuesday_to_time,rl_wednesday_to_time,rl_thursday_to_time,rl_friday_to_time,rl_saturday_to_time,rl_sunday_to_time;
	
	String str_monday_from_time,str_tuesday_from_time,str_wednesday_from_time,str_thursday_from_time,str_friday_from_time,str_saturday_from_time,str_sunday_from_time;
	String str_monday_to_time,str_tuesday_to_time,str_wednesday_to_time,str_thursday_to_time,str_friday_to_time,str_saturday_to_time,str_sunday_to_time;
	
	public final int TIME_DIALOG_ID = 1;
	int hours,minute;
	TextView common_text;
	
	AppPreferences appPref;
	
	Button next,btn_skip;
	public static BeansResponse responseBeans;
	public static BeansScheduleList scheduleListBeans;
	String responseString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_clinic_shedule);
		str_monday_from_time = str_tuesday_from_time = str_wednesday_from_time =str_thursday_from_time = str_friday_from_time = str_saturday_from_time = str_sunday_from_time = "09:00";
		str_monday_to_time = str_tuesday_to_time = str_wednesday_to_time =str_thursday_to_time = str_friday_to_time = str_saturday_to_time = str_sunday_to_time = "17:00";
		appPref = new AppPreferences(this);
		
		
		defineIds();
		
		rl_monday_from_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_monday_from_time.getId());
				
			}
		});
		
		rl_wednesday_from_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_wednesday_from_time.getId());
				
			}
		});
		
		rl_tuesday_from_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_tuesday_from_time.getId());
				
			}
		});
		
		rl_thursday_from_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_thursday_from_time.getId());
				
			}
		});
		
		rl_friday_from_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_friday_from_time.getId());
				
			}
		});
		
		rl_saturday_from_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_saturday_from_time.getId());
				
			}
		});
		
		rl_sunday_from_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_sunday_from_time.getId());
				
			}
		});
		
		rl_monday_to_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_monday_to_time.getId());
				
			}
		});
		
		rl_wednesday_to_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_wednesday_to_time.getId());
				
			}
		});
		
		rl_tuesday_to_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_tuesday_to_time.getId());
				
			}
		});
		
		rl_thursday_to_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_thursday_to_time.getId());
				
			}
		});
		
		rl_friday_to_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_friday_to_time.getId());
				
			}
		});
		
		rl_saturday_to_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_saturday_to_time.getId());
				
			}
		});
		
		rl_sunday_to_time.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				timeDialog(tv_sunday_to_time.getId());
				
			}
		});
		
		next.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				getValues();
			//	setValuesInBean();
			
				
				
			}
		});
		
		btn_skip.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				
				Intent i=new Intent(AddClinicShedule.this,UpateStatusInfo.class);
				startActivity(i);
			}
		});
	
		
		
		
		
		
	}
	
	public void defineIds()
	{
		btn_skip = (Button) findViewById(R.id.skip_btn);
		tv_monday_from_time = (TextView) findViewById(R.id.monday_from_time_text);
		tv_monday_to_time = (TextView) findViewById(R.id.monday_to_time_text);
		
		tv_tuesday_from_time = (TextView) findViewById(R.id.tuesday_from_time_text);
		tv_tuesday_to_time = (TextView) findViewById(R.id.tuesday_to_time_text);
		
		tv_wednesday_from_time = (TextView) findViewById(R.id.wednesday_from_time_text);
		tv_wednesday_to_time = (TextView) findViewById(R.id.wednesday_to_time_text);
		
		tv_thursday_from_time = (TextView) findViewById(R.id.thursday_from_time_text);
		tv_thursday_to_time = (TextView) findViewById(R.id.thursday_to_time_text);
		
		tv_friday_from_time = (TextView) findViewById(R.id.friday_from_time_text);
		tv_friday_to_time = (TextView) findViewById(R.id.friday_to_time_text);
		
		tv_saturday_from_time = (TextView) findViewById(R.id.saturday_from_time_text);
		tv_saturday_to_time = (TextView) findViewById(R.id.saturday_to_time_text);
		
		tv_sunday_from_time = (TextView) findViewById(R.id.sunday_from_time_text);
		tv_sunday_to_time = (TextView) findViewById(R.id.sunday_to_time_text);
		
		rl_monday_from_time = (RelativeLayout) findViewById(R.id.monday_from_time_layout);
		rl_monday_to_time = (RelativeLayout) findViewById(R.id.monday_to_time_layout);
		
		rl_tuesday_from_time = (RelativeLayout) findViewById(R.id.tuesday_from_time_layout);
		rl_tuesday_to_time = (RelativeLayout) findViewById(R.id.tuesday_to_time_layout);
		
		rl_wednesday_from_time = (RelativeLayout) findViewById(R.id.wednesday_from_time_layout);
		rl_wednesday_to_time = (RelativeLayout) findViewById(R.id.wednesday_to_time_layout);
		
		rl_thursday_from_time = (RelativeLayout) findViewById(R.id.thursday_from_time_layout);
		rl_thursday_to_time = (RelativeLayout) findViewById(R.id.thursday_to_time_layout);
		
		rl_friday_from_time = (RelativeLayout) findViewById(R.id.friday_from_time_layout);
		rl_friday_to_time = (RelativeLayout) findViewById(R.id.friday_to_time_layout);
		
		rl_saturday_from_time = (RelativeLayout) findViewById(R.id.saturday_from_time_layout);
		rl_saturday_to_time = (RelativeLayout) findViewById(R.id.saturday_to_time_layout);
		
		rl_sunday_from_time = (RelativeLayout) findViewById(R.id.sunday_from_time_layout);
		rl_sunday_to_time = (RelativeLayout) findViewById(R.id.sunday_to_time_layout);
		
		next = (Button) findViewById(R.id.next_step);
		
	}
	public void timeDialog(int id)
	{
		common_text = (TextView) findViewById(id);
		showDialog(1);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) 
	{
		
				
			int hour = 00;
			int min = 00;
			return new TimePickerDialog(this, 
                                        timePickerListener, hour, min,false);
 
	
		
	}
 
	private TimePickerDialog.OnTimeSetListener timePickerListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hours = selectedHour;
			minute = selectedMinute;
 
			// set current time into textview
			common_text.setText(new StringBuilder().append(padding_str(hours))
					.append(":").append(padding_str(minute)));
 
			// set current time into timepicker
		
 
		}
	};
	
	private static String padding_str(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
	
	public void getValues()
	{
		str_monday_from_time = tv_monday_from_time.getText().toString();
		str_tuesday_from_time = tv_tuesday_from_time.getText().toString();
		str_wednesday_from_time = tv_wednesday_from_time.getText().toString();
		str_thursday_from_time = tv_thursday_from_time.getText().toString();
		str_friday_from_time = tv_friday_from_time.getText().toString();
		str_saturday_from_time = tv_saturday_from_time.getText().toString();
		str_sunday_from_time = tv_sunday_from_time.getText().toString();
		
		str_monday_to_time = tv_monday_to_time.getText().toString();
		str_tuesday_to_time = tv_tuesday_to_time.getText().toString();
		str_wednesday_to_time = tv_wednesday_to_time.getText().toString();
		str_thursday_to_time = tv_thursday_to_time.getText().toString();
		str_friday_to_time = tv_friday_to_time.getText().toString();
		str_saturday_to_time = tv_saturday_to_time.getText().toString();
		str_sunday_to_time = tv_sunday_to_time.getText().toString();
		
		if(str_monday_from_time.equals("")||str_monday_to_time.equals("")||str_tuesday_from_time.equals("")||str_tuesday_to_time.equals("")||str_wednesday_from_time.equals("")||str_wednesday_to_time.equals("")||str_thursday_from_time.equals("")||str_thursday_to_time.equals("")||str_friday_from_time.equals("")||str_friday_to_time.equals("")||str_saturday_from_time.equals("")||str_saturday_to_time.equals("")||str_sunday_from_time.equals("")||str_sunday_to_time.equals(""))
		{
			Toast.makeText(AddClinicShedule.this,"Please fill complete Information" , Toast.LENGTH_LONG).show();
		}
		else if(!Cons.convertStringToDate(str_monday_from_time, "hh:mm").before(Cons.convertStringToDate(str_monday_to_time, "hh:mm")))
		{
			Toast.makeText(AddClinicShedule.this,"Enter correct clinic schedule for monday" , Toast.LENGTH_LONG).show();
		}
		else if(!Cons.convertStringToDate(str_tuesday_from_time, "hh:mm").before(Cons.convertStringToDate(str_tuesday_to_time, "hh:mm")))
		{
			Toast.makeText(AddClinicShedule.this,"Enter correct clinic schedule for tuesday" , Toast.LENGTH_LONG).show();
		}
		else if(!Cons.convertStringToDate(str_wednesday_from_time, "hh:mm").before(Cons.convertStringToDate(str_wednesday_to_time, "hh:mm")))
		{
			Toast.makeText(AddClinicShedule.this,"Enter correct clinic schedule for wednesday" , Toast.LENGTH_LONG).show();
		}
		else if(!Cons.convertStringToDate(str_thursday_from_time, "hh:mm").before(Cons.convertStringToDate(str_thursday_to_time, "hh:mm")))
		{
			Toast.makeText(AddClinicShedule.this,"Enter correct clinic schedule for thursday" , Toast.LENGTH_LONG).show();
		}
		else if(!Cons.convertStringToDate(str_friday_from_time, "hh:mm").before(Cons.convertStringToDate(str_friday_to_time, "hh:mm")))
		{
			Toast.makeText(AddClinicShedule.this,"Enter correct clinic schedule for friday" , Toast.LENGTH_LONG).show();
		}
		else if(!Cons.convertStringToDate(str_saturday_from_time, "hh:mm").before(Cons.convertStringToDate(str_saturday_to_time, "hh:mm")))
		{
			Toast.makeText(AddClinicShedule.this,"Enter correct clinic schedule for saturday" , Toast.LENGTH_LONG).show();
		}
		else if(!Cons.convertStringToDate(str_sunday_from_time, "hh:mm").before(Cons.convertStringToDate(str_sunday_to_time, "hh:mm")))
		{
			Toast.makeText(AddClinicShedule.this,"Enter correct clinic schedule for sunday" , Toast.LENGTH_LONG).show();
		}
		else
		{
		setValuesInBean();
		}
	}
	
	public void setValuesInBean()
	{
		scheduleListBeans = new BeansScheduleList();
		List<BeansScheduleInfo> schedule_info_beans = new ArrayList<BeansScheduleInfo>();
		BeansScheduleInfo schedule_mondayBean = new BeansScheduleInfo();
		schedule_mondayBean.setDay("Monday");
		schedule_mondayBean.setDay_ending_time(str_monday_to_time);
		schedule_mondayBean.setDay_starting_time(str_monday_from_time);
		schedule_info_beans.add(schedule_mondayBean);
		
		BeansScheduleInfo schedule_tuesdayBean = new BeansScheduleInfo();
		schedule_tuesdayBean.setDay("Tuesday");
		schedule_tuesdayBean.setDay_ending_time(str_tuesday_to_time);
		schedule_tuesdayBean.setDay_starting_time(str_tuesday_from_time);
		schedule_info_beans.add(schedule_tuesdayBean);
		
		BeansScheduleInfo schedule_wednesdayBean = new BeansScheduleInfo();
		schedule_wednesdayBean.setDay("Wednesday");
		schedule_wednesdayBean.setDay_ending_time(str_wednesday_to_time);
		schedule_wednesdayBean.setDay_starting_time(str_wednesday_from_time);
		schedule_info_beans.add(schedule_wednesdayBean);
		
		BeansScheduleInfo schedule_thursdayBean = new BeansScheduleInfo();
		schedule_thursdayBean.setDay("Thursday");
		schedule_thursdayBean.setDay_ending_time(str_thursday_to_time);
		schedule_thursdayBean.setDay_starting_time(str_thursday_from_time);
		schedule_info_beans.add(schedule_thursdayBean);
		
		BeansScheduleInfo schedule_fridayBean = new BeansScheduleInfo();
		schedule_fridayBean.setDay("Friday");
		schedule_fridayBean.setDay_ending_time(str_friday_to_time);
		schedule_fridayBean.setDay_starting_time(str_friday_from_time);
		schedule_info_beans.add(schedule_fridayBean);
		
		BeansScheduleInfo schedule_saturdayBean = new BeansScheduleInfo();
		schedule_saturdayBean.setDay("Saturday");
		schedule_saturdayBean.setDay_ending_time(str_saturday_to_time);
		schedule_saturdayBean.setDay_starting_time(str_saturday_from_time);
		schedule_info_beans.add(schedule_saturdayBean);
		
		BeansScheduleInfo schedule_sundayBean = new BeansScheduleInfo();
		schedule_sundayBean.setDay("Sunday");
		schedule_sundayBean.setDay_ending_time(str_sunday_to_time);
		schedule_sundayBean.setDay_starting_time(str_sunday_from_time);
		schedule_info_beans.add(schedule_sundayBean);
		scheduleListBeans.setSchedule_info(schedule_info_beans);
		
		if(Cons.isNetworkAvailable(this))
		{
			new AddScheduleInfoTask(this).execute();
		}
		else
			Cons.showDialog(this, "Carrxon", "Internet is not available", "OK");
		
	}
	
	public class AddScheduleInfoTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;

		public AddScheduleInfoTask(Context con)
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
			String schedule = gson.toJson(scheduleListBeans);
				url = Cons.url_addScheduleInfo+"doctor_id="+appPref.getDoctorId()+"&schedule_info="+schedule ;
				System.out.println("url::"+url);
				responseString = Cons.http_connection(url);	

			
				responseBeans=gson.fromJson(responseString, BeansResponse.class);

			




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
			myMessage.obj = "get_shcedule_info";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("get_shcedule_info"))
			{
				if (!isFinishing()) 
				{
					

						if((responseBeans == null))

						{

							Toast.makeText(AddClinicShedule.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
						}

						else if(responseBeans.getCode()==200)
						{

							Intent i=new Intent(AddClinicShedule.this,UpateStatusInfo.class);
							startActivity(i);
							


						}
						else 
						{
							Toast.makeText(AddClinicShedule.this, responseBeans.getMessage(), Toast.LENGTH_LONG).show();
						}
					}
					


				
			}


		}
	};


	
}
