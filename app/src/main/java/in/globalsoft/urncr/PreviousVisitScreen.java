package in.globalsoft.urncr;

import in.globalsoft.beans.BeansAddEmployer;
import in.globalsoft.urncr.R;
import in.globalsoft.util.Cons;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class PreviousVisitScreen extends Activity 
{
	Button btn_next;
	EditText et_employerName,et_authorizedPerson,et_prevoisTreatment;
	String str_employerName,str_authorizedPerson,str_previousTreatment;
	String responseString;
	BeansAddEmployer addEmployerBeans;
	public static String employer_id = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_previous_visit_screen);
		btn_next= (Button)findViewById(R.id.previous_visit_next_btn);
		et_employerName = (EditText) findViewById(R.id.employer_name);
		et_authorizedPerson = (EditText) findViewById(R.id.authorized_person_name);
		et_prevoisTreatment = (EditText) findViewById(R.id.previous_treatment);
		
		btn_next.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				getEditTextValues();
				if(Cons.isNetworkAvailable(PreviousVisitScreen.this))
				{
					new EmployerTask(PreviousVisitScreen.this).execute();
				}
				else
					Cons.showDialog(PreviousVisitScreen.this, null, "Please provide available network.", "OK");
				
			}
		});
		
	
	}
	
	public void getEditTextValues()
	{
		str_employerName = et_employerName.getText().toString();
		str_authorizedPerson = et_authorizedPerson.getText().toString();
		str_previousTreatment = et_prevoisTreatment.getText().toString();
	}
	
	public class EmployerTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;

		public EmployerTask(Context con)
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
		
			String url = Cons.url_add_employer_info+"employer_name="+str_employerName+"&approval_name="+str_authorizedPerson
					+"&previous_treatment="+str_previousTreatment;
			System.out.println(url);
			responseString = Cons.http_connection(url);
			Gson gson = new Gson();
			addEmployerBeans= gson.fromJson(responseString, BeansAddEmployer.class);
			if(addEmployerBeans !=null && addEmployerBeans.getCode()==200)
			{
				employer_id = addEmployerBeans.getEmployer_id();
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
			myMessage.obj = "employer_beans";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("employer_beans"))
			{
				if (!isFinishing()) 
				{

					if((addEmployerBeans == null)||Cons.isNetAvail==1)

					{

						Cons.isNetAvail = 0;
						Toast.makeText(PreviousVisitScreen.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
					}

					else if(addEmployerBeans.getCode()==200)
					{
						
						Intent travelexpense=new Intent(PreviousVisitScreen.this,InfoVerification.class);
						startActivity(travelexpense);
						

					}
					else 
					{
						
						Toast.makeText(PreviousVisitScreen.this, addEmployerBeans.getMessage(), Toast.LENGTH_LONG).show();
					}



				}
			}


		}
	};


	

}
