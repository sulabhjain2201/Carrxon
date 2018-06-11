package in.globalsoft.urncr;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.globalsoft.pojo.SavedDocPojo;
import in.globalsoft.util.Cons;

/**
 * Created by Linchpin66 on 12-07-17.
 */
public class PatientDocsDetailScreen extends AppCompatActivity {
    SavedDocPojo savedDocPojo;
    private TextView title, desc, cat, time, path;
    private ImageView photo, download_icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red)));
        actionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\">" + getString(R.string.detail) + "</font>"));
        savedDocPojo = (SavedDocPojo) getIntent().getSerializableExtra("detail");

        init();

    }

    private void init() {
        title = (TextView) findViewById(R.id.saved_title);
        desc = (TextView) findViewById(R.id.saved_description);
        cat = (TextView) findViewById(R.id.category);
        time = (TextView) findViewById(R.id.time);
        photo = (ImageView) findViewById(R.id.photo);
        path = (TextView) findViewById(R.id.path);
        download_icon = (ImageView) findViewById(R.id.download_icon);
        title.setText(savedDocPojo.getDoc_title());
        desc.setText(savedDocPojo.getDoc_description());
        cat.setText(savedDocPojo.getDoc_category());
        try {
            String docTime = Cons.convertMiliSecondsToString(Long.parseLong(savedDocPojo.getDoc_time()), "dd-MMM-yyyy hh:mm");
            time.setText(docTime);
        }
        catch (Exception e){
            time.setText(savedDocPojo.getDoc_time());
        }

        if (savedDocPojo.getDoc_url() != null && (savedDocPojo.getDoc_url().contains("png") || savedDocPojo.getDoc_url().contains("jpg") || savedDocPojo.getDoc_url().contains("pdf"))) {
            photo.setVisibility(View.VISIBLE);
            path.setVisibility(View.GONE);
            Picasso.with(this)
                    .load(Cons.download_url+savedDocPojo.getDoc_url())
                    .placeholder(R.drawable.place_holder) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.place_holder)
                     .fit()//this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(photo);
        } else {
            photo.setVisibility(View.GONE);
            path.setVisibility(View.VISIBLE);
            path.setText(savedDocPojo.getDoc_url());

        }


        download_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cons.downLoad(PatientDocsDetailScreen.this, savedDocPojo);
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action buttons
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
