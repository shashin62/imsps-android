package com.proschoolonline.services;

import android.util.Log;

import com.proschoolonline.application.DataFetchApplication;
import com.proschoolonline.networkutils.CheckNetwork;
import com.proschoolonline.networkutils.HTTPResponseCacheProperties;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.springframework.util.MultiValueMap;


/**
 * @author This is the implementation class of HTTPClient.
 *         Calls specific to requests are made from this class.
 */
@EBean
public class BfHttpClient extends HTTPClient {

	private static final String TAG = "BfHttpClient";

	@Bean
	CheckNetwork checkNetworkBean;

	@App
	DataFetchApplication application;

	/**
	 * Check if the super method is successful and make calls for the request
	 * specified in the param.
	 * 
	 * @Param The request id from the constants file to represent a specific
	 *        request.
	 * @Return The model class of the specific request calls. We dont know which
	 *         request is called and hence which model will be returned. Hence
	 *         Object is returned as a generic type.
	 * */
	@Override
	public Object setClient(int iRequestType,
							HTTPResponseCacheProperties overridecache, MultiValueMap<String, Object> map, String... params) {
		Object data = null;
		Log.i(TAG, "inside setClient");
		if (super.setClient(iRequestType, overridecache, map) != null) {
			if (checkNetworkBean.checkInternetConnection()) {
				application.checkForNetwork = 0;
				if (iRequestType == Constants.NEWS_DATA_LIST) {
					data = restClient.getNewsData();

					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}
				else if (iRequestType == Constants.CATEGORIES_LIST) {
					data = restClient.getCategoriesData();

					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}
				else if (iRequestType == Constants.NEWS_FILTER) {
					data = restClient.getNewsDataFilter(params[0]);

					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}

			} else {

				Log.i(TAG, "NetworkError");
				application.checkForNetwork = 1;

			}

		} else {
			Log.i(TAG, "Error");
		}
		return data;
	}
}
