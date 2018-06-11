package in.globalsoft.adapter;

import in.globalsoft.adapter.RecentChatAdapter.ViewHolder;
import in.globalsoft.urncr.R;
import in.globalsoft.pojo.MessagePatientPojo;

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

public class RecentDoctorChatAdapter extends BaseAdapter
{

	Context con;
	List<MessagePatientPojo> messageList;
	ImageLoader imageLoader;
	
	private ViewHolder viewHolder;
	
	public RecentDoctorChatAdapter(Context con,List<MessagePatientPojo> messageList) 
	{
		this.con=con;
		this.messageList=messageList;

		imageLoader=new ImageLoader(con);

	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(messageList==null)
			return 0;
		else 
		{
			return messageList.size();	
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

		MessagePatientPojo msg=messageList.get(position);

		//imageLoader.displayImage( ConstantUtil.urlBase+bar.getFlyerUrl(), viewHolder.ivBarFetelogo, options, null);
		viewHolder.tvFrndName.setText(msg.getPatient().getFirst_name()+" "+msg.getPatient().getLast_name());
		viewHolder.tvFrndMsg.setText(msg.getChat());
		if(msg.getPatient().isIsonline())
			viewHolder.tvOnline.setText("online");
		else {
			viewHolder.tvOnline.setText("offline");
		}

		return view;
	}

}
