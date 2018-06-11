package in.globalsoft.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class GetRegionTask extends AsyncTask<Void , Void , Void>{

    private String url;
    private Activity activity;
    private ProgressDialog progressDialog;
    public GetRegionTask(String url , Activity activity) {
        this.url = url;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // progressDialog = ProgressDialog.show(activity , activity.getString())
    }

    @Override
    protected Void doInBackground(Void... voids) {

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
