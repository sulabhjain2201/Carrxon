package in.globalsoft.urncr;

import in.globalsoft.beans.BeansResponse;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.google.gson.Gson;

public class UpateStatusInfo extends Activity
{
	int hours;
	int minute;
	RelativeLayout rl_status,rl_waitingTime,rl_nextappointmentTime;
	TextView tv_status,tv_waitingTime,tv_nextappointmentTime;
	TextView common_text;
	String str_status;
	Dialog dialog_status;
	String responseString;
	BeansResponse responseBeans;
	AppPreferences appPref;
	Button update_btn;
	Button btn_skip;
	public static final int DIALOG_WAITING_TIME = 1;
	public static final int DIALOG_APPOINTMENT_TIME = 2;
	int waitingTime = 0;
	TimePickerDialog timeDialog;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_status);
		appPref = new AppPreferences(this);
		update_btn = (Button) findViewById(R.id.update_status_btn);
		btn_skip = (Button)findViewById(R.id.skip_btn);

		rl_status = (RelativeLayout) findViewById(R.id.status_layout);
		rl_waitingTime = (RelativeLayout) findViewById(R.id.waitingTimeLayout);
		rl_nextappointmentTime = (RelativeLayout) findViewById(R.id.nextAppointmentTimeLayout);

		tv_status = (TextView) findViewById(R.id.status_text);
		tv_waitingTime = (TextView) findViewById(R.id.waitingTime_text);
		tv_nextappointmentTime = (TextView) findViewById(R.id.nextAppointmentTime_text);



		rl_waitingTime.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				showCustomDialog();
				//timeDialog(tv_waitingTime.getId(),DIALOG_WAITING_TIME);	
			}
		});

		rl_nextappointmentTime.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View arg0) 
			{

				timeDialog(tv_nextappointmentTime.getId(),DIALOG_APPOINTMENT_TIME);	
			}
		});

		rl_status.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View arg0) 
			{

				dialogStatus();
				dialog_status.show();
			}
		});

		update_btn.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View arg0) 
			{
				if(str_status == null || tv_waitingTime.getText().toString().equals("Waiting Time") )
				{
					Toast.makeText(UpateStatusInfo.this, "All fields are manedatory.", Toast.LENGTH_SHORT).show();

				}
				else if(waitingTime == 0)
				{
					Toast.makeText(UpateStatusInfo.this, "Waiting Time can not be zero.", Toast.LENGTH_SHORT).show();
				}
				else if(Cons.isNetworkAvailable(UpateStatusInfo.this))	
				{
					new UpdateStatusTask(UpateStatusInfo.this).execute();
				}
				else
					Cons.showDialog(UpateStatusInfo.this, "Carrxon", "Internet is not available", "OK");
			}
		});

		btn_skip.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(UpateStatusInfo.this,DisplayDoctorInfo.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);

			}
		});



	}

	protected Dialog onCreateDialog(int id) 
	{


		int hour = 00;
		int min = 00;
		if(id == DIALOG_WAITING_TIME)
		{
			timeDialog = new TimePickerDialog(this, 
					timePickerListenerWaitingTime, hour, min,true);

		}
		else
		{
			timeDialog = new TimePickerDialog(this, 
					timePickerListenerAppointmentTime, hour, min,true);	
		}
		//timeDialog.setTitle("Waiting Time in (HH:mm)");

		return timeDialog;



	}

	private TimePickerDialog.OnTimeSetListener timePickerListenerWaitingTime = 
			new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			timeDialog.setTitle("Waiting Time in (HH:mm)");
			hours = selectedHour;
			minute = selectedMinute;
			waitingTime = hours*60+minute;



			// set current time into textview
			common_text.setText(new StringBuilder().append(padding_str(hours))
					.append(":").append(padding_str(minute)));


			// set current time into timepicker


		}

	};
	private TimePickerDialog.OnTimeSetListener timePickerListenerAppointmentTime = 
			new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			timeDialog.setTitle("Waiting Time in (HH:mm)");
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

	public void timeDialog(int id,int dialogId)
	{
		common_text = (TextView) findViewById(id);
		showDialog(dialogId);
	}

	public void dialogStatus() 
	{
		final ArrayList<String> list_status = new ArrayList<String>();
		list_status.add("Close");
		list_status.add("Open");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, list_status);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Choose status");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (item == 0) {


					dialog.cancel();
				} 
				else if (item == 1) {


					dialog.cancel();

				}

				tv_status.setText(list_status.get(item));
				if(list_status.get(item).equals("Close"))
				{
					str_status = "0";
				}
				else
				{
					str_status = "1";
				}

			}
		});

		dialog_status = builder.create();
	}

	public class UpdateStatusTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;

		public UpdateStatusTask(Context con)
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
			url = Cons.url_update_statusInfo+"doctor_id="+appPref.getDoctorId()+"&waiting_time="+waitingTime
					+"&next_appointment=12:00"
					+"&open_status="+str_status;
			;
			System.out.println("url::"+url);
			responseString = Cons.http_connection(url);	
			if(responseString !=null)
				System.out.println(responseString);
			Gson gson = new Gson();
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
			myMessage.obj = "update_status";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("update_status"))
			{
				if (!isFinishing()) 
				{


					if((responseBeans == null))

					{


						Toast.makeText(UpateStatusInfo.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
					}

					else if(responseBeans.getCode()==200)
					{

						Toast.makeText(UpateStatusInfo.this, "Status successfully updated.", Toast.LENGTH_LONG).show();
						Intent i = new Intent(UpateStatusInfo.this,DisplayDoctorInfo.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);



					}
					else 
					{
						Toast.makeText(UpateStatusInfo.this, responseBeans.getMessage(), Toast.LENGTH_LONG).show();
					}
				}




			}


		}
	};

	private void showCustomDialog()
	{
		LayoutInflater li = LayoutInflater.from(UpateStatusInfo.this);
		View promptsView = li.inflate(R.layout.dialog_time_picker, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				UpateStatusInfo.this);
		alertDialogBuilder.setTitle("Waiting Time (HH:mm)");

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final TimePicker tpWaitingTime = (TimePicker) promptsView
				.findViewById(R.id.tpWaitingTime);
		tpWaitingTime.setIs24HourView(true);
		tpWaitingTime.setCurrentHour(0);
		tpWaitingTime.setCurrentMinute(0);
		
		
		tpWaitingTime.setOnTimeChangedListener(new OnTimeChangedListener() {
			   
			   @Override
			   public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
				   waitingTime = arg1*60+arg2;
			    hours  = arg1;
			    minute = arg2;
			   }
			  });

		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("SET",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    	tv_waitingTime.setText(new StringBuilder().append(padding_str(hours))
							.append(":").append(padding_str(minute)));
				
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}







}
