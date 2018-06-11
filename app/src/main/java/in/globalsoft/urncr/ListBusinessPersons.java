package in.globalsoft.urncr;


import in.globalsoft.beans.BeanSpecaility;
import in.globalsoft.beans.BeansListSpecialities;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.AdapterFacilityList;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;

public class ListBusinessPersons extends Activity
{
	AppPreferences appPref;
	List<String> listBusinessPersons;
	List<BeanSpecaility> specialities;
	ListView list_busineess;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_business_persons);
		appPref = new AppPreferences(this);
		listBusinessPersons = new ArrayList<String>();
		list_busineess = (ListView) findViewById(R.id.list_business);
		listOfDoctors();
		
		list_busineess.setAdapter(new AdapterFacilityList(this,listBusinessPersons));
		
		list_busineess.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				
				appPref.saveMapType(listBusinessPersons.get(arg2));
				Intent i = new Intent(ListBusinessPersons.this,HospitalsMap1.class);
				appPref.saveMapType(listBusinessPersons.get(arg2));
				i.putExtra("speciality_id",Integer.parseInt(specialities.get(arg2).getId()));

				startActivity(i);
				
			}
			
		});
	}
	
	public void listOfDoctors()
	{
		BeansListSpecialities beansListSpecialities = new Gson().fromJson(new AppPreferences(ListBusinessPersons.this).getListSpecialities(), BeansListSpecialities.class);
		specialities = beansListSpecialities.getSpecialities();

		for(BeanSpecaility specaility : specialities){
			listBusinessPersons.add(specaility.getName());
		}


	}
}