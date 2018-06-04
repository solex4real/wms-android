package com.whosmyserver.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class servers {
	static String result;
	
	public static String GetbyId(String url, String userId, String restaurantId) {
		
		//ArrayList<NameValuePair> myList = new ();
		//$username,$password,$name,$school,$datemodified,
		//$datecreated,$others,$likes,$groups,$favorites,$status,$userpriv
    InputStream is = null;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    nameValuePairs.add(new BasicNameValuePair("user_id",userId));
    nameValuePairs.add(new BasicNameValuePair("restaurant_id",userId));
    result = "0";
    // Download JSON data from URL
    try {
    	HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpclient.execute(httppost); 
        HttpEntity entity = response.getEntity();
        is = entity.getContent();
        Log.e("pass 1", "connection success ");
    	}catch(Exception e)
    	{
            	Log.e("Fail 1", e.toString());
    	}     
           // String result = null;
    		try
            {
                BufferedReader reader = new BufferedReader
    			(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line;
    			while ((line = reader.readLine()) != null)
    	    {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
    	    Log.e("pass 2", "connection success ");
    	   Log.e("Check Row Affected",result);
    	}
            catch(Exception e)
    	{
                Log.e("Fail 2", e.toString());
    	}     
       return result;
	}
public static String GetAllbyId(String url, String id) {
		
		//ArrayList<NameValuePair> myList = new ();
		//$username,$password,$name,$school,$datemodified,
		//$datecreated,$others,$likes,$groups,$favorites,$status,$userpriv
    InputStream is = null;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    nameValuePairs.add(new BasicNameValuePair("id",id));
    result = "0";
    // Download JSON data from URL
    try {
    	HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpclient.execute(httppost); 
        HttpEntity entity = response.getEntity();
        is = entity.getContent();
        Log.e("pass 1", "connection success ");
    	}catch(Exception e)
    	{
            	Log.e("Fail 1", e.toString());
    	}     
           // String result = null;
    		try
            {
                BufferedReader reader = new BufferedReader
    			(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line;
    			while ((line = reader.readLine()) != null)
    	    {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
    	    Log.e("pass 2", "connection success ");
    	   Log.e("Check Row Affected",result);
    	}
            catch(Exception e)
    	{
                Log.e("Fail 2", e.toString());
    	}     
       return result;
	}
	

}
