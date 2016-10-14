package com.proschoolonline.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

@EFragment(R.layout.fragment_form)
public class FormFragment extends Fragment implements Validator.ValidationListener{

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
    private Activity _activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
    }

    @AfterViews
    void afterViews(){
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
            String message = error.getCollatedErrorMessage(_activity);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(_activity, message, Toast.LENGTH_LONG).show();
            }
        }
    }


    @UiThread
    void showProgressDialog() {
        // TODO Auto-generated method stub
        progressDialog = ProgressDialog.show(_activity,"", "Please Wait...");
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
        //String result = performPostCall(postDataParams);
        try {
            doSubmit(BuildConfig.formUrl,postDataParams);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dismissProgressDialog();
        }

    }

    @UiThread
    void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Toast.makeText(_activity, getResources().getString(R.string.thanks), Toast.LENGTH_SHORT).show();
        _activity.finish();
    }

    public void doSubmit(String url, HashMap<String, String> data) throws IOException {
        URL siteUrl = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) siteUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        conn.setUseCaches (true);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        DataOutputStream out = new DataOutputStream(conn.getOutputStream());

        Set keys = data.keySet();
        Iterator keyIter = keys.iterator();
        String content = "";
        for(int i=0; keyIter.hasNext(); i++) {
            Object key = keyIter.next();
            if(i!=0) {
                content += "&";
            }
            content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
        }
        Log.v("FINALRESULT", content+"<<----");
        out.writeBytes(content);
        out.flush();
        out.close();
        int responseCode=conn.getResponseCode();
        String response = "";
        if (responseCode == HttpsURLConnection.HTTP_OK) {

            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line=br.readLine()) != null) {
                response+=line;
            }
            Log.v("FINALRESULT","<<---HTTP_OK---->>"+response);
        }
        else {
            Log.v("FINALRESULT","<<---HTTP_ERROR---->>");
        }
    }

}
