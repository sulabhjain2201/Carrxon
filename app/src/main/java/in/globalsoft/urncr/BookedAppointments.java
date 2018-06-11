package in.globalsoft.urncr;

import in.globalsoft.beans.BeanBookedAppointment;
import in.globalsoft.beans.ListBookedAppointments;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.CalendarAdapter;
import in.globalsoft.util.Cons;
import in.globalsoft.util.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class BookedAppointments extends Activity {

	public GregorianCalendar month, itemmonth;// calendar instances.

	public CalendarAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
	// marker.
	public ArrayList<String> items; // container to store calendar items which
	// needs showing the event marker
	//ArrayList<String> event;
	private LinearLayout rLayout;
	private ArrayList<String> date;
	private ArrayList<String> desc;
	private AdapterView<?> parent;
	private View v;
	private int position;
	private ListBookedAppointments bookedApntList;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booked_appointments);
		Locale.setDefault(Locale.US);

		rLayout = (LinearLayout) findViewById(R.id.text);
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();

		items = new ArrayList<String>();

		adapter = new CalendarAdapter(this, month);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(adapter);

		handler = new Handler();
		handler.post(calendarUpdater);
		setAppointments();

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();

			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) 
			{
				if (((LinearLayout) rLayout).getChildCount() > 0) {
					((LinearLayout) rLayout).removeAllViews();
				}
				desc = new ArrayList<String>();
				date = new ArrayList<String>();
				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.dayString
						.get(position);
				System.out.println("selectedGridDate::"+selectedGridDate);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v);


				if(Cons.isNetworkAvailable(BookedAppointments.this))
				{
					new GetBookedAppointment(BookedAppointments.this, selectedGridDate).execute();
				}
				else
					Cons.showDialog(BookedAppointments.this, "Carrxon", "Internet connection is not available.", "OK");


				//				for (int i = 0; i < Utility.startDates.size(); i++) {
				//					if (Utility.startDates.get(i).equals(selectedGridDate)) {
				//						desc.add(Utility.nameOfEvent.get(i));
				//					}
				//				}
				//
				//				if (desc.size() > 0) {
				//					for (int i = 0; i < desc.size(); i++) {
				//						TextView rowTextView = new TextView(CalendarView.this);
				//
				//						// set some properties of rowTextView or something
				//						rowTextView.setText("Event:" + desc.get(i));
				//						rowTextView.setTextColor(Color.BLACK);
				//
				//						// add the textview to the linearlayout
				//						rLayout.addView(rowTextView);
				//
				//					}
				//
				//				}
				//
				//				desc = null;

			}

		});
	}

	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

	}

	protected void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

	}

	public void refreshCalendar() {
		TextView title = (TextView) findViewById(R.id.title);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some calendar items

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();

			// Print dates of the current week
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			String itemvalue;
			//			event = Utility.readCalendarEvent(CalendarView.this);
			//			Log.d("=====Event====", event.toString());
			Log.d("=====Date ARRAY====", Utility.startDates.toString());

			for (int i = 0; i < Utility.startDates.size(); i++) {
				itemvalue = df.format(itemmonth.getTime());
				itemmonth.add(GregorianCalendar.DATE, 1);
				items.add(Utility.startDates.get(i));
			}
			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};

	private void setAppointments()
	{
		if (((LinearLayout) rLayout).getChildCount() > 0) {
			((LinearLayout) rLayout).removeAllViews();
		}
		String selectedGridDate = Cons.convertDateToString(new Date(), "yyyy-MM-dd");
		if(Cons.isNetworkAvailable(BookedAppointments.this))
		{
			new GetBookedAppointment(BookedAppointments.this, selectedGridDate).execute();
		}
		else
			Cons.showDialog(BookedAppointments.this, "Carrxon", "Internet connection is not available.", "OK");


	}


	public class GetBookedAppointment extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;
		String date;


		public GetBookedAppointment(Context con,String date)
		{
			this.con = con;
			this.date = date;

			BookedAppointments.this.parent = parent;
			BookedAppointments.this.v = v;
			BookedAppointments.this.position = position;
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

			AppPreferences appPref = new AppPreferences(BookedAppointments.this);
			url = Cons.url_booked_appointments + "doctor_id="+appPref.getDoctorId()
					+"&date="+date;
			System.out.println("url::"+url);
			String responseString = Cons.http_connection(url);
			if(responseString != null)
			{
				System.out.println("responseString::"+responseString);
				Gson gson = new Gson();
				try
				{
				bookedApntList = gson.fromJson(responseString, ListBookedAppointments.class);
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
			//			url = Cons.url_doctorAddress+"query="+Uri.encode(tv_speciality.getText().toString()+" in "+et_doctorState.getText().toString());
			//			System.out.println("url:"+url);
			//
			//			responseString = Cons.http_connection(url);
			//			if(responseString !=null)
			//			System.out.println(responseString);
			//			Gson gson = new Gson();
			//			addressList = gson.fromJson(responseString, BeansDoctorGoogleAddressList.class);
			//		System.out.println(addressList);

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
			myMessage.obj = "booked_appointments";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}

	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("booked_appointments"))
			{
				if (!isFinishing()) 
				{

					if((bookedApntList == null)||Cons.isNetAvail==1)

					{

						Cons.isNetAvail = 0;
						Toast.makeText(BookedAppointments.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
					}

					else if(bookedApntList.getCode() == 200)
					{
						List<BeanBookedAppointment> listBookedAppointments = bookedApntList.getAppointment_list();
						if(listBookedAppointments != null && listBookedAppointments.size()>0)
						{
							for(int i=0;i<listBookedAppointments.size();i++)
							{
								BeanBookedAppointment bookedAppointment = listBookedAppointments.get(i);
								String strAppointmentTime = bookedAppointment.getAppointment_time();
								String strPatientName = bookedAppointment.getPatient_name();
								// removing the previous view if added

								Button rowTextView = new Button(BookedAppointments.this);
								LayoutParams lp = new    LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
								lp.bottomMargin = 20;
								
								rowTextView.setBackgroundResource(R.drawable.button_one);
								rowTextView.setPadding(10, 5, 0, 5);
								rowTextView.setTextSize(20);
								rowTextView.setLayoutParams(lp);
								rowTextView.setTextColor(Color.WHITE);
								rowTextView.setText(strAppointmentTime+"		"+strPatientName);
								rLayout.addView(rowTextView);



							}
						}
					}

					//					else if(BookedAppointments.getCode()==200)
					//					{
					//						List<BeansHospitalSchduleDetails> hospitalBeans = specificHospitalDetailBean.getDoctor_details().getDoctor_schedule_details();
					//						if(bitmap !=null)
					//						{
					//						image_hospital.setImageBitmap(bitmap);
					//						}
					//						else
					//							image_hospital.setBackgroundResource(R.drawable.place_holder);
					//						
					//						
					//						tv_address.setText(specificHospitalDetailBean.getDoctor_details().getAddress());
					//						tv_phone.setText(specificHospitalDetailBean.getDoctor_details().getDoctor_phone());
					//						
					//						
					//						String day_schedule = "";
					//						for(int i=0 ;i<hospitalBeans.size();i++)
					//						{
					//							day_schedule = day_schedule+hospitalBeans.get(i).getDay()+": "+ hospitalBeans.get(i).getDay_starting_time()+"-"+hospitalBeans.get(i).getDay_ending_time();
					//							if(i%2==0)
					//							{
					//								day_schedule = day_schedule+"\n";
					//							}
					//							else
					//								day_schedule = day_schedule+"\n";
					//						}
					//						
					//						tv_clinic_hours.setText(day_schedule);
					//						
					//						
					//					
					//						
					//					
					//
					//					}
					//					else 
					//					{
					//						
					//						Toast.makeText(DisplayDoctorInfo.this, "Hospital is not registered", Toast.LENGTH_LONG).show();
					//						finish();
					//					}



				}
			}


		}
	};


}
