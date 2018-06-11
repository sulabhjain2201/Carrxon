package in.globalsoft.pojo;

import android.net.Uri;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import in.globalsoft.util.Cons;


public class FileUploadModel  {
	
	private long totalFileSize = 0;
	private String filePath;
	private JSONObject paramObject;
	private ProgressDataListener listener;
	public interface ProgressDataListener
	{
		void showProgress(String data);

		void showProgress();

		void hideProgress();

		void showOutput(JSONObject response);
	}

	public FileUploadModel(String filePath, JSONObject params, ProgressDataListener listener) {
		this.filePath = filePath;
		paramObject = params;
		this.listener=listener;
	}
	
	public void startFileUploadTask() {
		new UploadFileToServer().execute();
	}

	private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {			
			super.onPreExecute();
			listener.showProgress();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			String uploadprogress = progress[0] + "%";
			listener.showProgress(uploadprogress);
		}

		@Override
		protected String doInBackground(Void... params) {
			return uploadFile();
		}

		@SuppressWarnings("deprecation")
		private String uploadFile() {
			String responseString = null;

			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpPost httppost = new HttpPost(Cons.DOC_UPLOAD_URL);

			try {
				MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				try {
					entity.addPart("user_id", new StringBody("100"));
					entity.addPart("doc_title", new StringBody(paramObject.getString("doc_title")));
					entity.addPart("doc_description", new StringBody(paramObject.optString("doc_description")));
					entity.addPart("doc_category", new StringBody(paramObject.optString("doc_category")));
					entity.addPart("doc_time", new StringBody(paramObject.optString("doc_time")));
					entity.addPart("attach_name", new StringBody(paramObject.optString("attach_name")));

					Uri uri = Uri.parse(filePath);
					File sourceFile = new File(uri.getPath());
					entity.addPart("file", new FileBody(sourceFile));


				} catch (JSONException e) {
					e.printStackTrace();
				}

				totalFileSize = entity.getContentLength();
				httppost.setEntity(entity);

				// Making server call
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity r_entity = response.getEntity();

				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == 200) {
					// Server response
					//publishProgress(100);
					responseString = EntityUtils.toString(r_entity);
				} else {
					responseString = "Error occurred! Http Status Code: " + statusCode;
					responseString = EntityUtils.toString(r_entity);
				}
				
				
			} catch (ClientProtocolException e) {
				responseString = e.toString();
			} catch (IOException e) {
				responseString = e.toString();
			}

			return responseString;
		}

		@Override
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);
			listener.hideProgress();
			if(result != null && !result.contains("Error occurred! Http Status Code:")) {
				JSONObject response = null;//parseSavePostData(result);
				try {
					response = new JSONObject(result);
					listener.showOutput(response);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
