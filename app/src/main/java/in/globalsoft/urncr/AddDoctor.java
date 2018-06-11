package in.globalsoft.urncr;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.globalsoft.beans.BeansAddDoctorResponse;
import in.globalsoft.beans.BeansDoctorGoogleAddressList;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import in.globalsoft.util.ImageFilePath;

public class AddDoctor extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener
{

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private static final int MY_PERMISSIONS_REQUEST_IMAGE_CAPTURE = 3;
    private static final int MY_PERMISSIONS_REQUEST_GET_IMAGE_FROM_GALLERY = 4;
    AlertDialog dialog_upload_bill;
    private Uri mImageCaptureUri;
    Bitmap bitmap = null;
    Bitmap photo = null;
    ImageButton iv_doctor_image;
    TextView et_doctorName,et_doctorPhone,et_doctorAddress;
    EditText et_doctor_email,et_doctorState;
    String responseString;
    BeansAddDoctorResponse doctorInfoBeans;
    BeansDoctorGoogleAddressList addressList;
    public static String path = "";
    int serverResponseCode;
    String serverResponseMessage;
    String ts;
    Button btn_addDoctor;
    RelativeLayout layout_speciality;
    TextView tv_speciality;
    RelativeLayout layout_address;
    TextView tv_address;
    AlertDialog dialog_speciality,dialog_address;
    public static String str_speciality="";
    AppPreferences appPref;
    public static String str_doctorName="",str_doctorAddress="",str_doctorEmail="",str_doctorPhone="";
    double mLatitude = 0;
    double mLongitude = 0;
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        appPref = new AppPreferences(this);
        iv_doctor_image = (ImageButton) findViewById(R.id.doctor_image);
        et_doctorState = (EditText) findViewById(R.id.doctor_state);

        tv_speciality = (TextView) findViewById(R.id.doctor_speciality_text);
        layout_speciality = (RelativeLayout) findViewById(R.id.doctor_speciality_layout);

        tv_address = (TextView) findViewById(R.id.doctor_arrow_txt);
        layout_address = (RelativeLayout) findViewById(R.id.doctor_address_layout);

        et_doctorName = (TextView) findViewById(R.id.doctor_name);

        et_doctorPhone = (TextView) findViewById(R.id.doctor_phone);

        et_doctor_email = (EditText) findViewById(R.id.doctor_email);


