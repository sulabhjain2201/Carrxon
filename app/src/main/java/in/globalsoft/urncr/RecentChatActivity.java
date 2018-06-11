package in.globalsoft.urncr;

import in.globalsoft.adapter.RecentChatAdapter;
import in.globalsoft.adapter.RecentDoctorChatAdapter;
import in.globalsoft.adapter.SearchDoctorAdapter;
import in.globalsoft.adapter.SearchPatientAdapter;
import in.globalsoft.urncr.R;
import in.globalsoft.interfaces.RecentChatResult;
import in.globalsoft.interfaces.SearchDoctorResponse;
import in.globalsoft.interfaces.SearchPatientResponse;
import in.globalsoft.pojo.DoctorMessagePojo;
import in.globalsoft.pojo.DoctorPojo;
import in.globalsoft.pojo.MessagePatientPojo;
import in.globalsoft.pojo.MessagePojo;
import in.globalsoft.pojo.PatientMessagePojo;
import in.globalsoft.pojo.PatientPojo;
import in.globalsoft.pojo.RecentChatDoctorPojo;
import in.globalsoft.pojo.RecentChatPojo;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.tasks.SearchAllDoctorsByPatientTask;
import in.globalsoft.tasks.SearchAllPatients;
import in.globalsoft.tasks.GetRecentChatsTask;
import in.globalsoft.tasks.GetRecentDoctorChats;

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

