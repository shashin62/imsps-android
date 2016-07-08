package com.proschoolonline.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.proschoolonline.mob.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.splash_screen)
public class SplashActivity extends Activity {

	@ViewById(R.id.imageView1)
	ImageView myImageView;

	protected int _splashTime = 5000;
	private Activity _activity;
	private Animation myFadeInAnimation;
	Handler splash_time_handler;
	private Runnable r_callingMenu;

	public static boolean onlyOnce = true;

	@AfterViews
	void applicationStartExecuting() {
		_activity = this;
		startFadin();
		onlyOnce = true;// to reset the date to the todays Date
		splash_time_handler = new Handler();
		splash_time_handler.postDelayed(r_callingMenu = new Runnable() {
			public void run() {
				Intent intent = new Intent(_activity,HomeActivity_.class);
				intent.putExtra("FirstTime", true);
            	startActivity(intent);
                finish();
			}
		}, _splashTime);
	}

	protected void startFadin() {
		// TODO Auto-generated method stub
		myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		myImageView.startAnimation(myFadeInAnimation); // Set animation to your
														// ImageView
		// if(Constants.LOG)Log.d("SplashScreen","Fading Effect");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		splash_time_handler.removeCallbacks(r_callingMenu);
	}

}
