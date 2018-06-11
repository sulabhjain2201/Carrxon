package in.globalsoft.urncr;

import in.globalsoft.beans.BeansAddInsuranceInfo;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InsuredPersonInfo extends Activity 
{

	Button btn_insuredPersonInfo;
	EditText et_firstName,et_lastName,et_insured_MI;
	TextView tv_dob;
	int mYear,mMonth,mDay;
	public static String str_birthday="";
	int DIALOG_BIRTHDAY = 5;
	RelativeLayout layout_birthday;
	public static String str_firstname="",str_lastname="",str_insuredMI="";
	AppPreferences appPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insured_person_info);
		appPreferences = new AppPreferences(this);
		defineLayouts();
		if(appPreferences.getInsuranceState()==1)
		{
			prefilledTexts();
		}
	
		
		btn_insuredPersonInfo.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				
				getEdittextValues();
				checkForBlank();
			}
		});
		
		layout_birthday.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v)
			{
				showDialog(DIALOG_BIRTHDAY);
			}
		});
	}

	
	public void defineLayouts()
	{
		et_firstName = (EditText) findViewById(R.id.insured_person_first_name_text);
		et_lastName = (EditText) findViewById(R.id.insured_person_last_name_text);
		et_insured_MI =  (EditText) findViewById(R.id.insured_mi_text);
		btn_insuredPersonInfo= (Button) findViewById(R.id.insured_person_next_btn);
		tv_dob = (TextView) findViewById(R.id.indured_person_birthday_text);
		layout_birthday = (RelativeLayout) findViewById(R.id.indured_person_birthday_layout);
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
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date_birth = formatter.parse(Integer.toString(mYear)+"-" + Integer.toString(mMonth+1)+"-" + Integer.toString(mDay) );
				str_birthday =  df.format(date_birth);
				String changed_formate_birth = changeDateFormate(str_birthday);
				tv_dob.setText(changed_formate_birth);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	public void getEdittextValues()
	{
		str_firstname = et_firstName.getText().toString();
		str_lastname = et_lastName.getText().toString();
		str_insuredMI = et_insured_MI.getText().toString();
		

	}
	public void checkForBlank()
	{
		if(str_firstname.equals(""))
		{
			Toast.makeText(InsuredPersonInfo.this, "First name can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_lastname.equals(""))
		{
			Toast.makeText(InsuredPersonInfo.this, "Last name can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_insuredMI.equals(""))
		{
			Toast.makeText(InsuredPersonInfo.this, "Insured MI can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_birthday.equals(""))
		{
			Toast.makeText(InsuredPersonInfo.this, "Date of Birth can not be blank", Toast.LENGTH_LONG).show();
		}
		
		else
		{
			Intent i=new Intent(InsuredPersonInfo.this,InsuranceInfo.class);
			startActivity(i);
		}
	}
	
	public void prefilledTexts()
	{
		BeansAddInsuranceInfo insuranceInfoBeans = PatientInfo2.insuranceInfoBeans.getInsurance_info();
		et_firstName.setText(insuranceInfoBeans.getFirst_name());
		et_lastName.setText(insuranceInfoBeans.getLast_name());
		et_insured_MI.setText(insuranceInfoBeans.getInsured_MI());
		tv_dob.setText(changeDateFormate(insuranceInfoBeans.getInsured_DOB()));
		str_birthday = insuranceInfoBeans.getInsured_DOB();
		
		
	}
	
	

	

}
