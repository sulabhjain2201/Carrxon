package in.globalsoft.urncr;

import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
public class GcmIntentService extends IntentService{
	Context context;
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	public static final String TAG = "GCM Demo";
	public static final String BROADCAST_ACTION = "in.globalsoft.carrxondialysis";
	public GcmIntentService() {
		super("GcmIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();
		String msg = intent.getStringExtra("message");
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);


		if (!extras.isEmpty()) {

			if (GoogleCloudMessaging.
					MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.
					MESSAGE_TYPE_DELETED.equals(messageType)) {
				sendNotification("Deleted messages on server: " +
						extras.toString());
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.
					MESSAGE_TYPE_MESSAGE.equals(messageType)) {
				// This loop represents the service doing some work.
				for (int i=0; i<5; i++) {
					Log.i(TAG, "Working... " + (i+1)
							+ "/5 @ " + SystemClock.elapsedRealtime());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				// Post notification of received message.
				//sendNotification("Received: " + extras.toString());
				JSONObject obj;
				try {
					obj = new JSONObject(msg);
					String id=obj.getString("id").toString();
					String message=obj.getString("chat").toString();
					if((ChatActivity.getInstance()!=null ||OfficeChatActivity.getInstance()!= null) && new AppPreferences(this).getCurrentChatFriendId().equals(id))
						displayLoggingInfo(id,message);
					else {
						sendNotification(message);
						Log.i(TAG, "Received: " + extras.toString());
					}


				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}




			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
	private void sendNotification(String msg) {
		mNotificationManager = (NotificationManager)
				this.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent myintent = new Intent(this, RecentChatActivity.class);
		myintent.putExtra("message", msg);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				myintent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.launcher_icon)
		.setContentTitle(getString(R.string.app_name))
		.setStyle(new NotificationCompat.BigTextStyle()
		.bigText(msg))
		.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}


	private void displayLoggingInfo(String id,String msg) {
		Log.d(TAG, "entered DisplayLoggingInfo");
		Intent intent = new Intent(BROADCAST_ACTION);
		intent.putExtra("id", id);
		intent.putExtra("message", msg);
		sendBroadcast(intent);
	}

}
