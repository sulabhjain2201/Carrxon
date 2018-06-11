package in.globalsoft.urncr;

import org.json.JSONException;
import org.json.JSONObject;

import in.globalsoft.urncr.R;
import in.globalsoft.interfaces.GetChatResult;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.tasks.OfficeRegistrationTask;
import in.globalsoft.util.Cons;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DoctorOfficeInfo extends Activity implements GetChatResult{

	EditText etUsername,etPassword,etEmail,etName;
	TextView tvSubmit;
	AppPreferences appPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor_office_form_screen);
		appPref = new AppPreferences(this);
		etUsername=(EditText) findViewById(R.id.etUsername);
		etPassword=(EditText) findViewById(R.id.etPassword);
		etEmail=(EditText) findViewById(R.id.etEmail);
		tvSubmit=(TextView) findViewById(R.id.tvSubmit);
		etName=(EditText) findViewById(R.id.etName);

		tvSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(etName.getText().toString().equals(""))
					etName.setHintTextColor(getResources().getColor(R.color.red));
				else if(etUsername.getText().toString().equals(""))
					etUsername.setHintTextColor(getResources().getColor(R.color.red));
				else if(etPassword.getText().toString().equals(""))
					etPassword.setHintTextColor(getResources().getColor(R.color.red));
				else if(etEmail.getText().toString().equals(""))
					etEmail.setHintTextColor(getResources().getColor(R.color.red));
				else
				{
					String url=Cons.url_doctor_office_registration+appPref.getDoctorId()+"&email="+etEmail.getText().toString()+"&username="+etUsername.getText().toString()+"&name="+etName.getText().toString()+"&password="+etPassword.getText().toString();
					new OfficeRegistrationTask(DoctorOfficeInfo.this, url).execute();
				}


			}
		});






	}
	@Override
	public void ChatResult(String result) 
	{
		// TODO Auto-generated method stub
		try {
			JSONObject jObject=new JSONObject(result);
		String code=jObject.getString("code");
		String message=jObject.getString("message");
		if(code.equals("100"))
		{
			Intent i=new Intent(DoctorOfficeInfo.this,AddProfessionalInfo.class);
			startActivity(i);
		}
		
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}


}
