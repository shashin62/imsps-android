package com.proschoolonline.networkutils;

import android.util.Log;

import com.proschoolonline.services.Constants;

import org.androidannotations.annotations.EBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Ankit Agrawal
 * The purpose of this class is to create common request header generator as also add new headers to the common headers
 */

@EBean
public class RequestHeaders {

	private static final String TAG = "RequestHeaders";
	HashMap<String, String> requestHeaders = null;
	

	/**
	 *The purpose of this function is to return the common request headers
	 *@return the common request headers 
	 * */
	public HashMap<String, String> CommonRequestHeaders()
	{
		if(requestHeaders==null)
			requestHeaders = new HashMap<String, String>();

		requestHeaders.put("content-type", Constants.ACCEPT);

		/*requestHeaders.put("LICENSECODE", BuildConfig.LICENSCECODE);
		requestHeaders.put("PASSCODE", BuildConfig.PASSCODE);
		requestHeaders.put("REQUESTERID", BuildConfig.REQUESTERID);
		requestHeaders.put("SUBSCRIBERID", BuildConfig.SUBSCRIBERID);*/
		//requestHeaders.put("X-IMEI", Constants.XIMEI);
		//requestHeaders.put("content-type", Constants.ACCEPT);						
		
		return requestHeaders;	
	}
	
	/**
	 *The purpose of this function is to return the common request headers
	 *@param the additional request headers
	 *@return the complete request headers 
	 * */
	public HashMap<String, String> CompleteAdditionalHeaders(HashMap<String, String> newRequestHeaders)
	{
		HashMap<String, String> existingRequestHeaders = CommonRequestHeaders();
		
		if(newRequestHeaders!=null && newRequestHeaders.isEmpty()!=true)
		{		
			Log.i(TAG,"inside if(headers!=null && headers.isEmpty()!=true)");
			Iterator<Map.Entry<String, String>> itr = newRequestHeaders.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) itr
						.next();
				Log.i(TAG,pairs.getKey() + " = " + pairs.getValue());

				existingRequestHeaders.put(pairs.getKey(),pairs.getValue());
			}
			
		}
		
		return existingRequestHeaders;
		
	}
	
	
}

