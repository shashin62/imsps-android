package com.proschoolonline.services;

import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ankit.agrawal
 * This is the Interceptor class to set the headers in the RestClient.
 */
@EBean
public class CustomHeaderInterceptor implements ClientHttpRequestInterceptor {
	
	private static final String TAG = "CustomHeaderInterceptor";
	private HashMap<String, String> headers;	

	
	@Override
	public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] byteArray,
			ClientHttpRequestExecution arg2) throws IOException {
		
		Log.i(TAG,"inside intercept()");
		/**This is  the code for getting the headers set in the activity Adapter class and set them to the HttpRequest object*/
		
		Log.i(TAG,"before ClientHttpResponseStatus");

/*		String s = new String(byteArray);
		Log.i(TAG,"ClientHttpResponseStatus = " + s);
*/		
//		Log.i(TAG,"ClientHttpResponseStatus = " + response.getStatusText());
		
		if(headers!=null && headers.isEmpty()!=true)
		{		
			Log.i(TAG,"inside if(headers!=null && headers.isEmpty()!=true)");
			
			Iterator<Map.Entry<String, String>> itr = headers.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) itr
						.next();
				Log.i(TAG,pairs.getKey() + " = " + pairs.getValue());

				httpRequest.getHeaders().add(pairs.getKey(), pairs.getValue());
			}
			
		}

		return arg2.execute(httpRequest, byteArray);
	}

	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {
 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
	}
	
	/**
	 * This method is used to get headers.
	 * 
	 * @param
	 * @return headers as HashMap with headers.
	 */
	public HashMap<String, String> getHeaders() {
		return headers;
	}

	/**
	 * This method is used to set headers.
	 * 
	 * @param headers
	 *            as HashMap with headers.
	 * @return
	 */
	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}
	
}
