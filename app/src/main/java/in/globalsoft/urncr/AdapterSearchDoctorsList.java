package in.globalsoft.urncr;

import in.globalsoft.beans.BeansHospitalInfo;
import in.globalsoft.urncr.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterSearchDoctorsList extends BaseAdapter{
	
	Context context;
	List<BeansHospitalInfo> listDoctor;
	LayoutInflater myInflater;
	
	public AdapterSearchDoctorsList(Context con, List<BeansHospitalInfo> listDoctor)
	{
		
		context= con;
		this.listDoctor= listDoctor;
		
		myInflater=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listDoctor.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup parent) {
		View convertView=arg1;

		if (convertView==null)
			convertView=myInflater.inflate(R.layout.list_item_doctor_search,null);
		
		TextView docName= (TextView)convertView.findViewById(R.id.tv_doctor_name);
		
		docName.setText(listDoctor.get(position).getName());
		
		TextView docAddress= (TextView)convertView.findViewById(R.id.tv_doctor_address);
		
		docAddress.setText(listDoctor.get(position).getVicinity());
		return convertView;
	}

}
