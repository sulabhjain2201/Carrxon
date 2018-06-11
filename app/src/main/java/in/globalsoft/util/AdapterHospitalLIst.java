package in.globalsoft.util;


import in.globalsoft.urncr.HospitalsMap1;
import in.globalsoft.urncr.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterHospitalLIst extends BaseAdapter 
{
	
	Context con;

	private LayoutInflater inflater=null;
	
	public AdapterHospitalLIst(Context con)
	{
		this.con = con;
		
		
		inflater =(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() 
	{
		
		return HospitalsMap1.hospitalListBeans.getHospital_list().size();
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
		
		
		View vi=arg1;
	    if(arg1==null)

	    
	    vi = inflater.inflate(R.layout.adapter_hospitallist, null);
	    
	    TextView tv_hospitalname=(TextView)vi.findViewById(R.id.list_item);
	    tv_hospitalname.setText(HospitalsMap1.hospitalListBeans.getHospital_list().get(position).getName());
//	    meeting_info.setText(trip_list.get(position).getPurpose());
	    
	    return vi;
		
	}

}

