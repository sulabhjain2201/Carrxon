package in.globalsoft.util;

import in.globalsoft.beans.BeansPatientInfo;
import in.globalsoft.urncr.InfoVerification;
import in.globalsoft.urncr.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterPatientList extends BaseAdapter
{
	Context con;

	private LayoutInflater inflater=null;
	
	public AdapterPatientList(Context con)
	{
		this.con = con;
		
		
		inflater =(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() 
	{
		
		return InfoVerification.patientListBeans.getMember_list().size();
	}

	@Override
	public Object getItem(int arg0)
	{
		
		return null;
	}

	@Override
	public long getItemId(int position) 
	{
		
		return 0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup parent)
	{
		
		BeansPatientInfo patientInfobeans = InfoVerification.patientListBeans.getMember_list().get(position);
		View vi=arg1;
	    if(arg1==null)

	    
	    vi = inflater.inflate(R.layout.adapter_patient_list, null);
	    
	    TextView tv_patientname=(TextView)vi.findViewById(R.id.patient_name_text);
	    TextView tv_patientbirth=(TextView)vi.findViewById(R.id.patient_birthday_text);
	    
	    tv_patientname.setText(patientInfobeans.getFirst_name()+" "+patientInfobeans.getLast_name());
	    tv_patientbirth.setText(changeDateFormate(patientInfobeans.getDob()));
	    //tv_hospitalname.setText(HospitalsMap.places.get(position).get("place_name"));
//	    meeting_info.setText(trip_list.get(position).getPurpose());
	    
	    return vi;
		
	}
	public String changeDateFormate(String date)
	{
		SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd"); 
		Date dateObj = null;
		try 
		{
			dateObj = curFormater.parse(date);
		} 
		catch (ParseException e) 
		{

			e.printStackTrace();
		} 
		SimpleDateFormat postFormater = new SimpleDateFormat("MM-dd-yyyy"); 

		String newDateStr = postFormater.format(dateObj);
		return newDateStr;

	}


}
