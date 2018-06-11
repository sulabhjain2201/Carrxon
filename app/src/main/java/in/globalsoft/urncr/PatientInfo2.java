package in.globalsoft.urncr;

import in.globalsoft.beans.BeansInsuranceInfo;
import in.globalsoft.beans.BeansLogin;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class PatientInfo2 extends Activity
{

	Button btn_patientinfo_next;
	TextView tv_relationship,tv_previousPatient;
	RelativeLayout layout_patientRelation,layout_previousPatient;
	AlertDialog dialog_relation,dialog_previousPatient;
	AppPreferences appPreferences;
	String responseString;
	public static BeansInsuranceInfo insuranceInfoBeans;
	EditText et_symptoms;
	public static String str_symptoms="",str_relation="",str_previousPatient="";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_info2);
		appPreferences = new AppPreferences(this);
		et_symptoms = (EditText) findViewById(R.id.symptoms_edit);
		layout_patientRelation = (RelativeLayout) findViewById(R.id.patient_relation_layout);
		btn_patientinfo_next = (Button) findViewById(R.id.patinet_info_next_btn);
		tv_relationship = (TextView) findViewById(R.id.patient_relation_text);

		layout_previousPatient = (RelativeLayout) findViewById(R.id.is_previouspatient_laout);
		tv_previousPatient = (TextView) findViewById(R.id.is_previouspatient_text);

		TextView tvPatientName = (TextView) findViewById(R.id.patient_name_text);
		String strFirstName = getIntent().getExtras().getString("strFirstName");
		String strLastName = getIntent().getExtras().getString("strLastName");
		tvPatientName.setText(strFirstName+" "+ strLastName);
		btn_patientinfo_next.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				checkForBlank();
				

			}
		});
		layout_patientRelation.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				dialogRelation();
				dialog_relation.show();

			}
		});

		layout_previousPatient.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{

				dialogPreviosPatient();
				dialog_previousPatient.show();
			}
		});

	}

	public void dialogRelation() 
	{
		final ArrayList<String> listRelation = new ArrayList<String>();
		listRelation.add("Self");
		listRelation.add("Husband");
		listRelation.add("Wife");
		listRelation.add("Child");


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, listRelation);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Relationship with insured");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (item == 0) {


					dialog.cancel();
				} 
				else if (item == 1) {


					dialog.cancel();

				}
				else if (item == 2) {


					dialog.cancel();

				}
				else if (item == 3) {


					dialog.cancel();

				}

				tv_relationship.setText(listRelation.get(item));
				str_relation = String.valueOf(item);

			}
		});

		dialog_relation = builder.create();
	}


	public void dialogPreviosPatient() 
	{
		final ArrayList<String> listPreviousPatient = new ArrayList<String>();
		listPreviousPatient.add("No");
		listPreviousPatient.add("Yes");
		listPreviousPatient.add("Maybe");



		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, listPreviousPatient);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Has the patient been a patient of Carrxon before?");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (item == 0) {


					dialog.cancel();
				} 
				else if (item == 1) {


					dialog.cancel();

				}
				else if (item == 2) {


					dialog.cancel();

				}
			
				tv_previousPatient.setText(listPreviousPatient.get(item));
				str_previousPatient = String.valueOf(item);

			}
		});

		dialog_previousPatient = builder.create();
	}

	public class GetInsuranceInfoTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;

		public GetInsuranceInfoTask(Context con)
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
			String url = "";
			String msg = "";
			if(appPreferences.getLoginState()==1)
			{
				Gson gson = new Gson();
				BeansLogin loginBeans = gson.fromJson(appPreferences.getUserLoginInfo(), BeansLogin.class);
				url = Cons.url_get_insurance_info+"patient_id="+ InfoVerification.user_id;
				System.out.println("url::"+url);
				responseString = Cons.http_connection(url);	
				System.out.println(responseString);
				insuranceInfoBeans=gson.fromJson(responseString, BeansInsuranceInfo.class);

			}
			else
			{
				appPreferences.saveInsuranceState(0);
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
			myMessage.obj = "get_insuance_info";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("get_insuance_info"))
			{
				if (!isFinishing()) 
				{
					if(appPreferences.getLoginState() == 1)
					{

						if((insuranceInfoBeans == null)||Cons.isNetAvail==1)

						{

							Cons.isNetAvail = 0;
							Toast.makeText(PatientInfo2.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
						}

						else if(insuranceInfoBeans.getCode()==200)
						{

							Intent i=new Intent(PatientInfo2.this,InsuredPersonInfo.class);
							startActivity(i);
							appPreferences.saveInsuranceState(1);


						}
						else 
						{
							appPreferences.saveInsuranceState(0);
							Intent i=new Intent(PatientInfo2.this,InsuredPersonInfo.class);
							startActivity(i);
							//Toast.makeText(PatientInfo2.this, insuranceInfoBeans.getMessage(), Toast.LENGTH_LONG).show();
						}
					}
					else
					{

						Intent i=new Intent(PatientInfo2.this,InsuredPersonInfo.class);
						startActivity(i);

					}


				}
			}


		}
	};

	public void checkForBlank()
	{
		str_symptoms = et_symptoms.getText().toString();
		if(str_relation.equals("") || str_previousPatient.equals("") || str_symptoms.equals(""))
		{
			Toast.makeText(PatientInfo2.this, "All the fields are compulsury.", Toast.LENGTH_LONG).show();
		}
		
		else if(appPreferences.getInsuranceScreen() == 0)
		{
			if(InsuranceInfo.insurance_id != null)
				InsuranceInfo.insurance_id = "0";
			Intent i=new Intent(PatientInfo2.this,ContactInfo.class);
			startActivity(i);
		}
		else
		{
			if(Cons.isNetworkAvailable(PatientInfo2.this))
			{
				new GetInsuranceInfoTask(PatientInfo2.this).execute();
			}
		}
		
		

	}


}

