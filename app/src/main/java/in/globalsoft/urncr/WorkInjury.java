package in.globalsoft.urncr;


import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WorkInjury extends Activity
{
	Button btn_first_doctor_visit;
	Button btn_follow_up_visit;
	Button btn_directed_to_carrxon;
	AppPreferences appPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work_injury);
		appPref = new AppPreferences(this);
		btn_first_doctor_visit = (Button) findViewById(R.id.first_doctor_visit_btn);
		btn_follow_up_visit = (Button) findViewById(R.id.followup_visit_btn);
		btn_directed_to_carrxon = (Button) findViewById(R.id.directed_to_carrxon_btn);
		
		btn_first_doctor_visit.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				Cons.showDialog(WorkInjury.this, "Carrxon", "We are sorry! Check-In can not be used for new Injuries.\n\n If this is the first doctor's visit for the work related injury, you will need to come to the clinic in person. Check-In is for follow-up visits only. ", "OK");
				
			}
		});
		
		btn_follow_up_visit.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				appPref.saveInsuranceState(0);
				Intent i = new Intent(WorkInjury.this,InfoVerification.class);
				startActivity(i);
				
			}
		});
		
		btn_directed_to_carrxon.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				appPref.saveInsuranceState(0);
				Intent i = new Intent(WorkInjury.this,PreviousVisitScreen.class);
				startActivity(i);	
				
			}
		});
		
	}

	

}
