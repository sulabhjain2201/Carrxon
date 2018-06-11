package in.globalsoft.adapter;

import in.globalsoft.urncr.R;
import in.globalsoft.pojo.PatientPojo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.util.lazylist.ImageLoader;

public class SearchPatientAdapter extends BaseAdapter
{
	Context con;
	List<PatientPojo> patientList;
	ImageLoader imageLoader;
	private ViewHolder viewHolder;

	public SearchPatientAdapter(Context con,List<PatientPojo> patientList) 
	{
		this.con=con;
		this.patientList=patientList;

		imageLoader=new ImageLoader(con);

	}


	public void setPatientList(List<PatientPojo> patientList) {
		this.patientList = new ArrayList<PatientPojo>();
		this.patientList.addAll(patientList);
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(patientList==null)
			return 0;
		else 
		{
			return patientList.size();	
		}		
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(view==null)
		{
			LayoutInflater inflater =  ((Activity) con).getLayoutInflater();
			view = inflater.inflate(R.layout.recent_chat_adapter, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.ivFrndImage=(ImageView) view.findViewById(R.id.ivFrndImage);
			viewHolder.tvFrndName=(TextView) view.findViewById(R.id.tvFrndName);
			viewHolder.tvFrndMsg=(TextView) view.findViewById(R.id.tvFrndMsg);
			viewHolder.tvOnline=(TextView) view.findViewById(R.id.tvOnline);
			view.setTag(viewHolder);
		}
		else
			viewHolder = (ViewHolder) view.getTag();

		PatientPojo patient=patientList.get(position);

		//imageLoader.displayImage( ConstantUtil.urlBase+bar.getFlyerUrl(), viewHolder.ivBarFetelogo, options, null);
		viewHolder.tvFrndName.setText(patient.getFirst_name()+patient.getLast_name());
		viewHolder.tvFrndMsg.setText("");
		if(patient.isIsonline())
			viewHolder.tvOnline.setText("online");
		else {
			viewHolder.tvOnline.setText("offline");
		}

		return view;
	}
	
	public static class ViewHolder
	{
		TextView tvFrndName,tvFrndMsg,tvOnline;
		ImageView ivFrndImage;


	}
	

}

