package com.proschoolonline.services;

import android.util.Log;
import com.proschoolonline.networkutils.HTTPResponseCacheProperties;
import com.proschoolonline.networkutils.HttpUtils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;
import org.apache.http.client.HttpClient;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author ankit.agrawal
 * This is the base class for setting the Interceptor to the RestClient.
 */

@EBean
public class HTTPClient {
	
	private static final String TAG = "HTTPClient";

	@RestService
	RestClient restClient;

	private HashMap<String, String> headers;
	
	@Bean
	CustomHeaderInterceptor myInterceptor;
	
	@Bean
	CustomResponseErrorHandler Errhandler;

	/**
	 * Add the headers(set in the Activity) to the Interceptor and set the Interceptor to the RestClient
	 * @Param The request id from the constants file. This is used in the base class.
	 * @Return The httpClient to identify that the headers are not null.
	 * */
	public Object setClient(int iRequestType, HTTPResponseCacheProperties overridecache, MultiValueMap<String, Object> map, String... params)
	{
		Log.i(TAG,"inside setClient");
		String strResponseCacheParam=null;
		if(headers!=null && headers.isEmpty()!=true)
		{
			Log.i(TAG,"inside if(headers!=null ");	
			myInterceptor.setHeaders(headers);	
		}
		else
		{
			Log.i(TAG,"inside else ");
			//return null;
		}
			
		
		if(overridecache!=null)
			strResponseCacheParam = overridecache.toString();
		
		

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		
		interceptors.add(myInterceptor);
		HttpClient httpClient = HttpUtils.getNewHttpClient();
		RestTemplate restTemplate = restClient.getRestTemplate();
//		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
		restTemplate.setRequestFactory(new MyClientHttpRequestFactory(strResponseCacheParam));
//		restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());		
		restTemplate.setInterceptors(interceptors);
		//restClient.getRestTemplate().setRequestFactory(requestFactory)
		//CustomResponseErrorHandler Errhandler = new CustomResponseErrorHandler();
		restTemplate.setErrorHandler(Errhandler);
		
		// server not responding added the connection Time out.
		 ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
		    if (factory instanceof HttpComponentsClientHttpRequestFactory) {
		        HttpComponentsClientHttpRequestFactory advancedFactory = (HttpComponentsClientHttpRequestFactory) factory;
		        advancedFactory.setConnectTimeout(3000);
		        advancedFactory.setReadTimeout(3000);
		    } else if (factory instanceof SimpleClientHttpRequestFactory) {
		    	SimpleClientHttpRequestFactory advancedFactory = (SimpleClientHttpRequestFactory) factory;
		        advancedFactory.setConnectTimeout(3000);
		        advancedFactory.setReadTimeout(3000);
		    }
		Log.i(TAG,"end of setClient()");
		
		return httpClient;
		
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
