package in.globalsoft.urncr;

import in.globalsoft.beans.BeansHospitalInfo;
import in.globalsoft.beans.BeansHospitalList;
import in.globalsoft.urncr.R;
import in.globalsoft.pojo.DoctorOfficePojo;
import in.globalsoft.pojo.DoctorPojo;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import in.globalsoft.util.ParseInfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class HospitalsMap1 extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener
{
	GoogleMap mGoogleMap;
	Button btn_switch;
	String mapStr="";
	AppPreferences appPref;
	TextView tv_title,tv_info;
	//public static List<HashMap<String, String>> places = null;
	public static BeansHospitalList hospitalListBeans;

	double mLatitude = 28.6018425;
	double mLongitude = 77.352016;
	private LocationClient locationClient;
	LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
	String hospital_url;
	String data = null;
	LinearLayout llBottom;
	TextView tvtitle,tvPhone,tvAdd,tvWaittime,tvAppointtime;
	TextView btnTextChat;
    DoctorOfficePojo messagePojo;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospitals_map1);
		tv_title = (TextView) findViewById(R.id.title_text);
		appPref = new AppPreferences(this);
		tv_title.setText(appPref.getMapType().toUpperCase());
		llBottom=(LinearLayout) findViewById(R.id.llBottom);
		tvtitle=(TextView) findViewById(R.id.title);
		tvPhone=(TextView) findViewById(R.id.phone);
		tvAdd=(TextView) findViewById(R.id.address);
		tvWaittime=(TextView) findViewById(R.id.waitig_time);
		tvAppointtime=(TextView) findViewById(R.id.appointment_time);
		btnTextChat=(TextView) findViewById(R.id.btnTextChat);
		btn_switch = (Button)findViewById(R.id.switch_btn);
		btn_switch.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if(hospitalListBeans !=null &&hospitalListBeans.getHospital_list() !=null&& hospitalListBeans.getHospital_list().size()!=0)
				{
					Intent i= new Intent(HospitalsMap1.this,HospitalList.class);
					startActivity(i);
				}
				else
				{
					Toast.makeText(HospitalsMap1.this, "No nearby hospital found.", Toast.LENGTH_LONG).show();
				}

			}
		});
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        showGPSDisabledAlertToUser();


		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
			// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} 
		else 
		{ // Google Play Services are available

			// Getting reference to the SupportMapFragment
			SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);

			// Getting Google Map
			mGoogleMap = fragment.getMap();
            buildGoogleApiClient();
            createLocationRequest();
            mGoogleApiClient.connect();
		}

	}
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		System.out.println("Connection result::" + arg0);
		locationClient.connect();

	}

    @Override
    public void onConnected(Bundle arg0)
    {
        Location  mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null) {

            prepareUrlForHospitals(mLastLocation);
        }
        startLocationUpdates();
