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

import static in.globalsoft.urncr.CommonUtilities.SERVER_URL;
import static in.globalsoft.urncr.CommonUtilities.TAG;
import static in.globalsoft.urncr.CommonUtilities.displayMessage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.util.Log;

import in.globalsoft.urncr.R;

/**
 * Helper class used to communicate with the demo server.
 */
public final class ServerUtilities {

    private static final int MAX_ATTEMPTS = 1;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    /**
     * Register this account/device pair within the server.
     *
     * @return whether the registration succeeded or not.
     */
    static boolean register(final Context context, final String regId) {
        Log.i(TAG, "registering device (regId = " + regId + ")");
        String serverUrl = SERVER_URL + regId;
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register it in the
        // demo server. As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
            try {
                displayMessage(context, context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));
               String response = http_connection(serverUrl);
               
                String message = context.getString(R.string.server_registered);
                CommonUtilities.displayMessage(context, message);
                return true;
            } catch (Exception e) {
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(TAG, "Failed to register on attempt " + i, e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return false;
                }
                // increase backoff exponentially
                backoff *= 2;
            }
        }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        CommonUtilities.displayMessage(context, message);
        return false;
    }

    /**
     * Unregister this account/device pair within the server.
     */
//    static void unregister(final Context context, final String regId) {
//        Log.i(TAG, "unregistering device (regId = " + regId + ")");
//        String serverUrl = SERVER_URL + "/unregister";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("regId", regId);
//        try {
//            post(serverUrl, params);
//            GCMRegistrar.setRegisteredOnServer(context, false);
//            String message = context.getString(R.string.server_unregistered);
//            CommonUtilities.displayMessage(context, message);
//        } catch (IOException e) {
//            // At this point the device is unregistered from GCM, but still
//            // registered in the server.
//            // We could try to unregister again, but it is not necessary:
//            // if the server tries to send a message to the device, it will get
//            // a "NotRegistered" error message and should unregister the device.
//            String message = context.getString(R.string.server_unregister_error,
//                    e.getMessage());
//            CommonUtilities.displayMessage(context, message);
//        }
//    }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params request parameters.
     *
     * @throws java.io.IOException propagated from POST.
     */
    public static String http_connection(String url_string) 
	{

		String str = null;
		try 
		{
			//			
			url_string = url_string.replace(" ", "%20");
			URL url = new URL(url_string);

			URLConnection connection = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			StringBuffer buffer = new StringBuffer("");

			String line = "";
			while ((line = br.readLine()) != null) 
			{
				buffer.append(line);
			}
			str = buffer.toString();
			// xpp.setInput(br);
		} 
		catch (Exception ae)
		{
			

		}
		return str;

	}
}