        btn_addDoctor = (Button) findViewById(R.id.add_doctor_btn);

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
            buildGoogleApiClient();
            createLocationRequest();
        }
        iv_doctor_image.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                create_dialog_for_upload_bill();
                dialog_upload_bill.show();


            }
        });

        btn_addDoctor.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                checkForBlanks();

            }
        });

        layout_speciality.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(mLatitude == 0)
                {
                    Toast.makeText(AddDoctor.this, "Current location is not available.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    dialogSpeciality();
                    dialog_speciality.show();
                }

            }
        });

        layout_speciality.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(et_doctorState.equals(""))
                {
                    Toast.makeText(AddDoctor.this, "Enter City First", Toast.LENGTH_LONG).show();
                }
                else
                {
                    dialogSpeciality();
                    dialog_speciality.show();
                }

            }
        });
        layout_address.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(addressList == null)
                {
                    Toast.makeText(AddDoctor.this, "Please select speciality again as addresses could not get because of slow connection..", Toast.LENGTH_LONG).show();
                }
                else if(str_speciality.equals(""))
                {
                    Toast.makeText(AddDoctor.this, "Enter City and Speciality", Toast.LENGTH_LONG).show();
                }
                else
                {
                    dialog_address.show();
                }

            }
        });
    }


    public void create_dialog_for_upload_bill()
    {
        final String[] items = new String[] { "From Camera", "From SD Card" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {

                    checkImageCapturePermission();
                    dialog.cancel();
                } else {
                    checkGetImageFromGalleryPermission();

                }
            }
        });

        dialog_upload_bill = builder.create();

    }

    /**
     * method to capture image
     */
    public void imageCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment
                .getExternalStorageDirectory(), "carrxon_"
                + String.valueOf(System.currentTimeMillis())
                + ".jpg");
        mImageCaptureUri = Uri.fromFile(file);

        try {
            intent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    mImageCaptureUri);
            intent.putExtra("return-data", true);

            startActivityForResult(intent, PICK_FROM_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method to check Image Capture permission
     */

    public void checkImageCapturePermission() {
        if (Build.VERSION.SDK_INT >= 23){
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(AddDoctor.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(AddDoctor.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_IMAGE_CAPTURE);

            }else{
                //call method for get Location
                imageCapture();
            }
        }else {

            imageCapture();
        }
    }

    /**
     * method for get Image from Gallery
     */
    public void getImageFromGallery() {
        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent,
                "Complete action using"), PICK_FROM_FILE);
    }
    /**
     * method to check Get Image from Gallery permission
     */

    public void checkGetImageFromGalleryPermission() {
        if (Build.VERSION.SDK_INT >= 23){
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(AddDoctor.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_GET_IMAGE_FROM_GALLERY);

            }else{
                //call method for get Location
                getImageFromGallery();
            }
        }else {

            getImageFromGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_IMAGE_CAPTURE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission Granted
                    imageCapture();
                } else {
                    // Permission Denied
                    Toast.makeText(AddDoctor.this, getString(R.string.IMAGE_CAPTURE_PERMISSION_DENIED), Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case MY_PERMISSIONS_REQUEST_GET_IMAGE_FROM_GALLERY:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission Granted
                    getImageFromGallery();
                } else {
                    // Permission Denied
                    Toast.makeText(AddDoctor.this, getString(R.string.GET_IMAGE_FROM_GALLERY_PERMISSION_DENIED), Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;



        if (requestCode == PICK_FROM_FILE) {
            mImageCaptureUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageCaptureUri);

                path = ImageFilePath.getPath(this, data.getData());


//                path = getRealPathFromURI(mImageCaptureUri); // from Gallery
//
//                if (path == null)
//                    path = mImageCaptureUri.getPath(); // from File Manager
//
//                if (path != null) {
//                    bitmap = BitmapFactory.decodeFile(path);
//                }
                if (bitmap != null) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 140, 140, false);
                }
                //  }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            path = mImageCaptureUri.getPath();



            try {

                //				if (photo != null) {
                //					photo.recycle();
                //				}

                //				  photo = BitmapFactory.decodeFile(path);
                InputStream stream = getContentResolver().openInputStream(
                        mImageCaptureUri);


                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inSampleSize = 4;
                photo = BitmapFactory.decodeStream(stream, null, options );
                stream.close();

                ExifInterface exif = new ExifInterface(path);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                }
                else if (orientation == 3) {
                    matrix.postRotate(180);
                }
                else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                bitmap = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
                bitmap = Bitmap.createScaledBitmap(bitmap, 140, 140, false);
            }
            catch(Exception ae)
            {
                ae.printStackTrace();
            }
            //			bitmap = BitmapFactory.decodeFile(path);
            // bitmap=doGreyscale(bitmap);
        }

        //		iv_hospitalImage.setVisibility(View.VISIBLE);
        iv_doctor_image.setImageBitmap(bitmap);
        //		btn_captureHospitalImage.setVisibility(View.GONE);
        //		btn_chooseAnotherImage.setVisibility(View.VISIBLE);


    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);

        if (cursor == null)
            return null;

        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public class AddDoctorTask extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pd;
        Context con;

        public AddDoctorTask(Context con)
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
            String url = "";
            String url_checkin="";
            String msg = "";

            url = Cons.url_add_doctor_info+"doctor_name="+et_doctorName.getText().toString()
                    +"&doctor_speciality="+str_speciality
                    +"&doctor_phone="+et_doctorPhone.getText().toString()
                    +"&doctor_address="+et_doctorAddress.getText().toString()
                    +"&doctor_email="+et_doctor_email.getText().toString()
                    +"&hospital_id="+appPref.getDoctorId();
            System.out.println("url:"+url);

            responseString = Cons.http_connection(url);
            System.out.println(responseString);
            Gson gson = new Gson();
            doctorInfoBeans = gson.fromJson(responseString, BeansAddDoctorResponse.class);
            msg = "doctor_info";
            if(doctorInfoBeans !=null && doctorInfoBeans.getCode()==200)
            {


                msg = "doctor_image";
                upload_image(doctorInfoBeans.getDoctor_id());



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
            myMessage.obj = "add_doctor_complete";
            myHandler.sendMessage(myMessage);
            super.onPostExecute(result);

        }

    }
    private Handler myHandler = new Handler()
    {

        public void handleMessage(Message msg)
        {


            if (msg.obj.toString().equalsIgnoreCase("add_doctor_complete"))
            {
                if (!isFinishing())
                {
                    if(msg.equals("doctor_info"))
                    {
                        if(doctorInfoBeans == null || Cons.isNetAvail==1)
                        {
                            Cons.isNetAvail = 0;
                            Toast.makeText(AddDoctor.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(AddDoctor.this, doctorInfoBeans.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    else
                    {

                        if((serverResponseCode == 0)||Cons.isNetAvail==1)

                        {

                            Cons.isNetAvail = 0;
                            Toast.makeText(AddDoctor.this, "Doctor added but but Image is not added.", Toast.LENGTH_LONG).show();
                        }

                        else if(serverResponseMessage.equals("OK"))
                        {

                            Toast.makeText(AddDoctor.this, "Doctor added successfully.", Toast.LENGTH_LONG).show();

                        }
                        else
                        {

                            Toast.makeText(AddDoctor.this, serverResponseMessage, Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }
            else if(msg.obj.toString().equalsIgnoreCase("doctor_address_list"))
            {
                if(addressList == null )
                {
                    Cons.isNetAvail = 0;
                    Toast.makeText(AddDoctor.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
                }
                else if(addressList.getCode()==200)
                {
                    List<String> addresslist = new ArrayList<String>();
                    List<String> namelist = new ArrayList<String>();
                    List<String> phonelist = new ArrayList<String>();
                    for(int i=0;i<addressList.getClinic_addresses_list().size();i++)
                    {
                        addresslist.add(addressList.getClinic_addresses_list().get(i).getAddress());
                        namelist.add(addressList.getClinic_addresses_list().get(i).getName());
                        phonelist.add(addressList.getClinic_addresses_list().get(i).getPhone());
                    }
                    dialogAddresses(namelist,phonelist,addresslist);


                }
                else
                {
                    Toast.makeText(AddDoctor.this, addressList.getMessage(), Toast.LENGTH_LONG).show();
                }
            }


        }
    };

    public void upload_image(String doctor_id)
    {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        DataInputStream inputStream = null;

        String pathToOurFile = path;
        String urlServer = Cons.url_add_credit_image+"doctor_id="+doctor_id;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        try
        {
            System.out.println(pathToOurFile);
            FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );

            URL url = new URL(urlServer);
            connection = (HttpURLConnection) url.openConnection();

            // Allow Inputs & Outputs
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Enable POST method
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

            outputStream = new DataOutputStream( connection.getOutputStream() );
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            Long tsLong = System.currentTimeMillis();
            ts = tsLong.toString();
            outputStream.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\""+ts+".jpg"+"\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0)
            {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            String rs= http_connect(connection);

            // Responses from the server (code and message)
            serverResponseCode = connection.getResponseCode();
            serverResponseMessage = connection.getResponseMessage();
            System.out.println("response:"+serverResponseMessage);

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Cons.isNetAvail = 1;
        }
    }

    public static String http_connect(HttpURLConnection con)
    {

        String str = null;
        try
        {
            //

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            StringBuffer buffer = new StringBuffer("");

            String line = "";
            while ((line = br.readLine()) != null)
            {
                buffer.append(line);
            }
            str = buffer.toString();
            System.out.println(str);
            // xpp.setInput(br);
        }
        catch (Exception ae)
        {
            //isNetAvail = 1;

        }
        return str;

    }

    public void dialogSpeciality()
    {
        final String doctors_array[]= {"Urgent Care Centers","Acupuncturists", "Allergists", "Audiologists", "Cardiologists", "Chiropractors", "Colorectal Surgeons", "Dentists", "Dermatologists", "Dietitians", "Ear, Nose & Throat Doctors", "Emergency Medicine Physicians", "Endocrinologists", "Endodontists", "Eye Doctors", "Family Physicians", "Gastroenterologists", "Hand Surgeons", "Hearing Specialists", "Hematologists", "Infectious Disease Specialists", "Infertility Specialists", "Internists", "Naturopathic Doctors", "Nephrologists", "Neurologists", "Neurosurgeons", "Nurse Practitioners", "Nutritionists", "OB-GYNs", "Oncologists", "Ophthalmologists", "Optometrists", "Oral Surgeons", "Orthodontists", "Orthopedic Surgeons", "Pain Management Specialists", "Pediatric Dentists", "Pediatricians", "Periodontists", "Physiatrists", "Physical Therapists", "Plastic Surgeons", "Podiatrists", "Doctors", "Prosthodontists", "Psychiatrists", "Psychologists", "Psychotherapists", "Pulmonologists", "Radiologists", "Rheumatologists", "Sleep Medicine Specialists", "Sports Medicine Specialists", "Surgeons", "Therapists / Counselors", "Travel Medicine Specialists", "Urologists","Primary Care Doctors","Primary Care Centers","Suboxone doctors","Lab Services","Dialysis","Pharmacy"};
        final ArrayList<String> listSpeciality = new ArrayList<String>(Arrays.asList(doctors_array));;
//		listSpeciality.add("Physician");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, listSpeciality);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Doctor Specialty");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (item == 0) {


                    dialog.cancel();
                }
                else if (item == 1) {


                    dialog.cancel();

                }
                else if (item == 2) {


                    dialog.cancel();

                }
                else if (item == 3) {


                    dialog.cancel();

                }

                tv_speciality.setText(listSpeciality.get(item));
                str_speciality = String.valueOf(item+1);
                if(Cons.isNetworkAvailable(AddDoctor.this))
                {
                    new GetDoctorAddressesTask(AddDoctor.this).execute();
                }
                else
                    Cons.showDialog(AddDoctor.this, "Carrxon", "Internet connection is not available.", "OK");

            }
        });

        dialog_speciality = builder.create();
    }

    public void dialogAddresses(final List<String> nameList,final List<String> phoneList,final List<String> addresslist)
    {




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, addresslist);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Doctor Addresses");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (item == 0) {


                    dialog.cancel();
                }
                else if (item == 1) {


                    dialog.cancel();

                }
                else if (item == 2) {


                    dialog.cancel();

                }
                else if (item == 3) {


                    dialog.cancel();

                }

                tv_address.setText(addresslist.get(item));
                et_doctorName.setText(nameList.get(item));
                et_doctorPhone.setText(phoneList.get(item));
                str_doctorAddress = addresslist.get(item);
                str_doctorName = et_doctorName.getText().toString();
                str_doctorPhone = et_doctorPhone.getText().toString();

            }
        });

        dialog_address = builder.create();
    }


    public void checkForBlanks()
    {



        str_doctorEmail = et_doctor_email.getText().toString();

        if(str_doctorName.equals("") || str_doctorAddress.equals("") || str_doctorEmail.equals("") || str_doctorPhone.equals("") || str_speciality.equals("") )
        {
            Toast.makeText(AddDoctor.this, "All the fields are neccessary.", Toast.LENGTH_LONG).show();
        }
        else if(!Cons.isValidEmail(str_doctorEmail))
        {
            Toast.makeText(AddDoctor.this, "Please fill correct email", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent i = new Intent(AddDoctor.this,HospitalRegistration2.class);
            startActivity(i);
        }
    }


    public class GetDoctorAddressesTask extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pd;
        Context con;

        public GetDoctorAddressesTask(Context con)
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
            String url = "";
            String url_checkin="";
            String msg = "";
            url = Cons.url_doctorAddress+"lat=" +mLatitude+ "&lon="+mLongitude+"&query="+Uri.encode(tv_speciality.getText().toString());
            System.out.println("url:"+url);

            responseString = Cons.http_connection(url);
            if(responseString !=null)
                System.out.println(responseString);
            Gson gson = new Gson();
            addressList = gson.fromJson(responseString, BeansDoctorGoogleAddressList.class);
            // System.out.println(addressList);

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
            myMessage.obj = "doctor_address_list";
            myHandler.sendMessage(myMessage);
            super.onPostExecute(result);

        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub
        System.out.println("Connection result::" + arg0);


    }

    @Override
    public void onConnected(Bundle arg0)
    {
        Location  mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            mLatitude = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();
        }
        startLocationUpdates();

    }



    @Override
    public void onLocationChanged(Location arg0)
    {
        //prepareUrlForHospitals(arg0);
        mLatitude = arg0.getLatitude();
        mLongitude = arg0.getLongitude();

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
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
        mGoogleApiClient.connect();
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

}
