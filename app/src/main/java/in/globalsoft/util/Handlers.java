package in.globalsoft.util;

import in.globalsoft.urncr.DisplayDoctorInfo;
import in.globalsoft.urncr.DoctorOfficeHomeActivity;
import in.globalsoft.urncr.HomeScreen;
import in.globalsoft.urncr.HomeScreenWithLogin;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.urncr.R;
import in.globalsoft.urncr.SplashScreen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class Handlers extends Handler implements SplashScreen.PermissionCallBack{
	private static final int MY_PERMISSIONS_REQUEST = 1;
	Context mContext;
	Activity act;
	AppPreferences appPref;

	private String waitingTask;

	public Handlers(Context mContext) {
		this.mContext = mContext;
		act = (Activity) mContext;
		appPref = new AppPreferences(mContext);
		((SplashScreen)mContext).setPermissionCallBack(this);
	}
	public void handleMessage(Message msg) {

		waitingTask = msg.obj.toString();
		//call method to get permission
		getRequiredPermission();


	}

	public void createNextScreen() {
		if (waitingTask.equalsIgnoreCase("waiting_task")) {
			if (appPref.getLoginState() == 0) {
				Intent i = new Intent(mContext, HomeScreen.class);
				mContext.startActivity(i);
			} else if (appPref.getLogintype() == 0) {
				Intent i = new Intent(mContext, HomeScreenWithLogin.class);
				mContext.startActivity(i);
			} else if (appPref.getLogintype() == 1) {
				Intent i = new Intent(mContext, DisplayDoctorInfo.class);
				mContext.startActivity(i);
			} else if (appPref.getLogintype() == 2) {
				Intent i = new Intent(mContext, DoctorOfficeHomeActivity.class);
				mContext.startActivity(i);
			} else {
				Intent i = new Intent(mContext, HomeScreen.class);
				mContext.startActivity(i);
			}
			act.finish();
		}
	}

	public void getRequiredPermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			// Here, thisActivity is the current activity
			if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
					|| ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
					|| ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
					|| ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
					|| ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
					|| ContextCompat.checkSelfPermission(mContext, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED
					|| ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
					|| ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

				// No explanation needed, we can request the permission.
				ActivityCompat.requestPermissions((SplashScreen) mContext,
						new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
								Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,
								Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.GET_ACCOUNTS,
								Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE},
						MY_PERMISSIONS_REQUEST);

			} else {
				//call method for create next screen
				createNextScreen();
			}
		} else {

			createNextScreen();
		}
	}

	@Override
	public void permissionCallBack(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
						&& grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED
						&& grantResults[4] == PackageManager.PERMISSION_GRANTED && grantResults[5] == PackageManager.PERMISSION_GRANTED
						&& grantResults[6] == PackageManager.PERMISSION_GRANTED && grantResults[7] == PackageManager.PERMISSION_GRANTED) {
					//Permission Granted
					createNextScreen();
				} else {
					// Permission Denied
					Toast.makeText(mContext, mContext.getString(R.string.PERMISSION_DENIED), Toast.LENGTH_SHORT)
							.show();
				}
				break;

		}

	}
}
