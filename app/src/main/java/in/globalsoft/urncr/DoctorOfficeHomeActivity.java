package in.globalsoft.urncr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;

public class DoctorOfficeHomeActivity extends Activity
{
	TextView tvTextChat,tvBookAppointment;
    ImageView btn_logOut;
    AppPreferences appPref ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor_office_home_activity);
		tvTextChat=(TextView) findViewById(R.id.tvTextChat);
		tvBookAppointment=(TextView) findViewById(R.id.tvBookAppointment);
        btn_logOut = (ImageView) findViewById(R.id.logout_btn);
        appPref = new AppPreferences(this);

		setClickListeners();




	}

	private void setClickListeners() {
		// TODO Auto-generated method stub


		tvTextChat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(DoctorOfficeHomeActivity.this,RecentOfficeChat.class);
				startActivity(i);
			}
		});


		tvBookAppointment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(DoctorOfficeHomeActivity.this,RecentOfficePatientsForAppointment.class);
				startActivity(i);
			
			
			}
		});

        btn_logOut.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                appPref.clearPref();
                Intent i = new Intent(DoctorOfficeHomeActivity.this,HomeScreen.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
        });




	}

}
