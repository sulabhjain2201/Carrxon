package in.globalsoft.urncr;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;

import in.globalsoft.adapter.SavedAdapter;
import in.globalsoft.pojo.SavedDocRespo;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.tasks.GetSavedDocTask;
import in.globalsoft.util.Cons;
import in.globalsoft.util.RuntimePermissionsManager;

/**
 * Created by Linchpin66 on 09-07-17.
 */
public class SavedDocuments extends AppCompatActivity implements GetSavedDocTask.GetSavedListener {

    private RecyclerView savedList;
    private String user_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_doc);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red)));
        actionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\">" + getString(R.string.saved_doc) + "</font>"));
        savedList = (RecyclerView) findViewById(R.id.rvToDoList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        savedList.setLayoutManager(linearLayoutManager);
        savedList.setHasFixedSize(true);
        user_id= new AppPreferences(this).getUserId();
        if (Cons.isNetworkAvailable(this)) {
            new GetSavedDocTask(this,Cons.saved_doc_url+user_id).execute();
        } else {
            Cons.showDialog(this, getString(R.string.app_name), getString(R.string.net_not_available), "OK");
        }

        if (RuntimePermissionsManager.isPermissionCheckOpen()) {
            if (RuntimePermissionsManager.isBuildSysNeedRequiredPermissions()) {
                if (RuntimePermissionsManager.hasNeedRequiredPermissions(this)) {
                    RuntimePermissionsManager.requestRequiredPermissions(SavedDocuments.this, RuntimePermissionsManager.REQUIRED_PERMISSIONS_REQUEST_CODE);
                }
            }
            else
            {


            }
        }

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
    @Override
    public void onRequestPermissionsResult ( int requestCode, String[] permissions,
                                             int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (!RuntimePermissionsManager.isRequestPermissionsCode(requestCode)) {
            return;
        }

        if (RuntimePermissionsManager.hasDeniedPermissions(permissions, grantResults)) {
            finish();
        } else {

        }
    }

    @Override
    public void showList(SavedDocRespo response) {
        if(response!=null && response.getCode().equalsIgnoreCase("200"))
        {
          SavedAdapter adapter=new SavedAdapter(SavedDocuments.this,response.getDoc_list());
          savedList.setAdapter(adapter);
        }
    }
}
