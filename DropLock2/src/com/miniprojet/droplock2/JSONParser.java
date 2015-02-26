package com.miniprojet.droplock2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
	
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	
	// Constructor
	public JSONParser() {
		
	}
	
	// function get json from url
	// by making HTTP POST or GET method
	public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {
		
		// Making HTTP request
		try {
			
			// check for request method type
			if (method == "POST") {
				// request method is POST
				// defaultHTTPClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				//Log.d("JSONParser - 48", "adding url to httpost: " + url.toString());
				HttpPost httpPost = new HttpPost(url);
				//Log.d("JSONParser - 50", "HttpPost: " + httpPost.toString());
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				//Log.d("JSONParser - 52", "Url encoded entity: " + httpPost.getEntity().toString());
				
				HttpResponse httpResponse = httpClient.execute(httpPost);
				//Log.d("JSONParser - 55", "HttpResponse: " + httpResponse.toString());
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				
			} else if (method == "GET") {
				// request method is GET

				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");

				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);

				
				HttpResponse httpResponse = httpClient.execute(httpGet);

				HttpEntity httpEntity = httpResponse.getEntity();

				is = httpEntity.getContent();

			}
			
		} catch (UnsupportedEncodingException e) {
			Log.d("JSONParser - 78", "UnsupportedEncodingException" + e.toString());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
        	Log.d("JSONParser - 81", "ClientProtocolException" + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
        	Log.d("JSONParser - 84", "IOException: " + e.toString());
            e.printStackTrace();
        }
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader( is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		
		// try parse the string to a JSON object
		try {
			Log.d("Now I'm in JSONParser", json.toString());

			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		
		// return JSON String
		return jObj;
	}
	

}
