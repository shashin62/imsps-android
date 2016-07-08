package com.proschoolonline.adapter;

import android.util.Log;

import com.proschoolonline.model.CategoriesData;
import com.proschoolonline.model.NewsData;
import com.proschoolonline.networkutils.RequestHeaders;
import com.proschoolonline.services.BfHttpClient;
import com.proschoolonline.services.Constants;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.List;

@EBean
public class LoginAdapter {

	private static final String TAG = "LoginAdapter";
	
	@Bean
	BfHttpClient connectionObj;
	
	
	@Bean
	RequestHeaders requestHeaders;

	public List<NewsData> getNewsData()
	{
		HashMap<String, String> tempRequestHeaders = requestHeaders.CommonRequestHeaders();

		Log.i(TAG,"before setHeaders");

		connectionObj.setHeaders(tempRequestHeaders);
		Log.i(TAG,"before setClient");
		List<NewsData> weatherData;
		try{
			weatherData =(List<NewsData>)connectionObj.setClient(Constants.NEWS_DATA_LIST,null,null);
			Log.i(TAG,"News List"+weatherData);
		}
		catch(ResourceAccessException e){
			weatherData = null;
		}
		return weatherData;
	}

	public List<CategoriesData> getCategoriesData()
	{
		HashMap<String, String> tempRequestHeaders = requestHeaders.CommonRequestHeaders();

		Log.i(TAG,"before setHeaders");

		connectionObj.setHeaders(tempRequestHeaders);
		Log.i(TAG,"before setClient");
		List<CategoriesData> weatherData;
		try{
			weatherData =(List<CategoriesData>)connectionObj.setClient(Constants.CATEGORIES_LIST,null,null);
			Log.i(TAG,"Category List"+weatherData);
		}
		catch(ResourceAccessException e){
			weatherData = null;
		}
		return weatherData;
	}

	public List<NewsData> getNewsDataFilter(String data)
	{
		HashMap<String, String> tempRequestHeaders = requestHeaders.CommonRequestHeaders();

		Log.i(TAG,"before setHeaders");

		connectionObj.setHeaders(tempRequestHeaders);
		Log.i(TAG,"before setClient");
		List<NewsData> weatherData;
		try{
			weatherData =(List<NewsData>)connectionObj.setClient(Constants.NEWS_FILTER,null,null,data);
			Log.i(TAG,"News List"+weatherData);
		}
		catch(ResourceAccessException e){
			weatherData = null;
		}
		return weatherData;
	}

	
	/*public LoginList getLoginList(String uid,String pass)
	{		
		HashMap<String, String> tempRequestHeaders = requestHeaders.CommonRequestHeaders();

		Log.i(TAG,"before setHeaders");

		connectionObj.setHeaders(tempRequestHeaders);
		Log.i(TAG,"before setClient");
		LoginList brandList;
		try{
			brandList =(LoginList)connectionObj.setClient(Constants.LOGIN_LIST,null,null,uid,pass);
			Log.i(TAG,"Brand List"+brandList);
		}
		catch(ResourceAccessException e){
			brandList = null;
		}
		return brandList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Appointment> getAppointmentList(String uid)
	{		
		HashMap<String, String> tempRequestHeaders = requestHeaders.CommonRequestHeaders();

		Log.i(TAG,"before setHeaders");

		connectionObj.setHeaders(tempRequestHeaders);
		Log.i(TAG,"before setClient");
		List<Appointment> brandList;
		try{
			brandList =(List<Appointment>)connectionObj.setClient(Constants.APPOINTMENT_LIST,null,null,uid);
			Log.i(TAG,"Brand List"+brandList);
		}
		catch(ResourceAccessException e){
			brandList = null;
		}
		return brandList;
	}
	
	
	public String uploadCheckInData(CheckInDetailsModels data)
	{

		String verifyZip = null;
		HashMap<String, String> tempRequestHeaders = requestHeaders
				.CommonRequestHeaders();

		Log.i(TAG, "before setHeaders");

		connectionObj.setHeaders(tempRequestHeaders);
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonResponse = mapper.writeValueAsString(data);
			Log.v("Json Post", jsonResponse);
			verifyZip = (String) connectionObj.setClient(Constants.CHECKIN_DATA, null, null, jsonResponse);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return verifyZip;

	}*/

}
