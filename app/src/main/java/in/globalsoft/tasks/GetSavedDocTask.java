package in.globalsoft.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import in.globalsoft.pojo.SavedDocRespo;
import in.globalsoft.util.Cons;

/**
 * Created by Linchpin66 on 09-07-17.
 */
public class GetSavedDocTask extends AsyncTask<Void,Void,SavedDocRespo> {
    private String url;
    private GetSavedListener listener;
    private ProgressDialog progressDialog;
    public GetSavedDocTask(GetSavedListener listener, String url) {
        this.listener=listener;
        this.url=url;
    }
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog((Context) listener);
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected SavedDocRespo doInBackground(Void... voids) {
        String response = Cons.http_connection(url);
        if (null != response) {
            return new Gson().fromJson(response, SavedDocRespo.class);
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(SavedDocRespo savedDocPojo) {
        super.onPostExecute(savedDocPojo);

        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        listener.showList(savedDocPojo);


    }



    public interface GetSavedListener {
        void showList(SavedDocRespo response);
    }


}
