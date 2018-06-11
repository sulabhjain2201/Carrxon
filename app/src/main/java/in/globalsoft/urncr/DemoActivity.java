/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package in.globalsoft.urncr;

import java.io.IOException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.android.gms.gcm.GoogleCloudMessaging;



public class DemoActivity extends Activity {

  //  TextView mDisplay;
    AsyncTask<Void, Void, Void> mRegisterTask;
    GoogleCloudMessaging gcm;
    String regid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gcm = GoogleCloudMessaging.getInstance(this);
    	
    	 
    	new RegisterBackground().execute();	
        // Make sure the device has the proper dependencies.
        
      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()) {
//            /*
//             * Typically, an application registers automatically, so options
//             * below are disabled. Uncomment them if you want to manually
//             * register or unregister the device (you will also need to
//             * uncomment the equivalent options on options_menu.xml).
//             */
//            /*
//            case R.id.options_register:
//                GCMRegistrar.register(this, SENDER_ID);
//                return true;
//            case R.id.options_unregister:
//                GCMRegistrar.unregister(this);
//                return true;
//             */
//            case R.id.options_clear:
//                mDisplay.setText(null);
//                return true;
//            case R.id.options_exit:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    @Override
//    protected void onDestroy() {
//        if (mRegisterTask != null) {
//            mRegisterTask.cancel(true);
//        }
//        unregisterReceiver(mHandleMessageReceiver);
//        GCMRegistrar.onDestroy(this);
//        super.onDestroy();
//    }

    class RegisterBackground extends AsyncTask<String, String, String>{
    	 
    	@Override
    	protected String doInBackground(String... arg0) {
    	// TODO Auto-generated method stub
    	String msg = "";
    	try {
    	                if (gcm == null) {
    	                    gcm = GoogleCloudMessaging.getInstance(DemoActivity.this);
    	                }
    	                regid = gcm.register(CommonUtilities.SENDER_ID);
    	                msg = "Dvice registered, registration ID=" + regid;
    	                Log.d("111", msg);
    	                ServerUtilities.register(getApplicationContext(), regid);
    	 
    	            } catch (IOException ex) {
    	                msg = "Error :" + ex.getMessage();
    	            }
    	            return msg;
    	        }
    	 
    	@Override
    	        protected void onPostExecute(String msg) {
    	          
    	 
    	        }
    }

}