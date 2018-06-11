package in.globalsoft.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.util.lazylist.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import in.globalsoft.urncr.R;
import in.globalsoft.pojo.DoctorOfficePojo;

/**
 * Created by LinchPin on 3/26/2015.
 */
public class SearchDoctorOfficeAdapter extends BaseAdapter {
    Context con;
    List<DoctorOfficePojo> doctorList;
    ImageLoader imageLoader;

    private ViewHolder viewHolder;

    public SearchDoctorOfficeAdapter(Context con,List<DoctorOfficePojo> doctorList)
    {
        this.con=con;
        this.doctorList=doctorList;

        imageLoader=new ImageLoader(con);

    }





    public List<DoctorOfficePojo> getDoctorList() {
        return doctorList;
    }





    public void setDoctorList(List<DoctorOfficePojo> doctorList) {
        this.doctorList = new ArrayList<DoctorOfficePojo>();
        this.doctorList.addAll(doctorList);
    }





    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(doctorList==null)
            return 0;
        else
        {
            return doctorList.size();
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

        DoctorOfficePojo doctr=doctorList.get(position);

        //imageLoader.displayImage( ConstantUtil.urlBase+bar.getFlyerUrl(), viewHolder.ivBarFetelogo, options, null);
        viewHolder.tvFrndName.setText(doctr.getDoctor_name());
        viewHolder.tvFrndMsg.setText(doctr.getChat());
        if(doctr.isIsonline())
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


