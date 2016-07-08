package com.proschoolonline.services;

import android.util.Log;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MyClientHttpRequestFactory extends SimpleClientHttpRequestFactory{ 
 
	private String m_strResponseCacheParam=null;
	public static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {

	    public boolean verify(String hostname, SSLSession session) {
	        return true;
	    }
	};	
	
private static final String TAG = null;

public MyClientHttpRequestFactory(String strResponseCacheParam) {
	// TODO Auto-generated constructor stub
	this.m_strResponseCacheParam=strResponseCacheParam;
}

@Override
	protected void prepareConnection(HttpURLConnection connection,
			String httpMethod) throws IOException {
		// TODO Auto-generated method stub
	
		try 		
		{	
			if(m_strResponseCacheParam!=null)
			{
				connection.addRequestProperty("Cache-Control", m_strResponseCacheParam);
			}
			if (connection instanceof HttpsURLConnection)
		    {
		    	Log.i(TAG,"inside if (url.getProtocol().toLowerCase().equals https");
		    		HttpsURLConnection.setDefaultSSLSocketFactory(AcceptSSLCertificate().getSocketFactory());		    		
		            ((HttpsURLConnection)connection).setHostnameVerifier(DO_NOT_VERIFY);
		
		    }	
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		super.prepareConnection(connection, httpMethod);
	
	}

	private SSLContext AcceptSSLCertificate() throws NoSuchAlgorithmException, KeyManagementException 
	{
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}
		} };
		
	    SSLContext sc = SSLContext.getInstance("TLS"); 
	    sc.init(null, trustAllCerts, new java.security.SecureRandom());
	    
	    return sc;		
	}

}