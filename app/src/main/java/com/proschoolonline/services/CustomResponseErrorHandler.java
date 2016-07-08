package com.proschoolonline.services;

import org.androidannotations.annotations.EBean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;


@EBean
public class CustomResponseErrorHandler implements ResponseErrorHandler {


	/*	@App
	public BajaFreshApplication application;
*/
	
	private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

	public boolean hasError(ClientHttpResponse response) throws IOException {
		return errorHandler.hasError(response); }


	public void handleError(ClientHttpResponse response) throws IOException {
		
		//Integer StatusCode = Integer.parseInt(response.getStatusCode().toString());
		
/*		Log.d("Status code",""+StatusCode+application.isIsputordeleterequest());
		if(application != null && application.isIsputordeleterequest() && StatusCode != 200)
		{
			Log.d("Status code",""+StatusCode);
			application.setErrorResponse(true);
			application.setIsputordeleterequest(false);
			
		}
*/
		}
}


 