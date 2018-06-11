package in.globalsoft.urncr;


import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CheckInScreen extends Activity
{

	Button btn_checkin;
	AppPreferences appPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_in_screen);
		btn_checkin = (Button)findViewById(R.id.checkin_btn);
		
		
		btn_checkin.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(CheckInScreen.this,ListBusinessPersons.class);
				
				
				startActivity(intent);
				
			}
		});
	}

	

}
