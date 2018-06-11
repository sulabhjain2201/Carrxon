package in.globalsoft.urncr;


import in.globalsoft.urncr.R;
import in.globalsoft.util.Cons;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RegisterScreen1 extends Activity
{
	RelativeLayout layout_state;
	Button btn_submit;
	AlertDialog dialog_state;
	
	EditText et_firstname,et_lastname,et_address,et_cityname,et_zip,et_homephone,et_workcellphone,et_email,et_verifyEmail,etState;
	public static String str_firstname,str_lastname,str_address,str_cityname,str_state,str_zip,str_homephone,str_workcellphone,str_email,str_verifyEmail;
public static RegisterScreen1 reg1_obj;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_screen1);
		reg1_obj = this;
		defineLayouts();


		btn_submit.setOnClickListener(new OnClickListener(
				) {

			@Override
			public void onClick(View v) 
			{
				getEdittextValues();
				checkForBlank();
				
				

			}
		});

	
	}
	public void defineLayouts()
	{
		btn_submit= (Button)findViewById(R.id.register_btn);
		et_firstname = (EditText)findViewById(R.id.first_name);
		et_lastname= (EditText) findViewById(R.id.last_name);
		et_address = (EditText) findViewById(R.id.address);
		et_cityname = (EditText) findViewById(R.id.city_name);
		et_zip = (EditText) findViewById(R.id.zip);
		et_homephone = (EditText) findViewById(R.id.home_phone);
		et_workcellphone= (EditText) findViewById(R.id.work_cellphone);
		et_email = (EditText) findViewById(R.id.preferred_mail);
		et_verifyEmail = (EditText) findViewById(R.id.verified_mail);
		etState = (EditText) findViewById(R.id.etState);

	}

		public void getEdittextValues()
	{
		str_firstname = et_firstname.getText().toString();
		str_lastname = et_lastname.getText().toString();
		str_address = et_address.getText().toString();
		str_cityname = et_cityname.getText().toString();
		str_zip = et_zip.getText().toString();
		str_homephone = et_homephone.getText().toString();
		str_workcellphone = et_workcellphone.getText().toString();
		str_email = et_email.getText().toString();
		str_verifyEmail = et_verifyEmail.getText().toString();
		str_state =  etState.getText().toString();

	}
	public void checkForBlank()
	{
		if(str_firstname.equals(""))
		{
			Toast.makeText(RegisterScreen1.this, "First name can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_lastname.equals(""))
		{
			Toast.makeText(RegisterScreen1.this, "Last name can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_address.equals(""))
		{
			Toast.makeText(RegisterScreen1.this, "Address can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_cityname.equals(""))
		{
			Toast.makeText(RegisterScreen1.this, "City name can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_state.equals(""))
		{
			Toast.makeText(RegisterScreen1.this, "State can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_zip.equals(""))
		{
			Toast.makeText(RegisterScreen1.this, "Zip can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_homephone.equals(""))
		{
			Toast.makeText(RegisterScreen1.this, "Home Phone can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_workcellphone.equals(""))
		{
			Toast.makeText(RegisterScreen1.this, "Work/Cell can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(str_email.equals(""))
		{
			Toast.makeText(RegisterScreen1.this, "Email can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(!Cons.isValidEmail(str_email))
		{
			Toast.makeText(RegisterScreen1.this, "Please fill correct email", Toast.LENGTH_LONG).show();
		}
		else if(str_verifyEmail.equals(""))
		{
			Toast.makeText(RegisterScreen1.this, "Verify Email can not be blank", Toast.LENGTH_LONG).show();
		}
		else if(!str_email.equals(str_verifyEmail))
		{
			Toast.makeText(RegisterScreen1.this, "Primary mail and verify mail must be same.", Toast.LENGTH_LONG).show();
		}
		else
		{
			Intent i = new Intent(RegisterScreen1.this,RegisterScreen2.class);
			startActivity(i);
		}
	}

}
