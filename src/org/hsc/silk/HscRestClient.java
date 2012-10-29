package org.hsc.silk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HscRestClient {
	// private String url;
//	private DefaultHttpClient httpClient = new DefaultHttpClient();

	public HscRestClient() {

	}

	public String get(String url) throws IOException {
		// Making HTTP request
		InputStream is = null;
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			// HttpPost httpPost = new HttpPost(url);
			// HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			StatusLine statusLine = httpResponse.getStatusLine();
			if(statusLine.getStatusCode() != 200){
				System.out.println("Could not get resource - " + statusLine.getReasonPhrase() + "["  + statusLine.getStatusCode() + "]");
				return null;
			}else{
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}


		try {
			// BufferedReader reader = new BufferedReader(new InputStreamReader(
			// is, "iso-8859-1"), 8);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
//				System.out.println(line);
			}
			is.close();
			return sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		return null;
	}

}
