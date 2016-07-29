package com.proschoolonline.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.proschoolonline.components.AppButton;
import com.proschoolonline.components.AppEditText;
import com.proschoolonline.mob.BuildConfig;
import com.proschoolonline.mob.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * @purpose this class is used to show form popup
 * @author ankit
 *
 */
@EActivity(R.layout.form_alert)
public class FormAlertActivity extends AppCompatActivity implements Validator.ValidationListener{
	
	/*@ViewById(R.id.wvForm)
	WebView webview;*/

	@ViewById(R.id.toolbar)
	Toolbar toolbar;

	@ViewById(R.id.input_layout_email)
	TextInputLayout input_layout_email;

	@ViewById(R.id.input_layout_phone)
	TextInputLayout input_layout_phone;

	@ViewById(R.id.input_layout_name)
	TextInputLayout input_layout_name;

	@ViewById(R.id.input_layout_city)
	TextInputLayout input_layout_city;

	@NotEmpty
	@Email
	@ViewById(R.id.edtEmail)
	AppEditText edtEmail;

	@NotEmpty
	@ViewById(R.id.edtPhone)
	AppEditText edtPhone;

	@NotEmpty
	@ViewById(R.id.edtName)
	AppEditText edtName;

	@NotEmpty
	@ViewById(R.id.edtCity)
	AppEditText edtCity;

	@ViewById(R.id.btnSubmit)
	AppButton btnSubmit;

	Validator validator;
	private ProgressDialog progressDialog;
	Context context = this;

	@AfterViews
	void afterViews(){
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setIcon(R.mipmap.app_icon);

		validator = new Validator(this);
		validator.setValidationListener(this);

		/*WebSettings webSetting = webview.getSettings();
		webSetting.setJavaScriptEnabled(true);

		webview.addJavascriptInterface(new WebAppInterface(getApplicationContext()), "Android");

		String htmlFilename = "form.html";
		AssetManager mgr = getAssets();
		try {
			InputStream in = mgr.open(htmlFilename, AssetManager.ACCESS_BUFFER);
			String htmlContentInStringFormat = StreamToString(in);
			Log.v("File_String",htmlContentInStringFormat+"---");
			in.close();

			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
				webview.loadDataWithBaseURL(null, htmlContentInStringFormat, "text/html", "utf-8", null);
			}else{
				webview.loadData(htmlContentInStringFormat, "text/html", "utf-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	@Click(R.id.btnSubmit)
	void onClickButton(){
		validator.validate();
	}

	@Override
	public void onValidationSucceeded() {
		showProgressDialog();
	}

	@Override
	public void onValidationFailed(List<ValidationError> errors) {
		for (ValidationError error : errors) {
			View view = error.getView();
			String message = error.getCollatedErrorMessage(this);

			// Display error messages ;)
			if (view instanceof EditText) {
				((EditText) view).setError(message);
			} else {
				Toast.makeText(this, message, Toast.LENGTH_LONG).show();
			}
		}
	}


	@UiThread
	void showProgressDialog() {
		// TODO Auto-generated method stub
		progressDialog = ProgressDialog.show(context,"", "Please Wait...");
		progressDialog.show();
		postData();

	}

	@Background
	void postData() {

		HashMap<String, String> postDataParams  = new HashMap<String, String>();
		postDataParams.put("FirstName",edtName.getText().toString());
		postDataParams.put("EmailAddress",edtEmail.getText().toString());
		postDataParams.put("mx_City",edtCity.getText().toString());
		postDataParams.put("Phone",edtPhone.getText().toString());

		postDataParams.put("SourceCampaign","Android application");
		postDataParams.put("Source","Mobile App");
		postDataParams.put("MXHOrgCode","630");
		postDataParams.put("MXHLandingPageId","8848e12b-47f4-11e6-b61b-22000aa8e760");
		postDataParams.put("MXHFormBehaviour","0");
		postDataParams.put("MXHFormDataTransfer","0");
		postDataParams.put("MXHRedirectUrl","http://www.proschoolonline.com/cima-course");
		postDataParams.put("MXHAsc","50");
		postDataParams.put("MXHPageTitle","User Information");
		postDataParams.put("MXHOutputMessagePosition","0");
		postDataParams.put("MXHIsExternallyUsed","1");
		String result = performPostCall(postDataParams);
		dismissProgressDialog(result);
	}

	@UiThread
	void dismissProgressDialog(String result) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		Toast.makeText(context, getResources().getString(R.string.thanks), Toast.LENGTH_SHORT).show();
		finish();
	}

	public String performPostCall(HashMap<String, String> postDataParams) {

		URL url;
		String response = "";
		try {
			url = new URL(BuildConfig.formUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			//conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "multipart/form-data");
			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(os, "UTF-8"));
			String data = getPostDataString(postDataParams);
			Log.v("FINALRESULT", data+"<<----");
			writer.write(data);

			writer.flush();
			writer.close();
			os.close();
			int responseCode=conn.getResponseCode();
			if (responseCode == HttpsURLConnection.HTTP_OK) {
				Log.v("FINALRESULT","<<---HTTP_OK---->>");
				String line;
				BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line=br.readLine()) != null) {
					response+=line;
				}
			}
			else {
				Log.v("FINALRESULT","<<---HTTP_ERROR---->>");
				response="";

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for(Map.Entry<String, String> entry : params.entrySet()){
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		}

		return result.toString();
	}

	/*public static String StreamToString(InputStream in) throws IOException {
		if(in == null) {
			return "";
		}
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} finally {
		}
		return writer.toString();
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_form, menu);
		//menu.findItem(R.id.item_close).setTitle(Html.fromHtml("<font color='#ffffff'>Close</font>"));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {

			case R.id.item_close:
				finish();
				break;

			default:
				return super.onOptionsItemSelected(item);
		}

		return true;
	}
}
