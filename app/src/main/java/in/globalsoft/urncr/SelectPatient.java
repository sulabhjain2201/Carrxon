package in.globalsoft.urncr;

import in.globalsoft.urncr.R;
import in.globalsoft.util.AdapterPatientList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class SelectPatient extends Activity 
{
	ListView list_patient;
Button btn_addPatient;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_patient);
		btn_addPatient = (Button)findViewById(R.id.add_patient_btn);
		list_patient = (ListView) findViewById(R.id.list_patients);
		AdapterPatientList adapter_patient = new AdapterPatientList(this);
		list_patient.setAdapter(adapter_patient);
		list_patient.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
			
				Intent i = new Intent(SelectPatient.this,PatientInfo1.class);
				i.putExtra("position", arg2);
				startActivity(i);
			}
			
		});
		btn_addPatient.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(SelectPatient.this,PatientInfo1.class);
				i.putExtra("position", -1);
				startActivity(i);
				
			}
		});
	
	}
	
	
	

}
