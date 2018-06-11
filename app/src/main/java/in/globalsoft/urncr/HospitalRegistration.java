package in.globalsoft.urncr;



import java.io.File;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import in.globalsoft.urncr.R;

public class HospitalRegistration extends Activity 
{

	Button btn_next,btn_captureHospitalImage,btn_chooseAnotherImage;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	AlertDialog dialog_upload_bill;
	private Uri mImageCaptureUri;
	Bitmap bitmap = null;
	Bitmap photo = null;
	ImageView iv_hospitalImage;
	public static String path = "";
	
	EditText et_hospitalName,et_hospitalPhone,et_hospitalAddress,et_hospitalEmail;
	public static String str_hospitalName="",str_hospitalPhone="",str_hospitalAddress="",str_hospitalEmail="";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital_registration);
		btn_next = (Button) findViewById(R.id.next_step);
		et_hospitalName = (EditText) findViewById(R.id.search_hospitalName);
		et_hospitalPhone = (EditText) findViewById(R.id.hospital_phone);
		et_hospitalAddress = (EditText) findViewById(R.id.hospital_address);
		et_hospitalEmail = (EditText) findViewById(R.id.hospital_email);
		
		btn_captureHospitalImage = (Button)findViewById(R.id.hospital_image_button);
		btn_chooseAnotherImage = (Button)findViewById(R.id.hospital_image_button_another);
		iv_hospitalImage = (ImageView) findViewById(R.id.hospital_image_view);
		btn_next.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
			checkForBlank();
				
			}
		});
		btn_captureHospitalImage.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				create_dialog_for_upload_bill();
				dialog_upload_bill.show();
				
				
			}
		});
		btn_chooseAnotherImage.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) 
			{
				create_dialog_for_upload_bill();
				dialog_upload_bill.show();
				
				
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

					dialog.cancel();
				} else {
					Intent intent = new Intent();

					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);

					startActivityForResult(Intent.createChooser(intent,
							"Complete action using"), PICK_FROM_FILE);
				}
			}
		});

		dialog_upload_bill = builder.create();

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;

		

		if (requestCode == PICK_FROM_FILE) {
			mImageCaptureUri = data.getData();
			path = getRealPathFromURI(mImageCaptureUri); // from Gallery

			if (path == null)
				path = mImageCaptureUri.getPath(); // from File Manager

			if (path != null) {
				bitmap = BitmapFactory.decodeFile(path);
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
				bitmap = Bitmap.createScaledBitmap(bitmap, 130, 130, false);
			}
			catch(Exception ae)
			{
				ae.printStackTrace();
			}
			//			bitmap = BitmapFactory.decodeFile(path);
			// bitmap=doGreyscale(bitmap);
		}

		iv_hospitalImage.setVisibility(View.VISIBLE);
		iv_hospitalImage.setImageBitmap(bitmap);
		btn_captureHospitalImage.setVisibility(View.GONE);
		btn_chooseAnotherImage.setVisibility(View.VISIBLE);


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
	
	public void checkForBlank()
	{
		str_hospitalName = et_hospitalName.getText().toString();
		str_hospitalEmail = et_hospitalEmail.getText().toString();
		str_hospitalAddress = et_hospitalAddress.getText().toString();
		str_hospitalPhone = et_hospitalPhone.getText().toString();
		
		if(str_hospitalName.equals("") || str_hospitalEmail.equals("") || str_hospitalAddress.equals("") || str_hospitalPhone.equals(""))
		{
			Toast.makeText(HospitalRegistration.this, "All the fields are mandatory.", Toast.LENGTH_LONG).show();
			
		}
		else
		{
			System.out.println("str_hospitalAddress::"+str_hospitalAddress);
			Intent i = new Intent(HospitalRegistration.this,AddClinicShedule.class);
			startActivity(i);
			
		}
	}


	

}
