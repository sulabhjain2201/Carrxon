package in.globalsoft.urncr;

import in.globalsoft.beans.BeansAddInsuranceInfo;
import in.globalsoft.beans.BeansGetInsuranceInfo;
import in.globalsoft.beans.BeansLogin;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;

import java.util.ArrayList;
import java.util.Arrays;

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

public class InsuranceInfo extends Activity 
{
	Button btn_insurance_info;
	TextView text_insurance_companies,tv_insuranceCopy;
	AlertDialog dialog_insuranceCompanies,dialog_insuranceCopy;
	RelativeLayout layout_insuranceCompanies,layoutInsuraneCopy;
	EditText et_payer,et_claimAddress,et_servicePhoneNo,et_membershipId,et_groupName,et_groupNumber;
	String str_payer="",str_claimAddres="",str_servicePhoneNo="",str_membershipId="",str_groupName="";
	String str_groupNumber="",str_isCopy="";
	int company_id ;
	String isCopy="";
	String responseString;
	BeansGetInsuranceInfo insuranceInfoBeans;
	AppPreferences appPref;
	ArrayList<String> listInsuranceCompanies;
	public static String insurance_id="0";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insurance_info);
		appPref = new AppPreferences(this);
String insuranceList[]={"1199SEIU","20/20 Eyecare Plan","AARP","Abrazo Advantage",
		"Access Plus Program","Accountable Health Plan","ACE","Advantage Health",
		"Advantica","Adventist Health","Advocate Health Care","Aetna","Affinity Health Plan"
		,"AIG","Alameda Alliance for Health","Allegiance Life and Health","Alliance PPO",
		"Allied Insurance Group","AllState","Alta Bates Medical Group",
		"Altius (Coventry Health Care)","AlwaysCare","American Family Insurance",
		"American General","American Medical Security (AMS)","American National",
		"American Republic Insurance","American Specialty Health","America's 1st Choice",
		"AmeriChoice (UnitedHealthcare Community Plan)","AmeriGroup","AmeriHealth",
		"AmeriHealth Mercy Family of Companies","Amida Care","Anthem Blue Cross Blue Shield",
        "Anthem Blue Cross Blue Shield of Colorado","Anthem Blue Cross Blue Shield of Connecticut","Anthem Blue Cross Blue Shield of Indiana","Anthem Blue Cross Blue Shield of Kentucky","Anthem Blue Cross Blue Shield of Maine","Anthem Blue Cross Blue Shield of Missouri","Anthem Blue Cross Blue Shield of Nevada","Anthem Blue Cross Blue Shield of New Hampshire","Anthem Blue Cross Blue Shield of Ohio","Anthem Blue Cross Blue Shield of Virginia","Anthem Blue Cross Blue Shield of Wisconsin","Anthem Blue Cross of California","APWU","Arcadian Health Plan","Arizona Foundation for Medical Care","Arizona Physicians IPA (APIPA)","Arkansas Blue Cross Blue Shield","Ascension Health","Assurant Employee Benefits","Assurant Health","Asuris Northwest Health","Atlantis Health Plan (Easy Choice)","ATRIO Health Plans","Avalon Insurace Company","Averde","Avesis","AvMed","Bankers Life and Casualty Company","Banner Health","Banner MediSun, Inc.","Beaumont Employee Health Plan","Beech Street","Best Choice Plus","Best Life And Health","Better Health (Florida Medicaid)","Block Vision","Blue Bell Benefits Trust","Blue Care Network of Michigan","Blue Cross Blue Shield Federal Employee Program","Blue Cross Blue Shield of Alabama","Blue Cross Blue Shield of Arizona","Blue Cross Blue Shield of Florida (Florida Blue)","Blue Cross Blue Shield of Georgia","Blue Cross Blue Shield of Illinois","Blue Cross Blue Shield of Kansas","Blue Cross Blue Shield of Kansas City","Blue Cross Blue Shield of Louisiana","Blue Cross Blue Shield of Massachusetts","Blue Cross Blue Shield of Michigan","Blue Cross Blue Shield of Minnesota","Blue Cross Blue Shield of Mississippi","Blue Cross Blue Shield of Nebraska","Blue Cross Blue Shield of New Mexico","Blue Cross Blue Shield of North Carolina","Blue Cross Blue Shield of Oklahoma","Blue Cross Blue Shield of Rhode Island","Blue Cross Blue Shield of South Carolina","Blue Cross Blue Shield of Tennessee","Blue Cross Blue Shield of Texas","Blue Cross Blue Shield of Vermont","Blue Cross Blue Shield of Western New York","Blue Cross Blue Shield of Wyoming","Blue Cross of Idaho","Blue Cross of Northeastern Pennsylvania","Blue Shield of California","Blue Shield of Northeastern New York","BMC HealthNet Plan","Bravo Health","Bridgeway Health Solutions","Broward County Risk Management","Brown & Toland","California Division of Workers' Compensation","CalOptima","CalPERS","Capital Blue Cross","Capital Health Plan","CARE (Consolidated Association of Railroad Employees)","Care Access","Care Improvement Plus","Care IQ","Care N' Care","Care1st","Carecore National","CareFirst Blue Cross Blue Shield","CareMore","CareOregon","CarePlus Health Plans (Florida Medicare)","Caterpillar","CBA Blue","CDPHP","Celtic Insurance Company","CeltiCare Health Plan","CenterLight Healthcare","CHAMPVA","Chartered Health Plan","Children's Medical Services (CMS)","Chinese Community Health Plan","Choice Care Network","Cigna","Citizens Choice Healthplan","Citrus Health Care","Clements International","Cofinity","Columbia United Providers","Commonwealth Care","Community First Health Plans","Community Health Alliance","Community Health Choice","Community Health Group","Community Health Partners","Community Health Plan of Washington","Community Partners Health Plan","Compass Benefits Group","CompBenefits","Comprehensive Health Insurance Plan (CHIP) of Illinois","Comprehensive Medical and Dental Program","ComPsych","Concentra","Concert Health Plan","ConnectiCare","ConnectiCare of Massachusetts, Inc.","Conoco","Consolidated Health Plans","Consumer Health Network","Corvel","Country Financial","Coventry Health Care","Cox HealthPlans","Culinary Health Fund","Davis Vision","DC Workers' Compensation Commission","Definity Health","Deseret Mutual","Desert Canyon Community Care","Destiny Health Insurance Co","Devon Health Services","Dimensions Incorporated","DMC Care","DRN (Diagnostic Radiology Network)","Easy Choice Health Plan (California)","Easy Choice Health Plan of New York","Elderplan","Emblem","Empire Blue Cross Blue Shield","Empire Plan","Employers Workers' Compensation","Encore Health Network","Erie Insurance Group","ESSENCE","Everence Association, Inc,","Evolutions Healthcare Systems","Excellus Blue Cross Blue Shield","EyeMed","Eyetopia Vision Care","Fallon Community Health Plan (FCHP)","Family Health Network","FamilyCare Health Plans","Farmers","Fidelis Care (NY)","First Choice Health","First Health (Coventry Health Care)","First Look Vision Network","FirstCare Health Plans","FirstCarolinaCare","Florida Blue: Blue Cross Blue Shield of Florida","Florida Health Care Plans","Florida Health Partners","Florida Healthcare Plus","Florida Hospital Healthcare System (FHHS)","Florida KidCare","Foreign Service Benefit Plan","Fortified Provider Network","Freedom Health","Freelancers Insurance Company (BlueCard PPO Network)","Galaxy Health","Gateway Health Plan","GEHA Health Plans","Geico","Geisinger Health Plan","General Electric","GeoBlue","GHI","Gilsbar","Global Health","Golden Rule","GroupHealth","Guardian","GWH-Cigna (formerly Great West Healthcare)","Hanover Insurance","HAP (Alliance)","Harmony Health Plan","Harvard Pilgrim Health Care","Health Care District of Palm Beach County","Health Choice","Health First Health Plans (Florida)","Health Net","Health New England","Health Partners","Health Plan of Nevada","Health Right","Health Sun","HealthChoice","HealthFirst (NY-NJ)","Healthlink","Healthmarkets, Inc.","HealthPartners","HealthPlus","HealthSmart","Healthspring","Healthy Families","HearPO","Heritage Summit HealthCare","Heritage Vision Plans","HFN","HFS Medical Benefits","HighMark Blue Cross Blue Shield","Highmark Blue Cross Blue Shield of Delaware","HighMark Blue Shield","Hill Physicians","HIP","HMA (Health Management Associates)","Horizon Blue Cross Blue Shield of New Jersey","Horizon NJ Health","Hudson Health Plan","Humana","Illinicare","Illinois Workers' Compensation Commission","IMO (Independent Medical Systems)","Independence Blue Cross","Independent Health","Indiana Health Network (IHN)","Ingalls Provider Group (IPG)","Inter Valley Health Plan","International Medical Group (IMG)","JMH Health Plan","Johns Hopkins Employer Health Programs","Kaiser Permanente","Keystone Mercy Health Plan","KPS Health Plans","Liberty Health Advantage","Liberty Mutual","LifeWise","Lovelace Health Plan","Loyal American Life Insurance Company","Lutheran Preferred","M.D. IPA","Magellan Health Services","MagnaCare","Mail Handlers Benefit Plan","Mamsi","Managed Health Network (MHN)","Manatee Your Choice Health Plan","March Vision Care","Maricopa Health Plan","Maryland Health Insurance Plan","Maryland Medical Assistance (Medicaid)","Maryland Physicians Care","Maryland Workers' Compensation","MassHealth","MCM Maxcare","Medica (Minnesota)","Medica HealthCare Plans (Florida)","Medicaid","Medicaid Medi-Cal Dual Eligible","Medicaid Medicare Dual Eligible","Medi-Cal","Medical Eye Services (MES Vision)","Medical Mutual","Medicare","MediPass","MedStar Family Choice","Mega Life and Health Insurance Company","Memorial Managed Care","Merchants Insurance Group","Mercy Care","Meridian Health Plan","MetLife","Metro Plus","Metropolitan Health Plan","MHealth","Midwest Health Plan","Mills-Peninsula Medical Group","Missouri Care","Missouri Employers Mutual Insurance","MO HealthNet","Molina Healthcare","Motion Picture Industry Health Plan","Multiplan PHCS","Mutual of Omaha","MVP","National Capital","National Choice Care","National Vision Administrators","Nationwide","NCAS","Neighborhood Health Plan (Massachusetts)","Neighborhood Health Providers (NY)","Network Health","Nevada Preferred","New Era","NewYork-Presbyterian Community Health Plan","Nippon Life Benefits","Northeast Community Care","NovaNet","NY State No-Fault","NY State Workers' Compensation Board","ODS","OneNet PPO, LLC","OptiCare Managed Vision","Opticare of Utah","OptimaHealth","Optimum Choice","Optimum HealthCare","Optum Health","Oxford (UnitedHealthcare)","PacificSource Health Plans","Parkland Community Health Plan","PBA (Patrolmen's Benefit Association)","Peach State Health Plan","PeachCare for Kids","Penn National Insurance","Perfect Health","PersonalCare Insurance of Illinois Inc","Phoenix Health Plan","Physicians Health Choice","Physicians Medical Group of San Jose","Physicians Mutual","Physicians United Plan (PUP)","Pima Health System (PHS)","POMCO","Pre-Existing Condition Insurance Plan (PCIP)","Preferred Care Partners","Preferred Health Systems","Preferred Medical Plan","Preferred Network Access (PNA)","PreferredOne","Premera Blue Cross","Presbyterian Health Plan/Presbyterian Insurance Company","Prestige Health Choice","Prevea Health Network (PHN)","Prime Health Services, Inc","Principal Financial Group","Priority Health","Priority Partners","Progressive","Providence Health Plans","Public Aid (Illinois Medicaid)","Pyramid","QualCare","Quality Health Plans of New York","Ravenswood Physicians Associates (RPA)","Regence Blue Cross Blue Shield of Oregon","Regence Blue Cross Blue Shield of Utah","Regence Blue Shield of Idaho","Regence Blue Shield of Washington","Renaissance Health Systems","Rockport Healthcare","Rocky Mountain Health Plans","SafeCo","SafeGuard","Sagamore Health Network","Saint Mary's Health Plans","San Francisco Health Plan","Santa Clara County IPA (SCCIPA)","SCAN Health Plan","Scott & White Health Plan","Select Care","Select Health Network","SelectHealth","Sendero Health Plans","Senior Whole Health","Seton Health Plan","Seven Corners","Sierra Health and Life","Significa","Simply Healthcare","Sound Health & Wellness Trust","South Florida Community Care Network","Southeast Community Care","Spectera","Standard Life and Accident Insurance Company","Starbridge","Starmark","State Accident Insurance Fund Corporation (SAIF)","State Compensation Insurance Fund","State Farm�","Sterling Insurance","Stratose","SummaCare","Sunshine State Health Plan","Superior HealthPlan","Superior Vision","SutterSelect","TexanPlus","Texas Children's Health Plan","Texas Community Care","Texas Healthcare Foundation","Texas TrueChoice","The Hartford","Three Rivers Providers Network (TRPN)","Torrance Hospital IPA","Total Health Care","Total Health Choice","Touchstone","Travelers","Tricare","Triple-S Salud: Blue Cross Blue Shield of Puerto Rico","TrustMark","Tufts Associated Health Plans, Inc.","UCare","UniCare","Uniform Medical Plan","Union","Union Health Services, Inc","Union Pacific Railroad Employees Health Systems (UPREHS)","Unison Health Plan","United American Insurance Company","United Behavioral Health","UnitedHealthcare","UnitedHealthcare Community Plan","UnitedHealthcare Oxford","UnitedHealthOne","Unitrin","Univera","Universal American","Universal Healthcare","University Family Care","University of Chicago Health Plan","University Physicians Healthcare Group","UPMC Health Plan, Inc.","US Family Health Plan","US Health Group","USA Managed Care","USAA","ValueOptions","Viant","Virginia Health Network","Virginia Premier Health Plan","Virginia Workers' Compensation Commission","Vision Benefits of America","Vision Care Direct","Vision Plan of America","Vista","VNS Choice Select","VSP","Vytra","Wellcare","Wellmark Blue Cross Blue Shield of Iowa","Wellmark Blue Cross Blue Shield of South Dakota","Wells Fargo (Acordia)","Western Health Advantage","Workers Compensation","Zenith","Zurich"};
        listInsuranceCompanies = new ArrayList<String>(Arrays.asList(insuranceList));
	
		defineLayouts();
		if(appPref.getInsuranceState() ==1)
		{
			prefilledTexts();
		}


		btn_insurance_info.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				getEditTextValues();
				if(Cons.isNetworkAvailable(InsuranceInfo.this))
				{
					new InsuranceInfoTask(InsuranceInfo.this).execute();
				}

			}
		});

		layout_insuranceCompanies.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				dialogInsuranceCompanies();
				dialog_insuranceCompanies.show();

			}
		});

		layoutInsuraneCopy.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				dialogInsuranceCopy();
				dialog_insuranceCopy.show();
			}
		});

	}

	public void dialogInsuranceCompanies() 
	{


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, listInsuranceCompanies);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Choose Insurance Company");
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
				else if (item == 4) {


					dialog.cancel();

				}
				text_insurance_companies.setText(listInsuranceCompanies.get(item));
				company_id = item+1;

			}
		});

		dialog_insuranceCompanies = builder.create();
	}

	public void dialogInsuranceCopy() 
	{
		final ArrayList<String> listInsuranceCopy = new ArrayList<String>();
		listInsuranceCopy.add("Yes");
		listInsuranceCopy.add("No");


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, listInsuranceCopy);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Do you have a copy of your Insurance?");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (item == 0) {

					isCopy = "1";
					dialog.cancel();
				} 
				else if (item == 1) {

					isCopy = "0";
					dialog.cancel();

				}
				else if (item == 2) {


					dialog.cancel();

				}
				tv_insuranceCopy.setText(listInsuranceCopy.get(item));
				company_id = item+1;

			}
		});

		dialog_insuranceCopy = builder.create();
	}

	public void defineLayouts()
	{
		btn_insurance_info = (Button)findViewById(R.id.insurance_info_next_step_btn);
		text_insurance_companies = (TextView) findViewById(R.id.company_name_text);
		layout_insuranceCompanies = (RelativeLayout) findViewById(R.id.company_name_layout);

		tv_insuranceCopy = (TextView) findViewById(R.id.copy_of_insurance_text);
		layoutInsuraneCopy = (RelativeLayout) findViewById(R.id.copy_of_insurance_layout);
		et_payer = (EditText) findViewById(R.id.payor_edit);
		et_claimAddress = (EditText) findViewById(R.id.claim_address);
		et_servicePhoneNo = (EditText) findViewById(R.id.service_phoneno);
		et_membershipId = (EditText) findViewById(R.id.membership_id);
		et_groupName = (EditText) findViewById(R.id.group_name);
		et_groupNumber = (EditText) findViewById(R.id.group_no);

	}

	public void getEditTextValues()
	{
		str_payer = et_payer.getText().toString();
		str_claimAddres = et_claimAddress.getText().toString();
		str_membershipId = et_membershipId.getText().toString();
		str_groupName= et_groupName.getText().toString();
		str_groupNumber = et_groupNumber.getText().toString();
		str_servicePhoneNo = et_servicePhoneNo.getText().toString();
	}


	public class InsuranceInfoTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;

		public InsuranceInfoTask(Context con)
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
			Gson gson = new Gson();
			BeansLogin loginBeans = gson.fromJson(appPref.getUserLoginInfo(), BeansLogin.class);

			if(appPref.getInsuranceState() == 0)
			{
				url = Cons.url_add_insurance_info+"first_name="+InsuredPersonInfo.str_firstname+"&last_name="+InsuredPersonInfo.str_lastname
						+"&insured_MI="+InsuredPersonInfo.str_insuredMI + "&insured_DOB="+InsuredPersonInfo.str_birthday+"&payer_TPA="+str_payer
						+ "&insurance_company_id="+company_id+"&claim_address="+str_claimAddres+"&service_phone="+str_servicePhoneNo
						+"&membership_id="+str_membershipId+"&group_name="+str_groupName+"&group_number="+str_groupNumber
						+"&is_copy_of_insurance="+isCopy+"&insurance_id=0&patient_id="+InfoVerification.user_id;
				
			}
			else
			{
				
				BeansAddInsuranceInfo insuranceInfoAddBeans = PatientInfo2.insuranceInfoBeans.getInsurance_info();
				url = Cons.url_add_insurance_info+"first_name="+InsuredPersonInfo.str_firstname+"&last_name="+InsuredPersonInfo.str_lastname
						+"&insured_MI="+InsuredPersonInfo.str_insuredMI + "&insured_DOB="+InsuredPersonInfo.str_birthday+"&payer_TPA="+str_payer
						+ "&insurance_company_id="+company_id+"&claim_address="+str_claimAddres+"&service_phone="+str_servicePhoneNo
						+"&membership_id="+str_membershipId+"&group_name="+str_groupName+"&group_number="+str_groupNumber
						+"&is_copy_of_insurance="+isCopy+"&insurance_id="+insuranceInfoAddBeans.getInsurance_id_pk()
						+"&patient_id="+InfoVerification.user_id;
				
			}

			System.out.println("url:"+url);

			responseString = Cons.http_connection(url);
			System.out.println(responseString);
			if(responseString == null)
			{

			}
			else
			{
				
				insuranceInfoBeans = gson.fromJson(responseString, BeansGetInsuranceInfo.class);
				if(insuranceInfoBeans!=null && insuranceInfoBeans.getCode()==200)
				{
				insurance_id = insuranceInfoBeans.getInsurance_id();
				System.out.println(insurance_id);
				}
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
			myMessage.obj = "insurance_info_task";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("insurance_info_task"))
			{
				if (!isFinishing()) 
				{

					if((insuranceInfoBeans == null)||Cons.isNetAvail==1)

					{

						Cons.isNetAvail = 0;
						Toast.makeText(InsuranceInfo.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
					}

					else if(insuranceInfoBeans.getCode()==200)
					{

						Intent i=new Intent(InsuranceInfo.this,ContactInfo.class);
						startActivity(i);
						finish();

					}
					else 
					{

						Toast.makeText(InsuranceInfo.this, insuranceInfoBeans.getMessage(), Toast.LENGTH_LONG).show();
					}



				}
			}


		}
	};

	public void prefilledTexts()
	{
		BeansAddInsuranceInfo insuranceInfoBeans = PatientInfo2.insuranceInfoBeans.getInsurance_info();
		et_payer.setText(insuranceInfoBeans.getPayer_TPA());
		et_claimAddress.setText(insuranceInfoBeans.getClaim_address());
		et_servicePhoneNo.setText(insuranceInfoBeans.getService_phone());
		et_membershipId.setText(insuranceInfoBeans.getMembership_id());
		et_groupName.setText(insuranceInfoBeans.getGroup_name());
		et_groupNumber.setText(insuranceInfoBeans.getGroup_number());
		isCopy = insuranceInfoBeans.getIs_copy_of_insurance();
		if(insuranceInfoBeans.getIs_copy_of_insurance().equals("0"))
			tv_insuranceCopy.setText("No");
		else
			tv_insuranceCopy.setText("Yes");	
		company_id = Integer.parseInt(insuranceInfoBeans.getInsurance_company_id_fk());
		text_insurance_companies.setText(listInsuranceCompanies.get(Integer.parseInt(insuranceInfoBeans.getInsurance_company_id_fk())-1));

	}



}
