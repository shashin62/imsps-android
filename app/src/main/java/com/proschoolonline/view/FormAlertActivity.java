package com.proschoolonline.view;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.proschoolonline.mob.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @purpose this class is used to show form popup
 * @author ankit
 *
 */
@EActivity(R.layout.form_alert)
public class FormAlertActivity extends Activity {
	
	@ViewById(R.id.wvForm)
	WebView webview;

	@AfterViews
	void afterViews(){
		WebSettings webSetting = webview.getSettings();
		webSetting.setJavaScriptEnabled(true);
		//webSetting.setDisplayZoomControls(true);

		//webview.loadUrl("file:///android_asset/form.html");

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
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	public static String StreamToString(InputStream in) throws IOException {
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
	}

}
