package in.globalsoft.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import in.globalsoft.urncr.R;



public class UrncrTask extends AsyncTask<String,String,String> {

    private boolean isProgressDialog;
    private String url;
    private ProgressDialog pd;
    private Activity context;
    private String progressTitle;
    private String progressMessage;
    private Object responseObj;


    public UrncrTask(final Activity context, final  boolean isProgressDialog, final String url){

        this.isProgressDialog = isProgressDialog;
        this.url = url;
        this.context = context;

        setDefaults();

    }






    private void setDefaults() {

        this.progressTitle = context.getString(R.string.app_name);
        this.progressMessage = context.getString(R.string.defaultProgressMsg);
    }


    @Override
    protected void onPreExecute() {
        if(isProgressDialog){
            pd = ProgressDialog.show(context,progressTitle,progressMessage);
        }

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... inputs) {
        return null;
    }



    @Override
    protected void onPostExecute(String response) {

        if(isProgressDialog && pd != null && pd.isShowing()){
            pd.dismiss();
        }



        super.onPostExecute(response);
    }



    public boolean isProgressDialog() {
        return isProgressDialog;
    }

    public void setProgressDialog(boolean progressDialog) {
        isProgressDialog = progressDialog;
    }


    public Object getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(Object responseObj) {
        this.responseObj = responseObj;
    }

    public String getProgressTitle() {
        return progressTitle;
    }

    public void setProgressTitle(String progressTitle) {
        this.progressTitle = progressTitle;
    }

    public String getProgressMessage() {
        return progressMessage;
    }


    public void setProgressMessage(String progressMessage) {
        this.progressMessage = progressMessage;
    }

}
