package in.globalsoft.urncr;

import in.globalsoft.beans.BeansAddPatient;
import in.globalsoft.beans.BeansPatientInfo;
import in.globalsoft.urncr.R;
import in.globalsoft.util.Cons;
import in.globalsoft.util.ParseInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PatientInfo1 extends Activity 
{
	RelativeLayout layout_birthday,layout_gender;
	int mYear,mMonth,mDay;
	TextView tv_birthday,tv_gender;
	int DIALOG_BIRTHDAY = 5;
	AlertDialog dialog_gender;
	Button btn_info_next;
	EditText et_firstName,et_lastName,et_middleName;
	String str_firstName="",str_lastName="",str_middleName="";
	String str_gender="",str_birthday="";
	String str_patientId = "1";
	String responseString;
	public static BeansAddPatient addPatientBeans;
	public static BeansPatientInfo patientInfoBeans;
	public static String member_id="0";
	int pos=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_info1);
		if(getIntent().getExtras() != null)
		pos = getIntent().getExtras().getInt("position");

		layout_birthday = (RelativeLayout) findViewById(R.id.birthday_layout);
		tv_birthday = (TextView)findViewById(R.id.birthday_text);
		tv_gender = (TextView)findViewById(R.id.patient_gender_text);
		layout_gender = (RelativeLayout) findViewById(R.id.patient_gender_layout);
		btn_info_next = (Button) findViewById(R.id.patinet_info_next_btn);

		et_firstName = (EditText) findViewById(R.id.patient_first_name_text);
		et_middleName = (EditText) findViewById(R.id.patient_middle_name_text);
		et_lastName = (EditText) findViewById(R.id.patient_last_name_text);
		if(pos != -1)
		{
			preFillInfo();
		}

		layout_birthday.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				showDialog(DIALOG_BIRTHDAY);
			}
		});

		layout_gender.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				dialogPatientGender();
				dialog_gender.show();
			}
		});

		btn_info_next.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				str_firstName= et_firstName.getText().toString();
				str_lastName = et_lastName.getText().toString();
				str_middleName = et_middleName.getText().toString();
				if(str_firstName.equals(""))
				{
					Toast.makeText(PatientInfo1.this, "First name can not be left blank.", Toast.LENGTH_LONG).show();
				}
				else if(str_birthday.equals(""))
				{
					Toast.makeText(PatientInfo1.this, "Select Date of birth of patient.", Toast.LENGTH_LONG).show();
				}
				else if(str_gender.equals(""))
				{
					Toast.makeText(PatientInfo1.this, "Select gender of patient.", Toast.LENGTH_LONG).show();
				}

				else if(Cons.isNetworkAvailable(PatientInfo1.this))
				{
					new AddPatientTask(PatientInfo1.this).execute();
				}
				else
				{
					Toast.makeText(PatientInfo1.this, "Internet is not available.", Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	@Override
	protected Dialog onCreateDialog(int id)
	{

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);  
		return new DatePickerDialog(this,
				mDateSetListener,
				year, month, day);



	}

	private DatePickerDialog.OnDateSetListener mDateSetListener =
			new DatePickerDialog.OnDateSetListener() 
	{

		public void onDateSet(DatePicker view, int year, 
				int monthOfYear, int dayOfMonth) 
		{
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			System.out.println(mMonth);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date_birth = formatter.parse(Integer.toString(mYear)+"-" + String.valueOf(mMonth+1)+"-" + Integer.toString(mDay) );
				str_birthday =  df.format(date_birth);
				String changed_formate_birth = changeDateFormate(str_birthday);
				tv_birthday.setText(changed_formate_birth);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};

	public void dialogPatientGender() 
	{
		final ArrayList<String> list_gender = new ArrayList<String>();
		list_gender.add("Male");
		list_gender.add("Female");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, list_gender);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Choose Gender");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (item == 0) {


					dialog.cancel();
				} 
				else if (item == 1) {


					dialog.cancel();

				}

				tv_gender.setText(list_gender.get(item));
				if(list_gender.get(item).equals("Male"))
				{
					str_gender = "0";
				}
				else
				{
					str_gender = "1";
				}

			}
		});

		dialog_gender = builder.create();
	}

	public class AddPatientTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;

		public AddPatientTask(Context con)
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
			str_patientId = InfoVerification.user_id;
			if(pos==-1)
			{
				url = Cons.url_addPatient+"first_name="+str_firstName+"&last_name="+str_lastName
						+"&middle_initial="+str_middleName + "&dob="+str_birthday + "&gender="+str_gender
						+"&patient_id="+str_patientId;
				System.out.println("url:"+url);
			}
			else
			{
			
				patientInfoBeans = InfoVerification.patientListBeans.getMember_list().get(pos);
				member_id = patientInfoBeans.getMember_id();
				url = Cons.url_update_patient+"first_name="+str_firstName+"&last_name="+str_lastName
						+"&middle_initial="+str_middleName + "&dob="+str_birthday + "&gender="+str_gender
						+"&patient_id="+str_patientId+"&member_id="+ patientInfoBeans.getMember_id();
				System.out.println("url:"+url);
			}
			responseString = Cons.http_connection(url);
			System.out.println(responseString);
			if(responseString == null)
			{

			}
			else
			{
				ParseInfo parseInfo = new ParseInfo();
				addPatientBeans = parseInfo.parseAddPatient(responseString);
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
			myMessage.obj = "add_patient_task";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("add_patient_task"))
			{
				if (!isFinishing()) 
				{

					if((addPatientBeans == null)||Cons.isNetAvail==1)

					{

						Cons.isNetAvail = 0;
						Toast.makeText(PatientInfo1.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
					}

					else if(addPatientBeans.getCode()==200)
					{
						if(pos==-1)
						member_id = addPatientBeans.getMember_id();
						Intent i=new Intent(PatientInfo1.this,PatientInfo2.class);
						i.putExtra("strFirstName", str_firstName);
						i.putExtra("strLastName", str_lastName);
						startActivity(i);


					}
					else 
					{

						Toast.makeText(PatientInfo1.this, addPatientBeans.getMessage(), Toast.LENGTH_LONG).show();
					}



				}
			}


		}
	};

	public String changeDateFormate(String date)
	{
		SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd"); 
		Date dateObj = null;
		try 
		{
			dateObj = curFormater.parse(date);
		} 
		catch (ParseException e) 
		{

			e.printStackTrace();
		} 
		SimpleDateFormat postFormater = new SimpleDateFormat("MM-dd-yyyy"); 

		String newDateStr = postFormater.format(dateObj);
		return newDateStr;

	}

	public void preFillInfo()
	{
		BeansPatientInfo patientInfoBeans = InfoVerification.patientListBeans.getMember_list().get(pos);
		et_firstName.setText(patientInfoBeans.getFirst_name());
		et_lastName.setText(patientInfoBeans.getLast_name());
		et_middleName.setText(patientInfoBeans.getMiddle_initial());
		String gender="";
		if(patientInfoBeans.getGender().equals("0"))
		{
			gender = "Male";
		}
		else
			gender = "Female";
		tv_gender.setText(gender);
		str_birthday = patientInfoBeans.getDob();
		str_gender = patientInfoBeans.getGender();
		tv_birthday.setText(changeDateFormate(patientInfoBeans.getDob()));
	}


}
