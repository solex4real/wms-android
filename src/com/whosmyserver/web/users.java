package com.whosmyserver.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class users {
	static String result;
	
	
	
	public static String getUserInfo(String url, String username,  String password) {
    InputStream is = null;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    nameValuePairs.add(new BasicNameValuePair("username",username));
    nameValuePairs.add(new BasicNameValuePair("password",password));
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
	public static String CheckUser(String url, String username) {
    InputStream is = null;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    nameValuePairs.add(new BasicNameValuePair("username",username));
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
	
	
	public static String addUser(String url, String name, String username,  
			String password, String phone, String email, String imagePath){
		String Response = "";		
		try{
			
		
		HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
		MultipartEntity entity = new MultipartEntity(
                HttpMultipartMode.BROWSER_COMPATIBLE);
        entity.addPart("name", new StringBody(name, Charset.forName("UTF-8")));
        entity.addPart("username", new StringBody(username, Charset.forName("UTF-8")));
        entity.addPart("password", new StringBody(password, Charset.forName("UTF-8")));
        entity.addPart("email", new StringBody(email, Charset.forName("UTF-8")));
        entity.addPart("phone", new StringBody(phone, Charset.forName("UTF-8")));
        if(imagePath.trim().length()>0){
        File file = new File(imagePath);
        FileBody cbFile = new FileBody(file, "image/jpeg");
        entity.addPart("picture", cbFile);
        }
        post.setEntity(entity);
        HttpResponse response1 = client.execute(post);
        HttpEntity resEntity = response1.getEntity();
        Response = EntityUtils.toString(resEntity);
        Log.d("Response", Response);
		}catch(Exception e){
			Log.e("Error",e.toString());
		}
		return Response;
	}
	public static String updateUser(String url, String id, String name, String username,  
			String password, String email, String imagePath){
		String Response = "";		
		try{
			
		
		HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
		MultipartEntity entity = new MultipartEntity(
                HttpMultipartMode.BROWSER_COMPATIBLE);
		entity.addPart("id", new StringBody(id, Charset.forName("UTF-8")));
        entity.addPart("name", new StringBody(name, Charset.forName("UTF-8")));
        entity.addPart("username", new StringBody(username, Charset.forName("UTF-8")));
        entity.addPart("password", new StringBody(password, Charset.forName("UTF-8")));
        entity.addPart("email", new StringBody(email, Charset.forName("UTF-8")));
        if(imagePath.trim().length()>0){
        	/*File file = new File(imagePath);
            ContentBody cbFile = new FileBody(file);
            entity.addPart("picture", cbFile);*/
        	
        File file = new File(imagePath);
        FileBody cbFile = new FileBody(file, "image/jpg");
        entity.addPart("picture", cbFile);
        }
        post.setEntity(entity);
        HttpResponse response1 = client.execute(post);
        HttpEntity resEntity = response1.getEntity();
        Response = EntityUtils.toString(resEntity);
        Log.d("Response", Response);
		}catch(Exception e){
			Log.e("Error",e.toString());
		}
		return Response;
	}
}
