package in.globalsoft.adapter;

import in.globalsoft.urncr.R;
import in.globalsoft.pojo.ChatPojo;
import in.globalsoft.preferences.AppPreferences;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.util.lazylist.ImageLoader;

public class DoctorChatListAdapter extends BaseAdapter {

	Context con;
	List<ChatPojo> chatList;
	ImageLoader imageLoader;
	private ViewHolder viewHolder;
	AppPreferences pref;
	
	public  DoctorChatListAdapter(Context con,List<ChatPojo> chatList)
	{
		this.con=con;
		this.chatList=chatList;
		
		pref=new AppPreferences(con);
		
		imageLoader=new ImageLoader(con);
	}
	
	public void setChatList(ChatPojo pojo) {
		this.chatList.add(pojo);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(chatList==null)
			return 0;
		else 
		{
			return chatList.size();	
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
			view = inflater.inflate(R.layout.chat_rows, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.ivFrndImage=(ImageView) view.findViewById(R.id.ivfriendsImage);
			viewHolder.tvFrndMsg=(TextView) view.findViewById(R.id.tvFriendsMsgs);
			viewHolder.tvFrndStatus=(TextView) view.findViewById(R.id.tvFrndStatus);
			viewHolder.ivMyImage=(ImageView) view.findViewById(R.id.ivMyImage);
			viewHolder.tvMyMsg=(TextView) view.findViewById(R.id.tvMyMsgs);
			viewHolder.friendsPan=(RelativeLayout) view.findViewById(R.id.friendsPan);
			viewHolder.MyPan=(RelativeLayout) view.findViewById(R.id.MyPan);
			view.setTag(viewHolder);
		}
		else
			viewHolder = (ViewHolder) view.getTag();

		ChatPojo chat=chatList.get(position);
		if(!chat.getUser_id().equals(pref.getDoctorId())&& !chat.getUser_id().equals(pref.getOfficeId()))
		{
			
			viewHolder.friendsPan.setVisibility(View.VISIBLE);
			viewHolder.MyPan.setVisibility(View.GONE);
			
			//viewHolder.ivFrndImage.setText;
			viewHolder.tvFrndMsg.setText(chat.getChat());
			
			
			viewHolder.tvFrndStatus.setText("");
			
			
			
		}
		else
		{
			viewHolder.friendsPan.setVisibility(View.GONE);
			viewHolder.MyPan.setVisibility(View.VISIBLE);
			
			viewHolder.tvMyMsg.setText(chat.getChat());
			
			
		}
		//imageLoader.displayImage( ConstantUtil.urlBase+bar.getFlyerUrl(), viewHolder.ivBarFetelogo, options, null);
		

		return view;
	}
	
	public static class ViewHolder
	{
		TextView tvFrndMsg,tvFrndStatus,tvMyMsg,tvMyStatus;
		ImageView ivFrndImage,ivMyImage;
		RelativeLayout friendsPan,MyPan;


	}
	

}
