package com.proschoolonline.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.proschoolonline.mob.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * @purpose this class is used to show form popup
 * @author ankit
 *
 */
@EActivity(R.layout.form_alert)
public class FormAlertActivity extends AppCompatActivity{
	
	/*@ViewById(R.id.wvForm)
	WebView webview;*/

	@ViewById(R.id.toolbar)
	Toolbar toolbar;

	@AfterViews
	void afterViews(){
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setIcon(R.mipmap.app_icon);

	}

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
