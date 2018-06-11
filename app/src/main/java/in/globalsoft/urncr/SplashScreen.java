package in.globalsoft.urncr;


import in.globalsoft.urncr.R;
import in.globalsoft.util.BackgroungTask;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class SplashScreen extends Activity 
{

	ImageView iv_logo;
	GoogleCloudMessaging gcm;
	String regId;
	private PermissionCallBack permissionCallBack;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		iv_logo = (ImageView)findViewById(R.id.logo);
		TranslateAnimation more_animation = new TranslateAnimation(-400, 0, 0, 0);
		more_animation.setDuration(1500);
		more_animation.setStartOffset(0);
		iv_logo.startAnimation(more_animation);
		
		//add alarm reciever for sending online status on every 5 minutes
		setAlarm();

		new BackgroungTask(this).execute();
	}
	private void setAlarm()
	{
		Intent alarmIntent = new Intent(this, UserOnlineInfo.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		int interval = 5*60*1000;

		manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

	}
	/**
	 * method to set Permission CallBack
	 * @param permissionCallBack
	 */
	public void setPermissionCallBack(PermissionCallBack permissionCallBack) {
		this.permissionCallBack = permissionCallBack;
	}

	/**
	 * Inter face to get Permission result CallBack
	 */
	public interface PermissionCallBack {
		public void permissionCallBack(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

		permissionCallBack.permissionCallBack(requestCode, permissions, grantResults);
	}






}
