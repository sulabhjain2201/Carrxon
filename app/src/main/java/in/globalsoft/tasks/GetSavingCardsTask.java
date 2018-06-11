package in.globalsoft.tasks;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import in.globalsoft.pojo.ListSavingCardPojo;
import in.globalsoft.util.Cons;

public class GetSavingCardsTask extends AsyncTask<Object, Object, ListSavingCardPojo> {

    private final SavingCardsUpdate savingCardsInstance;
    private ProgressDialog progressDialog;

    public GetSavingCardsTask(final SavingCardsUpdate savingCardsInstance) {

        this.savingCardsInstance = savingCardsInstance;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog((Context) savingCardsInstance);
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected ListSavingCardPojo doInBackground(Object... voids) {

        final String response = Cons.http_connection(Cons.URL_COPAY_SAVING_CARDS);

        if (null != response) {
            return new Gson().fromJson(response, ListSavingCardPojo.class);
        } else {
            return null;
        }


    }

    @Override
    protected void onPostExecute(ListSavingCardPojo savingCards) {
        super.onPostExecute(savingCards);

        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        savingCardsInstance.updateSavingCards(savingCards);


    }

    public interface SavingCardsUpdate {
        void updateSavingCards(ListSavingCardPojo savingCards);
    }
}
