package in.globalsoft.util;

import in.globalsoft.beans.BeansDoctorsByHospitalList;
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

public class AdapterDoctorList extends BaseAdapter
{
	

	Context con;
	

	private LayoutInflater inflater=null;
	BeansDoctorsByHospitalList doctorListBeans;
	ImageLoader imageLoader;
	
	
	public AdapterDoctorList(Context con,BeansDoctorsByHospitalList doctorListBeans)
	{
		this.con = con;
		imageLoader=new ImageLoader(con);
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

	    
	    vi = inflater.inflate(R.layout.adapter_all_doctor_list, null);
	    
	    TextView tv_doctorName=(TextView)vi.findViewById(R.id.doctor_name);
	    tv_doctorName.setText(doctorListBeans.getDoctor_list().get(position).getDoctor_name());
	    
	    ImageView iv_DoctorImage=(ImageView)vi.findViewById(R.id.doctor_image);
	    imageLoader.DisplayImage("http://carrxon.com/CarrxonWebServices/ws/"+doctorListBeans.getDoctor_list().get(position).getDoctor_image(), ((Activity) con).getParent(), iv_DoctorImage);
	  
	    TextView tv_doctorSpecaility=(TextView)vi.findViewById(R.id.doctor_speciality);
	    tv_doctorSpecaility.setText(doctorListBeans.getDoctor_list().get(position).getDoctor_speciality());
	  
	    
	    return vi;
		
	}

}

