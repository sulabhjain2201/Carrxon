package in.globalsoft.urncr;


import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home_Physician extends Activity
{

	Button btn_walkingUrgentCare,btn_findPhysian,btn_findFacility;
	AppPreferences appPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home__physician);
		btn_walkingUrgentCare = (Button) findViewById(R.id.walkin_urgent_care);
		btn_findPhysian = (Button) findViewById(R.id.find_physician);
		btn_findFacility= (Button) findViewById(R.id.find_facility);
		appPref = new AppPreferences(this);
		
		btn_walkingUrgentCare.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				Intent i = new Intent(Home_Physician.this,HospitalsMap1.class);
				appPref.saveMapType("Urgent Care Centers");
				startActivity(i);
				
			}
		});
		
		btn_findFacility.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(Home_Physician.this,FacilityList.class);
				startActivity(i);
				
			}
		});
		btn_findPhysian.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(Home_Physician.this,HospitalsMap1.class);
				appPref.saveMapType("Physician");
				startActivity(i);
				
			}
		});
	}

	
}
