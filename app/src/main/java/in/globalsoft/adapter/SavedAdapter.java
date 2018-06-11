package in.globalsoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.globalsoft.pojo.SavedDocPojo;
import in.globalsoft.urncr.PatientDocsDetailScreen;
import in.globalsoft.urncr.R;
import in.globalsoft.util.Cons;

/**
 * Created by Linchpin66 on 09-07-17.
 */
public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SaveViewHolder> {
    private Context ctx;
    private List<SavedDocPojo> list;

    public SavedAdapter(Context context, List<SavedDocPojo> doc_list) {
        list = doc_list;
        ctx = context;
    }


    @Override
    public SaveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.saved_item, parent, false);
        return new SaveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SaveViewHolder holder, int position) {

        SavedDocPojo savedDocPojo = list.get(position);

        holder.title.setText(savedDocPojo.getDoc_title());



        if(savedDocPojo.getDoc_description().length()>10)
                holder.description.setText(savedDocPojo.getDoc_description());
        else
            holder.description.setText(savedDocPojo.getDoc_description());



        holder.category.setText(savedDocPojo.getDoc_category());

        try {
            holder.time.setText(Cons.convertMiliSecondsToString(Long.parseLong(savedDocPojo.getDoc_time()),"dd-MMM-yyyy hh:mm"));
        }
        catch (final Exception e) {
            holder.time.setText(savedDocPojo.getDoc_time());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SaveViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description, category,time;
        private ImageView download_file;

        public SaveViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.saved_title);
            description = (TextView) itemView.findViewById(R.id.saved_description);
            category = (TextView) itemView.findViewById(R.id.category);
            download_file= (ImageView) itemView.findViewById(R.id.download_icon);
            time= (TextView) itemView.findViewById(R.id.time);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(ctx,PatientDocsDetailScreen.class);
                    intent.putExtra("detail",list.get(getLayoutPosition()));
                    ctx.startActivity(intent);
                }
            });


            download_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SavedDocPojo savedDocPojo = list.get(getLayoutPosition());
                    Cons.downLoad(ctx,savedDocPojo);

                }
            });

        }
    }
}
