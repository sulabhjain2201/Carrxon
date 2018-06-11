package in.globalsoft.util;

import in.globalsoft.beans.BeansDoctorList;
import in.globalsoft.urncr.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.util.lazylist.ImageLoader;

public class AdapterPhysicianList extends BaseAdapter
{
	

	Context con;
	

	private LayoutInflater inflater=null;
	BeansDoctorList doctorListBeans;
	ImageLoader imageLoader;
	
	
	public AdapterPhysicianList(Context con,BeansDoctorList doctorListBeans)
	{
		this.con = con;
		imageLoader=new ImageLoader(con.getApplicationContext());
		this.doctorListBeans = doctorListBeans;
		
		
		inflater =(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() 
	{
		
		return doctorListBeans.getDoctor_list().size();
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

	    
	    vi = inflater.inflate(R.layout.adapter_physicianlist, null);
	    
	    TextView tv_physicianName=(TextView)vi.findViewById(R.id.physian_name);
	    tv_physicianName.setText(doctorListBeans.getDoctor_list().get(position).getDoctor_name());
	    
	    ImageView iv_physianImage=(ImageView)vi.findViewById(R.id.physian_image);
	    imageLoader.DisplayImage("http://carrxon.com/CarrxonWebServices/ws/"+doctorListBeans.getDoctor_list().get(position).getDoctor_name(), ((Activity) con).getParent(), iv_physianImage);
	  //  tv_physicianName.setText(HospitalsMap1.hospitalListBeans.getHospital_list().get(position).getName());
//	    meeting_info.setText(trip_list.get(position).getPurpose());
	    
	    return vi;
		
	}

}