public class RecentChatActivity extends Activity implements RecentChatResult,SearchDoctorResponse,TextWatcher,SearchPatientResponse
{
	EditText etSearch;
	ListView lvRecentChatList,lvSearchDoctorList;
	static String Response;
	GetRecentChatsTask recent;
	RecentChatPojo rChat;
	RecentChatAdapter adapter;
	RecentDoctorChatAdapter dadapter;
	SearchDoctorAdapter doctorAdapter=null;
	SearchPatientAdapter patientAdapter=null;
	List<MessagePojo> list;
	List<MessagePatientPojo> doctorChatlist;
	List<DoctorPojo> doctorlist;
	List<PatientPojo> patientlist;
	List<DoctorPojo> searchDoctorlist;
	List<PatientPojo> searchPatientlist;
	String test="";
	Gson gson; 
	TextView tvNoChat;
	AppPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recent_chat_fragment);

		initAll();


		lvRecentChatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {




				//////////////////     Code for opening the chat window   ////////////////

				if(pref.getLogintype()==1)
				{

					Intent i=new Intent(RecentChatActivity.this,ChatActivity.class);
					PatientPojo msgPojo=doctorChatlist.get(position).getPatient();
					//				Bundle b=new Bundle();
					//				b.putSerializable("MessagePojo", msg);
					//				i.putExtra("frnd_id", msg.getFriend_id());
					//				i.putExtra("doctr_name", msg.getDoctor().getDoctor_name());

					Bundle extras=new Bundle();   
					extras.putSerializable("PatientMessagePojo", msgPojo);
					i.putExtras(extras);
					startActivity(i);



				}
				else
				{
					Intent i=new Intent(RecentChatActivity.this,ChatActivity.class);
					DoctorPojo msgPojo=list.get(position).getDoctor();
					//				Bundle b=new Bundle();
					//				b.putSerializable("MessagePojo", msg);
					//				i.putExtra("frnd_id", msg.getFriend_id());
					//				i.putExtra("doctr_name", msg.getDoctor().getDoctor_name());

					Bundle extras=new Bundle();   
					extras.putSerializable("MessagePojo", msgPojo);
					i.putExtras(extras);
					startActivity(i);
				}
				//startActivity(i);

			}
		});



		lvSearchDoctorList.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {




				//////////////////     Code for opening the chat window   ////////////////

				if(pref.getLogintype()==1)
				{

					Intent i=new Intent(RecentChatActivity.this,ChatActivity.class);
					PatientPojo msgPojo=searchPatientlist.get(position);
					//				Bundle b=new Bundle();
					//				b.putSerializable("MessagePojo", msg);
					//				i.putExtra("frnd_id", msg.getFriend_id());
					//				i.putExtra("doctr_name", msg.getDoctor().getDoctor_name());

					Bundle extras=new Bundle();   
					extras.putSerializable("PatientMessagePojo", msgPojo);
					i.putExtras(extras);
					startActivity(i);



				}
				else
				{
					Intent i=new Intent(RecentChatActivity.this,ChatActivity.class);
					DoctorPojo msgPojo=searchDoctorlist.get(position);
					//				Bundle b=new Bundle();
					//				b.putSerializable("MessagePojo", msg);
					//				i.putExtra("frnd_id", msg.getFriend_id());
					//				i.putExtra("doctr_name", msg.getDoctor().getDoctor_name());

					Bundle extras=new Bundle();   
					extras.putSerializable("MessagePojo", msgPojo);
					i.putExtras(extras);
					startActivity(i);
				}
				//startActivity(i);

			}
		});






	}
	private void initAll() 
	{

		etSearch=(EditText) findViewById(R.id.etSearch);
		lvRecentChatList=(ListView) findViewById(R.id.lvRecentChatList);
		lvSearchDoctorList=(ListView) findViewById(R.id.lvSearchDoctorList);
		etSearch.addTextChangedListener(this);
		lvRecentChatList.setVisibility(View.VISIBLE);
		lvSearchDoctorList.setVisibility(View.GONE);
		tvNoChat=(TextView) findViewById(R.id.tvNoChat);

	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		pref=new AppPreferences(RecentChatActivity.this);

		if(pref.getLogintype()==1)
		{
            //get all patients by searching....
			new SearchAllPatients(RecentChatActivity.this,false).execute();

            //get all recent patients corrosponding ti a doctor
			new GetRecentDoctorChats(RecentChatActivity.this).execute();
			if(!pref.getSearchedPatient().equals(""))
			{
				PatientResponse(pref.getSearchedPatient());
			}
			if(!pref.getRecentDocotsChat().equals(""))
			{
				RecentChatResponse(pref.getRecentDocotsChat());
			}
		}
		
		
		else
		{
			new SearchAllDoctorsByPatientTask(RecentChatActivity.this,false).execute();

            //get all doctors to which patient recently chat
			new GetRecentChatsTask(RecentChatActivity.this).execute();
			if(!pref.getSearchedDoctor().equals(""))
			{
				DoctorResponse(pref.getSearchedDoctor());
			}
			if(!pref.getRecentPatientChat().equals(""))
			{
				RecentChatResponse(pref.getRecentPatientChat());
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

				if(pref.getLogintype()==1)
				{
					RecentChatDoctorPojo recentChat=gson.fromJson(Result, RecentChatDoctorPojo.class);

					if(recentChat.getStatus().equals("1"))
					{
						doctorChatlist=recentChat.getMessage();


						lvRecentChatList.setVisibility(View.VISIBLE);
						tvNoChat.setVisibility(View.GONE);

						dadapter=new RecentDoctorChatAdapter(RecentChatActivity.this, doctorChatlist);
						lvRecentChatList.setAdapter(dadapter);


					}
					else
					{
						lvRecentChatList.setVisibility(View.GONE);
						tvNoChat.setVisibility(View.VISIBLE);
					}
				}
				else
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



				}





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
	public void DoctorResponse(String response) 
	{
		if(response!=null && !response.equals(""))
		{
			try {
				//	JSONObject object=new JSONObject(Result);
				gson=new Gson();
				DoctorMessagePojo doctor=gson.fromJson(response, DoctorMessagePojo.class);

				if(doctor.getStatus().equals("1"))
				{
					doctorlist=doctor.getMessage();
					/*doctorAdapter=new SearchDoctorAdapter(RecentChatActivity.this, doctorlist);
					lvSearchList.setAdapter(doctorAdapter);
					 */

				}




			} catch (Exception e) {
				// TODO: handle exception
			}


		}
	}
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

		int length = s.length();
		if(length==0)
		{
			lvRecentChatList.setVisibility(View.VISIBLE);
			lvSearchDoctorList.setVisibility(View.GONE);
			if(pref.getLogintype()==1)
			{
				RecentChatResponse(pref.getRecentDocotsChat());
			}
			else
				RecentChatResponse(pref.getRecentPatientChat());
		}
		else
		{
			if(pref.getLogintype()==1)
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
						lvSearchDoctorList.setVisibility(View.VISIBLE);
						tvNoChat.setVisibility(View.GONE);
						if(patientAdapter==null && searchPatientlist!=null)
						{
							patientAdapter=new SearchPatientAdapter(RecentChatActivity.this,searchPatientlist);
							lvSearchDoctorList.setAdapter(patientAdapter);
						}
						else 
						{
							patientAdapter.setPatientList(searchPatientlist);
							patientAdapter.notifyDataSetChanged();


						}
					}
				}



			}
			else
			{

				lvRecentChatList.setVisibility(View.GONE);
				lvSearchDoctorList.setVisibility(View.VISIBLE);
				searchDoctorlist=new ArrayList<DoctorPojo>(); 
				if(doctorlist!= null)
				{
					for(int i=0;i<doctorlist.size();i++)
					{
						DoctorPojo doctor=doctorlist.get(i);
						String doctorName=doctor.getDoctor_name();
						if(doctorName.length()>=length&&doctorName.substring(0, length).equalsIgnoreCase(""+s))
							searchDoctorlist.add(doctor);

					}

					if(searchDoctorlist.size()==0)
					{

						lvSearchDoctorList.setVisibility(View.GONE);
						tvNoChat.setVisibility(View.VISIBLE);

					}
					else
					{
						lvSearchDoctorList.setVisibility(View.VISIBLE);
						tvNoChat.setVisibility(View.GONE);
						if(doctorAdapter==null)
						{

							doctorAdapter=new SearchDoctorAdapter(RecentChatActivity.this,searchDoctorlist);
							lvSearchDoctorList.setAdapter(doctorAdapter);
						}
						else 
						{
							doctorAdapter.setDoctorList(searchDoctorlist);
							doctorAdapter.notifyDataSetChanged();


						}
					}
				}
			}
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
