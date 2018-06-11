package in.globalsoft.urncr;

import in.globalsoft.adapter.RecentDoctorChatAdapter;
import in.globalsoft.adapter.SearchPatientAdapter;
import in.globalsoft.urncr.R;
import in.globalsoft.interfaces.RecentChatResult;
import in.globalsoft.interfaces.SearchPatientResponse;
import in.globalsoft.pojo.MessagePatientPojo;
import in.globalsoft.pojo.PatientMessagePojo;
import in.globalsoft.pojo.PatientPojo;
import in.globalsoft.pojo.RecentChatOfficePojo;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.tasks.SearchAllPatients;
import in.globalsoft.tasks.SingleOfficePatientTask;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

public class RecentOfficeChatActivity extends Activity implements TextWatcher,RecentChatResult,SearchPatientResponse
{

	EditText etSearch;
	Gson gson;
	List<MessagePatientPojo> doctorChatlist;
	ListView lvRecentChatList,lvSearchDoctorList;
	TextView tvNoChat;
	RecentDoctorChatAdapter dadapter;
	List<PatientPojo> patientlist;
	AppPreferences pref;
	SearchPatientAdapter patientAdapter=null;
	List<PatientPojo> searchPatientlist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.recent_chat_fragment);
		initAll();

		lvRecentChatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) 
			{
				MessagePatientPojo patientPojo=doctorChatlist.get(pos);
				Intent i=new Intent(RecentOfficeChatActivity.this,OfficeChatActivity.class);
				i.putExtra("PatientPojo", patientPojo);
				startActivity(i);



			}
		});

	}



	private void initAll() {
		// TODO Auto-generated method stub
		etSearch=(EditText) findViewById(R.id.etSearch);
		etSearch.addTextChangedListener(this);
		lvRecentChatList=(ListView) findViewById(R.id.lvRecentChatList);
		lvSearchDoctorList=(ListView) findViewById(R.id.lvSearchDoctorList);
		tvNoChat=(TextView) findViewById(R.id.tvNoChat);
		pref=new AppPreferences(RecentOfficeChatActivity.this);
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		AppPreferences pref=new AppPreferences(getApplicationContext());


		String url="http://carrxon.com/CarrxonWebServices/ws/office_recent_chat.php?user_id=2&status=doctor_office";
		new SingleOfficePatientTask(RecentOfficeChatActivity.this,url).execute();
		new SearchAllPatients(RecentOfficeChatActivity.this,true).execute();
	}


	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) 
	{
		int length = s.length();
		if(length==0)
		{
			lvRecentChatList.setVisibility(View.VISIBLE);
			lvSearchDoctorList.setVisibility(View.GONE);
			tvNoChat.setVisibility(View.GONE);
			
		}
		else
		{
			lvRecentChatList.setVisibility(View.GONE);
			lvSearchDoctorList.setVisibility(View.VISIBLE);

			searchPatientlist=new ArrayList<PatientPojo>(); 
			if(patientlist!=null)
			{
				for(int i=0;i<patientlist.size();i++)
				{
					PatientPojo patient=patientlist.get(i);
					String patientName=patient.getFirst_name()+" "+patient.getLast_name();
					if(patientName.length()>=length&&patientName.substring(0, length).equalsIgnoreCase(""+s))
						searchPatientlist.add(patient);

				}
				
				if(searchPatientlist.size()==0)
				{
					lvSearchDoctorList.setVisibility(View.GONE);
					tvNoChat.setVisibility(View.VISIBLE);
					
				}

				else
				{
					if(patientAdapter==null && searchPatientlist!=null)
					{
						patientAdapter=new SearchPatientAdapter(RecentOfficeChatActivity.this,searchPatientlist);
						lvSearchDoctorList.setAdapter(patientAdapter);
					}
				
					
				}
				
				
			}
			else
			{
				
			}
			
			
		}
	
		
		
		
		
		
		
	}



	@Override
	public void RecentChatResponse(String Result) {
		// TODO Auto-generated method stub

		if(Result!=null && !Result.equals("")&&etSearch.getText().toString().equals(""))
		{
			try {
				//	JSONObject object=new JSONObject(Result);
				gson=new Gson();

				//				if(pref.getDoctorLogin()==1)
				//				{
				RecentChatOfficePojo recentChat=gson.fromJson(Result, RecentChatOfficePojo.class);

				if(recentChat.getStatus().equals("1"))
				{
					doctorChatlist=recentChat.getMessage();


					lvRecentChatList.setVisibility(View.VISIBLE);
					tvNoChat.setVisibility(View.GONE);

					dadapter=new RecentDoctorChatAdapter(RecentOfficeChatActivity.this, doctorChatlist);
					lvRecentChatList.setAdapter(dadapter);


				}
				else
				{
					lvRecentChatList.setVisibility(View.GONE);
					tvNoChat.setVisibility(View.VISIBLE);
				}
				//	}
				/*		else
				{

					RecentChatPojo recentChat=gson.fromJson(Result, RecentChatPojo.class);

					if(recentChat.getStatus().equals("1"))
					{
						list=recentChat.getMessage();

						lvRecentChatList.setVisibility(View.VISIBLE);
						tvNoChat.setVisibility(View.GONE);

						adapter=new RecentChatAdapter(RecentChatActivity.this, list);
						lvRecentChatList.setAdapter(adapter);


					}
					else
					{
						lvRecentChatList.setVisibility(View.GONE);
						tvNoChat.setVisibility(View.VISIBLE);
					}



				}*/





			} catch (Exception e) {
				// TODO: handle exception
			}




		}
		else
		{
			lvRecentChatList.setVisibility(View.GONE);
			tvNoChat.setVisibility(View.VISIBLE);
		}

	}



	@Override
	public void PatientResponse(String response) {


		if(response!=null && !response.equals(""))
		{
			try {
				//	JSONObject object=new JSONObject(Result);
				gson=new Gson();
				PatientMessagePojo patient=gson.fromJson(response, PatientMessagePojo.class);

				if(patient.getStatus().equals("1"))
				{
					patientlist=patient.getMessage();
					/*doctorAdapter=new SearchDoctorAdapter(RecentChatActivity.this, doctorlist);
					lvSearchList.setAdapter(doctorAdapter);
					 */

				}




			} catch (Exception e) {
				// TODO: handle exception
			}


		}



	}


}
