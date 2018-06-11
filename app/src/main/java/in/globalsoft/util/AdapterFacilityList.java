package in.globalsoft.util;


import in.globalsoft.urncr.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterFacilityList extends BaseAdapter
{
	List<String> listFacility;
	Context con;

private LayoutInflater inflater=null;

public AdapterFacilityList(Context con, List<String> listFacility)
{
	this.con = con;
	this.listFacility = listFacility;
	
	
	
	inflater =(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}

@Override
public int getCount() 
{
	
	return listFacility.size();
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

    
    vi = inflater.inflate(R.layout.adapter_facilitylist, null);
    
    TextView tv_hospitalname=(TextView)vi.findViewById(R.id.list_item);
    tv_hospitalname.setText(listFacility.get(position));
//    meeting_info.setText(trip_list.get(position).getPurpose());
    
    return vi;
	
}



}