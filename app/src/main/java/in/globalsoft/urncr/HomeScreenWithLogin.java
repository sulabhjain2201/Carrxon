package in.globalsoft.urncr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import in.globalsoft.beans.BeansLogin;
import in.globalsoft.preferences.AppPreferences;

public class HomeScreenWithLogin extends Activity 
{
	private ImageView btn_logOut;
	private Button btnChat,uploadADocument,saved_document;
	private AppPreferences appPref;
	private BeansLogin loginBeans;
	private TextView tv_userName;
	private Button btn_painMgmtSpecialities,btn_findPhysian,btn_findFacility,btn_search_doctor;
	private Button btnSavingCards;
	private ImageView btnInvite;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen_with_login);
		appPref = new AppPreferences(this);
		
		btn_logOut = (ImageView) findViewById(R.id.logout_btn);
		btn_painMgmtSpecialities = (Button) findViewById(R.id.painMgmtSpecBtn);
		btn_findPhysian = (Button) findViewById(R.id.find_physician);
		btn_findFacility= (Button) findViewById(R.id.find_facility);
		uploadADocument= (Button) findViewById(R.id.upload_a_document);
		saved_document= (Button) findViewById(R.id.saved_document);
		tv_userName = (TextView)findViewById(R.id.username_text);
		btnChat=(Button) findViewById(R.id.btnChat);
		btnInvite = (ImageView) findViewById(R.id.invite_btn);
		setUserName();

		uploadADocument.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(HomeScreenWithLogin.this,UploadDocument.class);
				startActivity(intent);
			}
		});

		saved_document.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(HomeScreenWithLogin.this,SavedDocuments.class);
				startActivity(intent);
			}
		});

		btnInvite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				PopupMenu popup = new PopupMenu(HomeScreenWithLogin.this, btnInvite);
				popup.getMenuInflater().inflate(R.menu.popup_share_patient, popup.getMenu());

				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						Intent sharingIntent = new Intent(Intent.ACTION_SEND);
						sharingIntent.setType("text/html");
						if(item.getItemId() == R.id.doctor_share){
							sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text_doctor));

						}
						else if(item.getItemId() == R.id.patient_share) {

							sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text_patient));

						}
						else {

							sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text_health_care));

						}
						startActivity(Intent.createChooser(sharingIntent,"Invite using"));
						return true;
					}
				});

				popup.show();



			}
		});
		
btn_search_doctor=(Button)findViewById(R.id.btnSearchDoctor);
		
		btn_search_doctor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(HomeScreenWithLogin.this, SearchDoctor.class);
				startActivity(i);
				
			}
		});
		
		btn_painMgmtSpecialities.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				Intent i = new Intent(HomeScreenWithLogin.this,HospitalsMap1.class);
                appPref.saveMapType(getString(R.string.map_name));
				startActivity(i);
				
			}
		});
		
		btnChat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showChatSelectionDialog();
				
			}
		});
		
		
		btn_findFacility.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(HomeScreenWithLogin.this,FacilityList.class);
				startActivity(i);
				
			}
		});
		btn_findPhysian.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(HomeScreenWithLogin.this,ListBusinessPersons.class);
			
				startActivity(i);
				
			}
		});

		btnSavingCards = (Button) findViewById(R.id.copay_saving_btn);

		btnSavingCards.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(HomeScreenWithLogin.this , ListCopaySavingCardsActivity.class);
				startActivity(i);
			}
		});
		
		btn_logOut.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				
				appPref.clearPref();
				Intent i = new Intent(HomeScreenWithLogin.this,HomeScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(i);
				finish();
				
			}
		});
		
	}
	
	public void setUserName()
	{
		String userLoginInfo = appPref.getUserLoginInfo();
		Gson gson = new Gson();
		loginBeans = gson.fromJson(userLoginInfo, BeansLogin.class);
		tv_userName.setText("Welcome "+loginBeans.getFirst_name()+" "+loginBeans.getLast_name());
	}

    //dialog for showing chat selection
    public void showChatSelectionDialog()
    {
        final ArrayList<String> listChattype = new ArrayList<String>();
        listChattype.add("Chat with Doctor");
        listChattype.add("Chat with Doctor Office");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, listChattype);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Chat type:");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (item == 0) {

                    // TODO Auto-generated method stub
                    Intent i = new Intent(HomeScreenWithLogin.this,RecentChatActivity.class);
                    startActivity(i);

                }
                else if (item == 1) {

                    // TODO Auto-generated method stub
                    Intent i = new Intent(HomeScreenWithLogin.this,RecentOfficeChat.class);
                    startActivity(i);


                }


            }
        });

        Dialog dialogChatType = builder.create();
        dialogChatType.show();
    }




}

