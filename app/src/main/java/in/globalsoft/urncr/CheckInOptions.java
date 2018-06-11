package in.globalsoft.urncr;


import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CheckInOptions extends Activity
{
	Button btn_insurance;
	Button btn_privatePay;
	Button btn_workInjury;
	AppPreferences appPref;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_in_options);
		appPref = new AppPreferences(this);
		btn_insurance = (Button) findViewById(R.id.use_insurance_btn);
		btn_privatePay = (Button) findViewById(R.id.private_pay_btn);
		btn_workInjury= (Button) findViewById(R.id.my_employer_authorised_btn);
		btn_insurance.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				if(PreviousVisitScreen.employer_id != null && !PreviousVisitScreen.employer_id.equals("0"))
					PreviousVisitScreen.employer_id = "0";
				appPref.isInsuranceScreen(1);
				Intent i = new Intent(CheckInOptions.this,InfoVerification.class);
				startActivity(i);
				
			}
		});
		
		btn_privatePay.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				if(PreviousVisitScreen.employer_id != null && !PreviousVisitScreen.employer_id.equals("0"))
					PreviousVisitScreen.employer_id = "0";
				appPref.isInsuranceScreen(0);
				Intent i = new Intent(CheckInOptions.this,InfoVerification.class);
				startActivity(i);
				
			}
		});
		
		btn_workInjury.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				Intent i = new Intent(CheckInOptions.this,WorkInjury.class);
				startActivity(i);
			}
		});
	}

	

}
