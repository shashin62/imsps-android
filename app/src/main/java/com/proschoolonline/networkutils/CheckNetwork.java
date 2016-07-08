package com.proschoolonline.networkutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;


/**
 * This class used for CheckNetwork connection is exists or not and used
 *
 * @author Harshapaul Pinto
 */
@EBean
public class CheckNetwork {

	private Context context;

	public CheckNetwork(Context context) {

		this.context = context;

	}

	/**
	 * This method used for check internet connection
	 *
	 * @param
	 * @return connected or not as boolean value
	 */

	public boolean checkInternetConnection() {

		boolean bGotHost=false;

		ConnectivityManager cm = ((ConnectivityManager) context

				.getSystemService(Context.CONNECTIVITY_SERVICE));

		// test for connection

		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected())
		{
			bGotHost=true;
		} 
		
		return bGotHost;

	}

	// if network connection not available show alert dialog
	/**
	 * This method used for if connection is not available then shows error
	 * message as in alert view.
	 *
	 * @param
	 * @return
	 */

	public void showNWError() {
		Toast toast = Toast.makeText(context, "Please Connect To Internet",
				Toast.LENGTH_LONG);
		toast.show();
	}

}