package in.globalsoft.urncr;

import in.globalsoft.beans.BeanDocInsuranceInfo;
import in.globalsoft.beans.BeansLanguagesKnown;
import in.globalsoft.beans.BeansResponse;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class AddProfessionalInfo extends Activity
{
	String listLanguages[] = {"English","Spanish","German","Urdu","Chinese","Hindi","Arabic","Portuguese","Bengali","Russian","Japanese","Punjabi","Korean","Italian"};
	String listInsurances[] = {"Cigna","Local 1199","Oxford","GHI","Bluecross","Medicare","Magnacare","UnitedHealthcare","Empire","Aetna HIP","Affinity","workers Comp","Medicaid","Fidelis","Multiplan","Humana"};
	boolean[] itemsChecked = new boolean[listLanguages.length];
	boolean[] itemsCheckedInsurances = new boolean[listInsurances.length];
	int experience = -1;
	int insuranceType;
	List<String> langList;
	List<String> insuranceList;
	BeansResponse responseBeans;
	String strFees;

	@Override 
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_professional_info);
		RelativeLayout rlExperience = (RelativeLayout) findViewById(R.id.experience_layout);
		rlExperience.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				dialogExperience();

			}
		});
		
		RelativeLayout rlLanguages = (RelativeLayout) findViewById(R.id.language_layout);
		rlLanguages.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				dialogLanguages();
				
			}
		});
		
		RelativeLayout rlInsurances = (RelativeLayout) findViewById(R.id.insurances_layout);
		rlInsurances.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				dialogInsuranceType();
				
			}
		});
		
		RelativeLayout rlFees = (RelativeLayout) findViewById(R.id.fees_layout);
		rlFees.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				dialogFees();
				
			}
		});

		
		Button btnContinue = (Button) findViewById(R.id.continue_btn);
		btnContinue.setOnClickListener(new OnClickListener()
		{
			
			
			@Override
			public void onClick(View v) 
			{
				String errorMsg = checkValidation();
				if(!errorMsg.equals(""))
				{
					Toast.makeText(AddProfessionalInfo.this, errorMsg, Toast.LENGTH_LONG).show();
				}
				else
				{
					if(Cons.isNetworkAvailable(AddProfessionalInfo.this))	
					{
						new UpdateProfesionalInfoTask(AddProfessionalInfo.this).execute();
					}
					else
						Cons.showDialog(AddProfessionalInfo.this, "Carrxon", "Internet is not available", "OK");
				}
				
			}
		});
		
	}

	

	public void dialogExperience() 
	{
		List<Integer> experienveList = new ArrayList<Integer>();
		for(int i=0;i<=40;i++)
		{
			experienveList.add(i);
		}

		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
				android.R.layout.select_dialog_item, experienveList);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Choose Experience");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				
				TextView tvExp = (TextView) findViewById(R.id.tvExperience);
				tvExp.setText(String.valueOf(item));
				experience = item;
			}
		});

		AlertDialog dialogExp = builder.create();
		dialogExp.show();
	}
	
	
	 public void dialogLanguages()
	    {
	    	
	    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
	    	builder.setTitle("Pick Known Languages");
	    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) 
	            {
	            	String selectedTech="Selected Languages - ";
	            	langList = new ArrayList<String>();
	                for (int i = 0; i < listLanguages.length; i++) {
	                if (itemsChecked[i]) {
	                    
	                	selectedTech=selectedTech+listLanguages[i]+" ";
	                	langList.add(listLanguages[i]);
	                	
	                    itemsChecked[i]=false;
	                }
	            }
	             TextView tvLangValues=(TextView)findViewById(R.id.tvLanguageVlaue);
	             tvLangValues.setVisibility(View.VISIBLE);
	             tvLangValues.setText(selectedTech);
	            
	            }
	        });
	    	
	    	builder.setMultiChoiceItems(listLanguages, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						itemsChecked[which]=isChecked;	
				}
			});
	    	builder.show();
	    }
	 
	 public void dialogInsuraces()
	    {
	    	
	    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
	    	builder.setTitle("Pick Insurances");
	    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) 
	            {
	            	String selectedTech="Selected Insurances - ";
	            	insuranceList = new ArrayList<String>();
	                for (int i = 0; i < listInsurances.length; i++) {
	                if (itemsCheckedInsurances[i]) {
	                    
	                	selectedTech=selectedTech+listInsurances[i]+",";
	                	insuranceList.add(listInsurances[i]);
	                	
	                	itemsCheckedInsurances[i]=false;
	                }
	            }
	             TextView tvInsurancesValue=(TextView)findViewById(R.id.tvInsurancesvalue);
	             tvInsurancesValue.setVisibility(View.VISIBLE);
	             tvInsurancesValue.setText(selectedTech);
	            
	            }
	        });
	    	
	    	builder.setMultiChoiceItems(listInsurances, itemsCheckedInsurances, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					itemsCheckedInsurances[which]=isChecked;	
				}
			});
	    	builder.show();
	    }
	 
		public void dialogInsurance() 
		{
			final ArrayList<String> listInsurance = new ArrayList<String>();
			listInsurance.add("Choose from given Insurances ");
			listInsurance.add("Add Different Insurance Carrier");
			



			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.select_dialog_item, listInsurance);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setTitle("Select Insurance Type");
			builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {

					if (item == 0) {

						dialogInsuraces();
						dialog.cancel();
					} 
					else if (item == 1) {


						dialog.cancel();

					}
					
				
					

				}
			});

			AlertDialog dialogInsurances = builder.create();
			dialogInsurances.show();
		}
	 
	 private String checkValidation()
	 {
		 String errorMsg = "";
		 EditText etEducation = (EditText) findViewById(R.id.etEducation);
		 EditText etServices = (EditText) findViewById(R.id.etServices);
		 if(experience == -1)
		 {
			  errorMsg = "Enter your Experience";
		 }
		 else if(langList == null || langList.size() == 0)
		 {
			 errorMsg = "Select atleast one Known language";
		 }
		 else if(insuranceList == null || insuranceList.size() == 0)
		 {
			 errorMsg = "Select atleast one Insurance";
		 }
		 else if(etEducation.getText().toString().equals(""))
		 {
			 errorMsg = "Enter your education Info";
		 }
		 else if(etEducation.getText().toString().equals(""))
		 {
			 errorMsg = "Enter your education Info";
		 }
		 else if(etServices.getText().toString().equals(""))
		 {
			 errorMsg = "Enter your Services";
		 }
		 return errorMsg;
	 }
	 
	 public class UpdateProfesionalInfoTask extends AsyncTask<Void, Void, Void>
		{
			ProgressDialog pd;
			Context con;
			private String educationInfo;
			private String servicesInfo;

			public UpdateProfesionalInfoTask(Context con)
			{
				this.con = con;
			}

			@Override
			protected void onPreExecute() 
			{
				EditText etEducation = (EditText) findViewById(R.id.etEducation);
				EditText etServices = (EditText) findViewById(R.id.etServices);
				educationInfo = etEducation.getText().toString();
				servicesInfo = etServices.getText().toString();
				pd = ProgressDialog.show(con, null, "Loading...");
				super.onPreExecute();
			}
			@Override
			protected Void doInBackground(Void... params)
			{
				try {
					AppPreferences appPref = new AppPreferences(AddProfessionalInfo.this);
					BeansLanguagesKnown languagesKnown = new BeansLanguagesKnown();
					languagesKnown.setLanguage(langList);
					Gson gson = new Gson();
					String jsonlang = gson.toJson(languagesKnown);
					BeanDocInsuranceInfo docInsuranceInfo = new BeanDocInsuranceInfo();
					docInsuranceInfo.setInsurance_info(insuranceList);

					String jsonInsurance = gson.toJson(docInsuranceInfo);

					String url = "";
					url = Cons.url_add_docor_education + "doctor_id=" + appPref.getDoctorId() + "&experience=" + experience + "&fees=" + strFees
							+ "&language=" + jsonlang + "&insurance_info=" + jsonInsurance + "&education=" + URLEncoder.encode(educationInfo, "utf-8") + "&services=" + URLEncoder.encode(servicesInfo, "utf-8");

					;
					System.out.println("url::" + url);
					String responseString = Cons.http_connection(url);
					if (responseString != null)
						System.out.println(responseString);

					responseBeans = gson.fromJson(responseString, BeansResponse.class);


				}
				catch (Exception e){
					e.printStackTrace();
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
				myMessage.obj = "add_professional";
				myHandler.sendMessage(myMessage);
				super.onPostExecute(result);

			}

		}
		private Handler myHandler = new Handler() 
		{

			public void handleMessage(Message msg)
			{


				if (msg.obj.toString().equalsIgnoreCase("add_professional"))
				{
					if (!isFinishing()) 
					{


						if((responseBeans == null))

						{


							Toast.makeText(AddProfessionalInfo.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
						}

						else if(responseBeans.getCode()==200)
						{

							Toast.makeText(AddProfessionalInfo.this, "Status successfully updated.", Toast.LENGTH_LONG).show();
							Intent i = new Intent(AddProfessionalInfo.this,AddClinicShedule.class);
							i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(i);



						}
						else 
						{
							Toast.makeText(AddProfessionalInfo.this, responseBeans.getMessage(), Toast.LENGTH_LONG).show();
						}
					}




				}


			}
		};
		
		public void dialogInsuranceType() 
		{
			final ArrayList<String> listInsuranceType = new ArrayList<String>();
			listInsuranceType.add("Choose from prelisted Insurances");
			listInsuranceType.add("Add Different Insurance Carrier");
			



			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.select_dialog_item, listInsuranceType);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setTitle("Select Insurances");
			builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {

					if (item == 0) {

dialogInsuraces();
						dialog.cancel();
					} 
					else if (item == 1) {

						inputDialog();
						dialog.cancel();

					}
					else if (item == 2) {

						
						dialog.cancel();

					}
				
					

				}
			});

			AlertDialog dialog_previousPatient = builder.create();
			dialog_previousPatient.show();
		}
		
		public void dialogFees() 
		{
			final ArrayList<String> listFeesType = new ArrayList<String>();
			listFeesType.add("40$");
			listFeesType.add("50$");
			listFeesType.add("60$");
			listFeesType.add("70$");
			listFeesType.add("75$");
			listFeesType.add("100$");
			listFeesType.add("125$");
			listFeesType.add("150$");
			listFeesType.add("200$");



			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.select_dialog_item, listFeesType);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setTitle("Select Doctor Fees");
			builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {

					TextView tvFees = (TextView) findViewById(R.id.tvFees);
					tvFees.setText(listFeesType.get(item));
					strFees = listFeesType.get(item);
				
					

				}
			});
			


			AlertDialog dialog_previousPatient = builder.create();
			dialog_previousPatient.show();
		}

	private void inputDialog()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(AddProfessionalInfo.this);
        alert.setTitle("Select Insurance Carrier "); //Set Alert dialog title here
        alert.setMessage("Select Insurance Carriers seprated by Commas"); //Message here

        // Set an EditText view to get user input 
        final EditText input = new EditText(AddProfessionalInfo.this);
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
         //You will get as string input data in this variable.
         // here we convert the input to a string and show in a toast.
         String srt = input.getEditableText().toString();
         TextView tvInsurancesValue=(TextView)findViewById(R.id.tvInsurancesvalue);
         tvInsurancesValue.setVisibility(View.VISIBLE);
         tvInsurancesValue.setText(srt);
         insuranceList = new ArrayList<String>();
         insuranceList = Arrays.asList(srt.split("\\s*,\\s*"));
         Toast.makeText(AddProfessionalInfo.this,srt,Toast.LENGTH_LONG).show();                
        } // End of onClick(DialogInterface dialog, int whichButton)
    }); //End of ALERT.setPositiveButton
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            // Canceled.
              dialog.cancel();
          }
    }); //End of alert.setNegativeButton
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
	}

}
