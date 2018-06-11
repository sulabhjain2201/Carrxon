package in.globalsoft.urncr;


import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import in.globalsoft.urncr.R;

public class HospitalsMap extends FragmentActivity  {
	//GoogleMap mGoogleMap;
	Spinner mSprPlaceType;
	Button btn_switch;
	public static List<HashMap<String, String>> places = null;

	// String[] mPlaceType=null;
	// String[] mPlaceTypeName=null;

	double mLatitude = 0;
	double mLongitude = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospitals_map);
		btn_switch = (Button)findViewById(R.id.switch_btn);

		btn_switch.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if(places.size()!=0)
				{
					Intent i= new Intent(HospitalsMap.this,HospitalList.class);
					startActivity(i);
				}
				else
				{
					Toast.makeText(HospitalsMap.this, "No nearby hospital found.", Toast.LENGTH_LONG).show();
				}

			}
		});

//		int status = GooglePlayServicesUtil
//				.isGooglePlayServicesAvailable(getBaseContext());
//
//		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
//			// not available
//
//			int requestCode = 10;
//			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
//					requestCode);
//			dialog.show();
//
//		} else { // Google Play Services are available
//
//			// Getting reference to the SupportMapFragment
//			SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager()
//					.findFragmentById(R.id.map);
//
//			// Getting Google Map
//			mGoogleMap = fragment.getMap();
//
//			// Enabling MyLocation in Google Map
//		//	mGoogleMap.setMyLocationEnabled(true);
//
//			// Getting LocationManager object from System Service
//			// LOCATION_SERVICE
//			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//			// Creating a criteria object to retrieve provider
//			Criteria criteria = new Criteria();
//
//			// Getting the name of the best provider
//			String provider = locationManager.getBestProvider(criteria, true);
//
//			// Getting Current Location From GPS
//			Location location = locationManager.getLastKnownLocation(provider);
//
//			if (location != null) {
//				onLocationChanged(location);
//				
//				
//			}
//
//			locationManager.requestLocationUpdates(provider, 0, 0, this);
//
//		
//
//			// Creating a new non-ui thread task to download Google place json
//			// data
//			
//		}
//	}
//
//	private String downloadUrl(String strUrl) throws IOException {
//		String data = "";
//		InputStream iStream = null;
//		HttpURLConnection urlConnection = null;
//		try {
//			URL url = new URL(strUrl);
//
//			// Creating an http connection to communicate with url
//			urlConnection = (HttpURLConnection) url.openConnection();
//
//			// Connecting to url
//			urlConnection.connect();
//
//			// Reading data from url
//			iStream = urlConnection.getInputStream();
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(
//					iStream));
//
//			StringBuffer sb = new StringBuffer();
//
//			String line = "";
//			while ((line = br.readLine()) != null) {
//				sb.append(line);
//			}
//
//			data = sb.toString();
//
//			br.close();
//
//		} catch (Exception e) {
//			Log.d("Exception while downloading url", e.toString());
//		} finally {
//			iStream.close();
//			urlConnection.disconnect();
//		}
//
//		return data;
//	}
//
//	/** A class, to download Google Places */
//	private class PlacesTask extends AsyncTask<String, Integer, String> {
//
//		String data = null;
//
//		// Invoked by execute() method of this object
//		@Override
//		protected String doInBackground(String... url) {
//			try {
//				data = downloadUrl(url[0]);
//			} catch (Exception e) {
//				Log.d("Background Task", e.toString());
//			}
//			return data;
//		}
//
//		// Executed after the complete execution of doInBackground() method
//		@Override
//		protected void onPostExecute(String result) {
//			ParserTask parserTask = new ParserTask();
//
//			// Start parsing the Google places in JSON format
//			// Invokes the "doInBackground()" method of the class ParseTask
//			parserTask.execute(result);
//		}
//
//	}
//
//	/** A class to parse the Google Places in JSON format */
//	private class ParserTask extends
//	AsyncTask<String, Integer, List<HashMap<String, String>>> {
//
//		JSONObject jObject;
//		ProgressDialog pd;
//		@Override
//		protected void onPreExecute() 
//		{
//			pd = ProgressDialog.show(HospitalsMap.this, null, "Loading...");
//			pd.show();
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//		}
//		// Invoked by execute() method of this object
//		@Override
//		protected List<HashMap<String, String>> doInBackground(
//				String... jsonData) {
//
//			places = null;
//			PlaceJSONParser placeJsonParser = new PlaceJSONParser();
//
//			try {
//				jObject = new JSONObject(jsonData[0]);
//
//				/** Getting the parsed data as a List construct */
//				places = placeJsonParser.parse(jObject);
//
//			} catch (Exception e) {
//				Log.d("Exception", e.toString());
//			}
//			return places;
//		}
//
//		// Executed after the complete execution of doInBackground() method
//		@Override
//		protected void onPostExecute(List<HashMap<String, String>> list) {
//			if(pd.isShowing())
//			{
//				pd.dismiss();
//			}
//
//			// Clears all the existing markers
//			mGoogleMap.clear();
//
//			for (int i = 0; i < list.size(); i++) {
//
//				// Creating a marker
//				MarkerOptions markerOptions = new MarkerOptions();
//
//				// Getting a place from the places list
//				HashMap<String, String> hmPlace = list.get(i);
//
//				// Getting latitude of the place
//				double lat = Double.parseDouble(hmPlace.get("lat"));
//
//				// Getting longitude of the place
//				double lng = Double.parseDouble(hmPlace.get("lng"));
//
//				// Getting name
//				String name = hmPlace.get("place_name");
//
//				// Getting vicinity
//				String vicinity = hmPlace.get("vicinity");
//
//				LatLng latLng = new LatLng(lat, lng);
//
//				// Setting the position for the marker
//				markerOptions.position(latLng);
//
//				// Setting the title for the marker.
//				// This will be displayed on taping the marker
//				markerOptions.title(name + " : " + vicinity);
//
//				// Placing a marker on the touched position
//				mGoogleMap.addMarker(markerOptions);
//
//			}
//
//		}
//
//	}
//
//	@Override
//	public void onLocationChanged(Location location) {
//		mLatitude = location.getLatitude();
//		mLongitude = location.getLongitude();
//		LatLng latLng = new LatLng(mLatitude, mLongitude);
//		StringBuilder sb = new StringBuilder(
//				"http://www.carrxon.com/CarrxonWebServices/ws/filter_registered_hospitals.php?");
//		sb.append("loc=" +mLatitude+ "&lon="+mLongitude);
////		sb.append("&radius=5000");
////		sb.append("&types=hospital");
////		sb.append("&sensor=true");
////		sb.append("&key=AIzaSyDafsZSD2kx1I-GPFhp7_KEG9p5w7C2NtI");
//		System.out.println(sb);
//		PlacesTask placesTask = new PlacesTask();
//
//		// Invokes the "doInBackground()" method of the class PlaceTask
//		placesTask.execute(sb.toString());
//
//		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
//
//	}
//
//	@Override
//	public void onProviderDisabled(String provider) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//		// TODO Auto-generated method stub
//
//	}
	
	}

}