//		Location loc = locationClient.getLastLocation();
//		if(loc !=null)
//			prepareUrlForHospitals(loc);



    }



    @Override
    public void onLocationChanged(Location arg0)
    {
        if(distance(arg0.getLatitude(), arg0.getLongitude(), mLatitude, mLongitude)>200)
            prepareUrlForHospitals(arg0);

    }





    @Override
    protected void onStop() {
        super.onStop();

    }



	private class PlacesTask extends AsyncTask<Void, Void, Void> {


		ProgressDialog pd;

		@Override
		protected void onPreExecute() 
		{
			pd = ProgressDialog.show(HospitalsMap1.this, null, "Loading...");
			pd.show();
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		// Invoked by execute() method of this object
		@Override
		protected Void doInBackground(Void... params)
		{
			try 
			{
				data = Cons.http_connection(hospital_url);
                System.out.println("data::"+data);
				if(data!=null)
				{
					ParseInfo parseInfo = new ParseInfo();
					hospitalListBeans = parseInfo.parseHospitalInfo(data);
				}
				//				PlaceJSONParser placeJsonParser = new PlaceJSONParser();
				//
				//				JSONObject jObject = new JSONObject(data);
				//
				//				/** Getting the parsed data as a List construct */
				//				places = placeJsonParser.parse(jObject);


			}

			catch (Exception e) 
			{
				Log.d("Background Task", e.toString());
			}
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
			myMessage.obj = "hospital_info_task";
			myHandler.sendMessage(myMessage);
			// Clears all the existing markers


			super.onPostExecute(result);

		}

	}
	public void prepareUrlForHospitals(Location location)
	{
		if(location != null)
		{
			mLatitude = location.getLatitude();
			mLongitude = location.getLongitude();
		}

        /*mLatitude = 40.7320203;
        mLongitude = -73.7183638;*/
		LatLng latLng = new LatLng(mLatitude, mLongitude);
		StringBuilder sb = new StringBuilder(
				"http://www.carrxon.com/CarrxonWebServices/ws/filter_registered_hospitals.php?");
		sb.append("lat=" +mLatitude+ "&lon="+mLongitude+"&type="+appPref.getMapType());
		//		sb.append("&radius=5000");
		//		sb.append("&types=hospital");
		//		sb.append("&sensor=true");
		//		sb.append("&key=AIzaSyDafsZSD2kx1I-GPFhp7_KEG9p5w7C2NtI");
		System.out.println(sb);
		hospital_url = sb.toString();


		PlacesTask placesTask = new PlacesTask();

		// Invokes the "doInBackground()" method of the class PlaceTask
		placesTask.execute();

		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

	}

	private Handler myHandler = new Handler() 
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("hospital_info_task"))
			{
				if (!isFinishing()) 
				{

					if((hospitalListBeans == null) || data == null)

					{
                        System.out.println("net available::"+Cons.isNetAvail);
                        System.out.println("list::"+hospitalListBeans);
                        System.out.println("data::"+data);

						Cons.isNetAvail = 0;
						Toast.makeText(HospitalsMap1.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();

					}

					else if(hospitalListBeans.getCode()==200)
					{

						mGoogleMap.clear();
						if(hospitalListBeans.getHospital_list().size() == 0)
						{
							Toast.makeText(HospitalsMap1.this, "No near by hospital found.", Toast.LENGTH_LONG).show();
						}
						else
						{
							for (int i = 0; i < hospitalListBeans.getHospital_list().size(); i++) {

								// Creating a marker
								MarkerOptions markerOptions = new MarkerOptions();
								markerOptions.snippet(String.valueOf(i));



								// Getting a place from the places list
								BeansHospitalInfo hospitalInfoBeans = hospitalListBeans.getHospital_list().get(i);

								// Getting latitude of the place
								double lat = Double.parseDouble(hospitalInfoBeans.getLat());

								// Getting longitude of the place
								double lng = Double.parseDouble(hospitalInfoBeans.getLon());

								// Getting name
								String name = hospitalInfoBeans.getName();

								// Getting vicinity
								String vicinity = hospitalInfoBeans.getVicinity();

								String status =hospitalInfoBeans.getOpen_status();

								System.out.println("address::"+vicinity + "status::" + status+"doctorId::"+hospitalInfoBeans.getDoctor_id());;

								LatLng latLng = new LatLng(lat, lng);

								// Setting the position for the marker
								markerOptions.position(latLng);

								// Setting the title for the marker.
								// This will be displayed on taping the marker
								//markerOptions.title(name + "\n" + vicinity+"\nWaiting time:\nAppointment time");

								if(status.equals(""))
								{

									markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_marker));

									// Placing a marker on the touched position

								}
								else if(status.equals("0"))
								{


									markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.red_marker));

									// Placing a marker on the touched position


								}
								else
								{
									markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.green_marker));
								}
								mGoogleMap.addMarker(markerOptions);
								mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

									@Override
									public boolean onMarkerClick(Marker arg0) {
										// TODO Auto-generated method stub
										final int id = Integer.parseInt(arg0.getSnippet());
										
										llBottom.setVisibility(View.VISIBLE);
										
										tvtitle.setText(hospitalListBeans.getHospital_list().get(id).getName());
										tvWaittime.setText("Waiting time: "+hospitalListBeans.getHospital_list().get(id).getWating_time()+"mins");
										tvAppointtime.setText("\n Appointment time: "+hospitalListBeans.getHospital_list().get(id).getNext_appointment_time());
										tvAdd.setText(hospitalListBeans.getHospital_list().get(id).getVicinity());
										tvPhone.setText( "Phone No:"+hospitalListBeans.getHospital_list().get(id).getPhone());
										
										tvPhone.setOnClickListener(new OnClickListener() {
											
											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method stub
                                                if(hospitalListBeans.getHospital_list().get(id).getPhone() != null && !hospitalListBeans.getHospital_list().get(id).getPhone().equals(""))

                                                {
                                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                    callIntent.setData(Uri.parse("tel:" + hospitalListBeans.getHospital_list().get(id).getPhone()));
                                                    startActivity(callIntent);
                                                }
                                                else
                                                {
                                                    Toast.makeText(HospitalsMap1.this,"Phone number is not registered",Toast.LENGTH_SHORT).show();
                                                }
											}
										});


                                            btnTextChat.setVisibility(View.VISIBLE);
                                        btnTextChat.setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(hospitalListBeans.getHospital_list().get(id).getOpen_status().equals(""))
                                                {
                                                    dialogForDocNotRegistered(hospitalListBeans.getHospital_list().get(id).getPhone());
                                                }
                                                else if(appPref.getLogintype()==-1)
                                                {
                                                    dialogForLoginRegister();
                                                }
                                                else
                                                showChatSelectionDialog(hospitalListBeans.getHospital_list().get(id).getDoctor_id(),hospitalListBeans.getHospital_list().get(id).getName());
                                            }
                                        });
                                        llBottom.setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(hospitalListBeans.getHospital_list().get(id).getOpen_status().equals(""))
                                                {

                                                    Toast.makeText(HospitalsMap1.this,"Hospital is not registered",Toast.LENGTH_SHORT).show();

                                                }
                                                else
                                                {
                                                    Intent i = new Intent(HospitalsMap1.this,HospitalDescription.class);
                                                    i.putExtra("position", id);
                                                    startActivity(i);
                                                }
                                            }
                                        });
										
										return false;
									}
								});

								/*mGoogleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

									@Override
									public void onInfoWindowClick(Marker arg0)
									{
										int id = Integer.parseInt(arg0.getSnippet());
										BeansHospitalInfo hospitalInfoBeans = hospitalListBeans.getHospital_list().get(id);
										String status = hospitalInfoBeans.getOpen_status();

										if(status.equals(""))
										{
											Toast.makeText(HospitalsMap1.this, "Hospital is not registered", Toast.LENGTH_LONG).show();
										}
										else
										{
											Intent i = new Intent(HospitalsMap1.this,HospitalDescription.class);
											i.putExtra("position", id);
											startActivity(i);
										}




									}
								});

								mGoogleMap.setInfoWindowAdapter(new InfoWindowAdapter() 
								{

									public View getInfoWindow(Marker arg0)
									{
										int id = Integer.parseInt(arg0.getSnippet());
										View v = HospitalsMap1.this.getLayoutInflater().inflate(R.layout.marker_info_window, null);
										TextView tv_name = (TextView)v.findViewById(R.id.title);
										TextView tv_address = (TextView)v.findViewById(R.id.address);
										TextView tv_waitingtime = (TextView)v.findViewById(R.id.waitig_time);
										TextView tv_appiontmenttime = (TextView)v.findViewById(R.id.appointment_time);
										TextView tv_phoneNO = (TextView)v.findViewById(R.id.phone);
										tv_name.setText(hospitalListBeans.getHospital_list().get(id).getName());
										tv_address.setText(hospitalListBeans.getHospital_list().get(id).getVicinity());
										tv_phoneNO.setText( "Phone No:"+hospitalListBeans.getHospital_list().get(id).getPhone());
										tv_waitingtime.setText("Waiting time: "+hospitalListBeans.getHospital_list().get(id).getWating_time()+"mins");
										tv_appiontmenttime.setText("Appointment time: "+hospitalListBeans.getHospital_list().get(id).getNext_appointment_time());
										return v;

									}

									public View getInfoContents(Marker arg0) 
									{
										return null;
									}
								});

*/
							}
						}
					}
					else 
					{

						Toast.makeText(HospitalsMap1.this, "Api not working or connection is slow.Please try after some time.", Toast.LENGTH_LONG).show();
					}



				}
			}


		}
	};

    //dialog for showing chat selection
    public void showChatSelectionDialog(final String doctorId, final String doctorName)
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

    DoctorPojo doctorInfo = new DoctorPojo();
                    doctorInfo.setDoctor_id(doctorId);
                    doctorInfo.setDoctor_name(doctorName);

                   Intent i = new Intent(HospitalsMap1.this,ChatActivity.class);
                    i.putExtra("MessagePojo",doctorInfo);
                    startActivity(i);
                    dialog.cancel();
                }
                else if (item == 1) {

                    //if selection is doctor office then hit web service to get data of DoctorOfficePojo
                    if(Cons.isNetworkAvailable(getApplicationContext()))
new GetOfficeInfoWithDoctor(doctorId).execute();
                    else
                        Toast.makeText(getApplicationContext(),"Internet connection is not available. Please try again later.",Toast.LENGTH_SHORT);


                }


            }
        });

       Dialog dialogChatType = builder.create();
        dialogChatType.show();
    }




    private class GetOfficeInfoWithDoctor extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog pd;
        private String doctorId;

        GetOfficeInfoWithDoctor(String doctorId)
        {
            this.doctorId = doctorId;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(HospitalsMap1.this,null,"Loading...");
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String url =Cons.url_getOfficeBydoctorId+doctorId;
            String responseString = Cons.http_connection(url);
            if(responseString != null && !responseString.equals(""))
            {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(responseString);

                Gson gson = new Gson();
                messagePojo = gson.fromJson(obj.getString("message"),DoctorOfficePojo.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(pd != null && pd.isShowing())
                pd.dismiss();
            if(messagePojo != null)
            {
                Intent i = new Intent(HospitalsMap1.this,OfficeChatActivity.class);
                i.putExtra("MessagePojo", messagePojo);
                startActivity(i);

            }
            else
            {
Toast.makeText(HospitalsMap1.this,"Internet connection is not available. Please try again later.",Toast.LENGTH_SHORT);
            }

        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(60000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub

    }

    //returns distance between the two lat and longs in meters
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device and you will bot be able to get nearby doctors. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void dialogForLoginRegister()
    {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(HospitalsMap1.this);
        alt_bld.setMessage("To activate this link you have to register with app or you already registered then login with app").setCancelable(false)
                .setPositiveButton("Register", new DialogInterface.OnClickListener()
                {

                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent i = new Intent(HospitalsMap1.this,RegisterScreen1.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Login", new DialogInterface.OnClickListener()
                {

                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(HospitalsMap1.this, LoginScreen.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        dialog.cancel();
                    }
                    });

        AlertDialog alert = alt_bld.create();
        alert.setTitle("\t" + getString(R.string.app_name));
        //		alert.setIcon(AlertDialog.BUTTON_NEGATIVE);
        alert.show();

    }

    private void dialogForDocNotRegistered(final String phoneNo)
    {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(HospitalsMap1.this);
        alt_bld.setMessage("Provider not available for chat , Please call at the phone number").setCancelable(false)
                .setPositiveButton("Call", new DialogInterface.OnClickListener()
                {

                    public void onClick(DialogInterface dialog, int id)
                    {
                        if(phoneNo != null && !phoneNo.equals(""))

                        {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" +phoneNo));
                            startActivity(callIntent);
                        }
                        else
                        {
                            Toast.makeText(HospitalsMap1.this,"Phone number is not registered",Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alert = alt_bld.create();
        alert.setTitle("\t" + getString(R.string.app_name));
        //		alert.setIcon(AlertDialog.BUTTON_NEGATIVE);
        alert.show();
    }


}
