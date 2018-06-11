package in.globalsoft.urncr;

import in.globalsoft.beans.BeanSpecaility;
import in.globalsoft.beans.BeansHospitalInfo;
import in.globalsoft.beans.BeansListSpecialities;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.urncr.R;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

public class ListDoctors extends Activity {
	
	ListView listDoctors;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_list_doctors);
		
		final List<BeansHospitalInfo> listDoctor = HospitalsMap1.hospitalListBeans.getHospital_list();
		
		listDoctors = (ListView)findViewById(R.id.list_hospitals);
		
		listDoctors.setAdapter(new AdapterSearchDoctorsList(ListDoctors.this, listDoctor));
		listDoctors.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				
				if(listDoctor.get(arg2).getOpen_status().equals(""))
				{
					Toast.makeText(ListDoctors.this, "Hospital is not registered", Toast.LENGTH_LONG).show();
				}
				else
				{
				Intent i = new Intent(ListDoctors.this,HospitalDescription.class);
                    final String specialityId = listDoctor.get(arg2).getSpeciality_id();

                    String strSpecialityList = new AppPreferences(ListDoctors.this).getListSpecialities();

                    BeansListSpecialities beansListSpecialities = new Gson().fromJson(strSpecialityList, BeansListSpecialities.class);
                    String speciality = "";
                    for(BeanSpecaility beanSpecaility : beansListSpecialities.getSpecialities()){
                        if(beanSpecaility.getId().equals(specialityId)){
                            speciality = beanSpecaility.getName();
							break;
                        }
                    }



                    new AppPreferences(ListDoctors.this).saveMapType(speciality);
				i.putExtra("position", arg2);
				startActivity(i);
				}
			
				
			}
			
		});
		
		
		
		
	}

}
