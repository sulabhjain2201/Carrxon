package in.globalsoft.urncr;

import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.AdapterFacilityList;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FacilityList extends Activity 
{
	ListView listFacilities;
	List<String> listFacility;
	AppPreferences appPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facility_list);
		appPref = new AppPreferences(this);
		listFacility = new ArrayList<String>();
		listFacilities = (ListView) findViewById(R.id.list_facilities);
		listOfFacilities();
		
		listFacilities.setAdapter(new AdapterFacilityList(this,listFacility));
		
		listFacilities.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
//				Intent i = new Intent(FacilityList.this,HospitalList.class);
//				appPref.saveMapType(listFacility.get(arg2));
//				i.putExtra("speciality_id",arg2+1);
//				startActivity(i);

				Toast.makeText(FacilityList.this,"No Record Found",Toast.LENGTH_SHORT).show();
				
			}
			
		});
	}
	
	public void listOfFacilities()
	{
		String facility_array[]= {"Emergency Departments","Behavioral Health","Dental Centers"
				,"Dialysis","Family Medicine","Internal Medicine","Mind/Body/Health","Gynecology",
				"Pediatricians","Sleep Centers","Specialty Practices","Health Centers","Imaging"
				,"Lab Services","Pharmacy","Rehabilitation","Surgical Centers","Administration"};
		for(int i = 0;i<facility_array.length;i++)
		{
			listFacility.add(facility_array[i]);
		}
		
	}

	

}
