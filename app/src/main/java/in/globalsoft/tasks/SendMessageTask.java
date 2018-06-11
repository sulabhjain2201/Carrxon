package in.globalsoft.tasks;

import in.globalsoft.urncr.ChatActivity;
import in.globalsoft.urncr.OfficeChatActivity;
import in.globalsoft.util.Cons;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class SendMessageTask extends AsyncTask<Void, Void, Void> {

	Context con;
	String url;
	String response;
	ProgressDialog pd;
    boolean isOfficechat;
	public SendMessageTask(Context con,String url,boolean isOfficeChat)
	{
		this.con=con;
		this.url=url;
        this.isOfficechat= isOfficeChat;
		pd=new ProgressDialog(con);
		pd.setCancelable(false);
		pd.setMessage("Loading...");
		pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		pd.show();

	}



	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
	
		response=Cons.http_connection(url);
		
		return null;
	}


	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(pd.isShowing())
			pd.dismiss();
        if(!isOfficechat)
		((ChatActivity) con).SendChatResult(response);
        else
            ((OfficeChatActivity) con).SendChatResult(response);
	}


}
