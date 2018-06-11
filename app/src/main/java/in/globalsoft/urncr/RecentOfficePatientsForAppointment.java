package in.globalsoft.urncr;

import in.globalsoft.adapter.RecentDoctorChatAdapter;
import in.globalsoft.adapter.RecentOfficeChatAdapter;
import in.globalsoft.adapter.SearchDoctorOfficeAdapter;
import in.globalsoft.adapter.SearchPatientAdapter;
import in.globalsoft.beans.BeansLogin;
import in.globalsoft.urncr.R;
import in.globalsoft.interfaces.RecentChatResult;
import in.globalsoft.interfaces.SearchDoctorResponse;
import in.globalsoft.interfaces.SearchPatientResponse;
import in.globalsoft.pojo.DoctorOfficeMessagePojo;
import in.globalsoft.pojo.DoctorOfficePojo;
import in.globalsoft.pojo.MessagePatientPojo;
import in.globalsoft.pojo.MessagePojoOffice;
import in.globalsoft.pojo.PatientMessagePojo;
import in.globalsoft.pojo.PatientPojo;
import in.globalsoft.pojo.RecentChatOfficePojo;
import in.globalsoft.pojo.RecentChatPojo;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.tasks.RecentPatientsByOfficeTask;
import in.globalsoft.tasks.SearchAllPatients;
import in.globalsoft.tasks.GetRecentChatsTask;

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

public class RecentOfficePatientsForAppointment extends Activity implements RecentChatResult,SearchDoctorResponse,TextWatcher,SearchPatientResponse
{
    EditText etSearch;
    ListView lvRecentChatList,lvSearchDoctorList;
    static String Response;
    GetRecentChatsTask recent;
    RecentChatPojo rChat;
    RecentOfficeChatAdapter adapter;
    RecentDoctorChatAdapter dadapter;
    SearchDoctorOfficeAdapter doctorAdapter=null;
    SearchPatientAdapter patientAdapter=null;
    List<MessagePojoOffice> list;
    List<MessagePatientPojo> doctorChatlist;
    List<DoctorOfficePojo> doctorlist;
    List<PatientPojo> patientlist;
    List<DoctorOfficePojo> searchDoctorlist;
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




                //code for open appointment window with patient id from recent list
                AppPreferences pref = new AppPreferences(getApplicationContext());
                pref.setUserId(doctorChatlist.get(position).getPatient().getPatient_id());
                pref.saveDoctorIdByPatient(pref.getDoctorId());
                saveUserInfoIntoBean(doctorChatlist.get(position).getPatient());
                Intent i = new Intent(RecentOfficePatientsForAppointment.this,AvailableAppointments.class);
                startActivity(i);


            }
        });



        lvSearchDoctorList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {


                //code for open appointment window with patient id from search  list
                AppPreferences pref = new AppPreferences(getApplicationContext());
                pref.setUserId(searchPatientlist.get(position).getPatient_id());
                pref.saveDoctorIdByPatient(pref.getDoctorId());
                saveUserInfoIntoBean(searchPatientlist
                        .get(position));
                Intent i = new Intent(RecentOfficePatientsForAppointment.this,AvailableAppointments.class);
                startActivity(i);



            }
        });






    }

    //save the user login info
    //convert patient poojo to BeansLogin
    private void saveUserInfoIntoBean(PatientPojo patientPojo) {
        BeansLogin userInfo = new BeansLogin();
        userInfo.setAddress(patientPojo.getAddress());
        userInfo.setCity(patientPojo.getCity());
        userInfo.setEmail(patientPojo.getEmail());
        userInfo.setFirst_name(patientPojo.getFirst_name());
        userInfo.setHome_phone(patientPojo.getHome_phone());
        userInfo.setLast_name(patientPojo.getLast_name());
        userInfo.setPatient_id(patientPojo.getPatient_id());
        userInfo.setWork_phone(patientPojo.getWork_phone());
        userInfo.setZip(patientPojo.getZip());
        userInfo.setState(patientPojo.getState());
        Gson gson = new Gson();
        String user = gson.toJson(userInfo);
        AppPreferences appPreferences = new AppPreferences(getApplicationContext());
        appPreferences.saveUserLoginInfo(user);
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

        pref=new AppPreferences(RecentOfficePatientsForAppointment.this);

        new SearchAllPatients(RecentOfficePatientsForAppointment.this,true).execute();
        String url="http://carrxon.com/CarrxonWebServices/ws/office_recent_chat.php?user_id="+pref.getOfficeId()+"&status=doctor_office";
        new RecentPatientsByOfficeTask(RecentOfficePatientsForAppointment.this,url).execute();
            if(!pref.getSearchedPatient().equals(""))
            {
                PatientResponse(pref.getSearchedPatient());
            }
            if(!pref.getRecentPatientChatByOffice().equals(""))
            {
                RecentChatResponse(pref.getRecentPatientChatByOffice());
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


                    RecentChatOfficePojo recentChat=gson.fromJson(Result, RecentChatOfficePojo.class);

                    if(recentChat.getStatus().equals("1"))
                    {
                        doctorChatlist=recentChat.getMessage();


                        lvRecentChatList.setVisibility(View.VISIBLE);
                        tvNoChat.setVisibility(View.GONE);

                        dadapter=new RecentDoctorChatAdapter(RecentOfficePatientsForAppointment.this, doctorChatlist);
                        lvRecentChatList.setAdapter(dadapter);


                    }
                    else
                    {
                        lvRecentChatList.setVisibility(View.GONE);
                        tvNoChat.setVisibility(View.VISIBLE);
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
                DoctorOfficeMessagePojo doctor=gson.fromJson(response, DoctorOfficeMessagePojo.class);

                if(doctor.getStatus().equals("1"))
                {
                    doctorlist=doctor.getMessage();
					/*doctorAdapter=new SearchDoctorAdapter(RecentOfficeChat.this, doctorlist);
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

                RecentChatResponse(pref.getRecentDocotsChat());

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
                        lvSearchDoctorList.setVisibility(View.VISIBLE);
                        tvNoChat.setVisibility(View.GONE);
                        if(patientAdapter==null && searchPatientlist!=null)
                        {
                            patientAdapter=new SearchPatientAdapter(RecentOfficePatientsForAppointment.this,searchPatientlist);
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
					/*doctorAdapter=new SearchDoctorAdapter(RecentOfficeChat.this, doctorlist);
					lvSearchList.setAdapter(doctorAdapter);
					 */

                }




            } catch (Exception e) {
                // TODO: handle exception
            }


        }
    }

}
