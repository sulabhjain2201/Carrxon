package in.globalsoft.urncr;


import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class HospitalHomeScreen extends Activity 
{

	Button btn_addDoctor,btn_updateSchedule,btn_doctorList;
	ImageView btn_logOut;
	AppPreferences appPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital_home_screen);
		appPref = new AppPreferences(this);
		btn_addDoctor = (Button) findViewById(R.id.btn_adddoctor);
		btn_updateSchedule = (Button) findViewById(R.id.btn_updateSchedule);
		btn_doctorList = (Button) findViewById(R.id.btn_doctorList);
		btn_logOut = (ImageView) findViewById(R.id.logout_btn);
		
		btn_addDoctor.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				Intent i= new Intent(HospitalHomeScreen.this,AddDoctor.class);
				startActivity(i);
				
			}
		});
		
		btn_doctorList.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				Intent i= new Intent(HospitalHomeScreen.this,GetDoctorLIst.class);
				startActivity(i);
				
			}
		});
		btn_logOut.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				
				appPref.clearPref();
				Intent i = new Intent(HospitalHomeScreen.this,HomeScreen.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(i);
				finish();
				
			}
		});
		
	}

	

}
