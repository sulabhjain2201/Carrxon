package in.globalsoft.urncr;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URISyntaxException;

import in.globalsoft.pojo.FileUploadModel;
import in.globalsoft.util.RuntimePermissionsManager;

/**
 * Created by Linchpin66 on 02-07-17.
 */
public class UploadDocument extends AppCompatActivity implements FileUploadModel.ProgressDataListener{
    private static final int REQ_CAMERA_IMAGE = 1;
    private final String TAG = "UploadDocument";
    Spinner spinner;
    EditText edtitle, edDesc;
    TextView attachedFile;
    String filePath = "";
    String category;
    private String fileName="";
    private ProgressDialog progressDialog=null;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_document_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red)));
        actionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\">" + getString(R.string.upload_doc) + "</font>"));
        init();
        if (RuntimePermissionsManager.isPermissionCheckOpen()) {
            if (RuntimePermissionsManager.isBuildSysNeedRequiredPermissions()) {
                    if (RuntimePermissionsManager.hasNeedRequiredPermissions(this)) {
                           RuntimePermissionsManager.requestRequiredPermissions(UploadDocument.this, RuntimePermissionsManager.REQUIRED_PERMISSIONS_REQUEST_CODE);
                      }
            }
            else
            {


            }
        }


    }


    private void init() {
        spinner = (Spinner) findViewById(R.id.category_sppinner);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{
                "Medications List", "Labs","Imaging","BP","Blood sugar","Insurance Card","ID","Sick day Notes","Referrals notes","Other Notes"});
        spinner.setAdapter(adapter);
        edtitle = (EditText) findViewById(R.id.input_title);
        edDesc = (EditText) findViewById(R.id.input_descrition);
        attachedFile= (TextView) findViewById(R.id.attached);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 category=adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onAttach(View view) {

        showDialog(100);

    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setItems(new String[]{"Take a Photo","Select from Folder"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0)
                        {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQ_CAMERA_IMAGE);
                        }
                        else
                            showFileChooser();
                    }
                });
        return builder.create();
    }




    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult ( int requestCode, String[] permissions,
                                             int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (!RuntimePermissionsManager.isRequestPermissionsCode(requestCode)) {
             return;
        }

        if (RuntimePermissionsManager.hasDeniedPermissions(permissions, grantResults)) {
            finish();
        } else {

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    // should the path be here in this string
                    try {

                        filePath = getFilePath(this,uri);

                        if(filePath!=null )
                            attachedFile.setText(filePath);

                        File file = new File(filePath);
                        fileName = file.getName();

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    System.out.print("Path  = " + filePath);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;

            case REQ_CAMERA_IMAGE:

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), photo);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));
                filePath=finalFile.getPath();
                fileName = finalFile.getName();
                attachedFile.setText(filePath);

                  break;

               }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public void onPostData(View view)
    {

        String title=edtitle.getText().toString();
        String desc=edDesc.getText().toString();

         if(title!=null && title.equals("")) {
             Toast.makeText(this,"Please Fill Title",Toast.LENGTH_SHORT).show();
             return;
         }
        if(desc!=null && desc.equals("")) {
            Toast.makeText(this,"Please Fill Description",Toast.LENGTH_SHORT).show();
            return;
        }

        if(category!=null && category.equals("")) {
            Toast.makeText(this,"Please Select Category",Toast.LENGTH_SHORT).show();
            return;
        }

        if(filePath!=null && filePath.equals("")) {
            Toast.makeText(this,"Please Select a file",Toast.LENGTH_SHORT).show();
            return;
        }


        JSONObject params=new JSONObject();
        try {
            params.put("user_id","100");
            params.put("doc_title",title);
            params.put("doc_description",desc);
            params.put("doc_category",category);
            params.put("doc_time",System.currentTimeMillis());
            params.put("attach_name",fileName);
            params.put("file",filePath);

            FileUploadModel fileUploadModel=new FileUploadModel(filePath,params,this);
            fileUploadModel.startFileUploadTask();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action buttons
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void showProgress(String data) {
        attachedFile.setText(data);
    }

    @Override
    public void showProgress() {
         progressDialog=new ProgressDialog(this);
         progressDialog.setTitle("NCR Carrxon");
         progressDialog.setMessage("Uploading....");
         progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         progressDialog.show();
    }

    @Override
    public void hideProgress() {
     if(progressDialog!=null && progressDialog.isShowing())
         progressDialog.dismiss();
    }

    @Override
    public void showOutput(JSONObject response) {
          if(response!=null )
          {
              try {
                  if(response.getString("code").equals("200"))
                  {
                      Toast.makeText(this,response.getString("message"),Toast.LENGTH_LONG).show();
                      Intent i = new Intent(UploadDocument.this,SavedDocuments.class);
                      startActivity(i);
                      finish();
                  }
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }
    }
}
