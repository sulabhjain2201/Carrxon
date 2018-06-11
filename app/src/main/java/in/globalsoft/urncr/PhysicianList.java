package in.globalsoft.urncr;

import in.globalsoft.beans.BeansDoctorList;
import in.globalsoft.urncr.R;
import in.globalsoft.util.AdapterPhysicianList;
import in.globalsoft.util.Cons;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

public class PhysicianList extends Activity 
{
  ListView listPhysician;
  String responseString;
  BeansDoctorList doctorListBeans;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician_list);
		listPhysician = (ListView)findViewById(R.id.list_physician);
	
		if(Cons.isNetworkAvailable(this))
		{
			new GetPhysicianListTask(this).execute();
		}
		else
		{
			Cons.showDialog(this, "Carrxon", "Internet not available.", "OK");
			finish();
		}
		
		listPhysician.setOnItemClickListener(new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) 
		{
			Intent i = new Intent(PhysicianList.this,PhysicainDetail.class);
			i.putExtra("position", doctorListBeans.getDoctor_list().get(arg2).getDoctor_id());
			startActivity(i);
			
		}
		
	});

	}

	public class GetPhysicianListTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pd;
		Context con;

		public GetPhysicianListTask(Context con)
		{
			this.con = con;
		}

		@Override
		protected void onPreExecute() 
		{
			pd = ProgressDialog.show(con, null, "Loading...");
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... params)
		{
			
			String url = Cons.url_get_physicianlist;
			responseString = Cons.http_connection(url);
			Gson gson = new Gson();
			
			doctorListBeans = gson.fromJson(responseString, BeansDoctorList.class);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			if(pd.isShowing())
			{
				pd.dismiss();
			}
			Message myMessage = new Message(); 
			myMessage.obj = "doctor_list";
			myHandler.sendMessage(myMessage);
			super.onPostExecute(result);

		}

	}
	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("doctor_list"))
			{
				if (!isFinishing()) 
				{

					if((doctorListBeans == null)||Cons.isNetAvail==1)

					{

						Cons.isNetAvail = 0;
						Toast.makeText(PhysicianList.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
					}

					else if(doctorListBeans.getCode()==200)
					{
						
						listPhysician.setAdapter(new AdapterPhysicianList(PhysicianList.this,doctorListBeans));
					}
					else 
					{
						
						Toast.makeText(PhysicianList.this, doctorListBeans.getMessage(), Toast.LENGTH_LONG).show();
					}



				}
			}


		}
	};


}
