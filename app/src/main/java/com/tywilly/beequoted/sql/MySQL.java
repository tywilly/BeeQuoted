package com.tywilly.beequoted.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class MySQL {

	HttpClient httpClient;
	HttpPost httpPost;
	HttpResponse response;

	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

	public MySQL(String address, int port, String username, String password,
			String database) {

		httpClient = new DefaultHttpClient();
		httpPost = new HttpPost(
				"http://tywilly.com/projects/AndroidMySQLibrary/PublicQuery.php");

		nameValuePairs.add(new BasicNameValuePair("address", address));
		nameValuePairs.add(new BasicNameValuePair("port", Integer.toString(port)));
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		nameValuePairs.add(new BasicNameValuePair("database", database));

	}

	public MySQLData query(String query) {

		nameValuePairs.add(new BasicNameValuePair("queryString", query));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			response = httpClient.execute(httpPost);

			InputStream in = response.getEntity().getContent();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			in.close();

			Log.d("MYSQL", "" + sb.toString());

			JSONArray jArray;

			if (!sb.toString().equals("[]")) {
				jArray = new JSONArray(sb.toString());
			} else {
				jArray = new JSONArray("{ 'confirm': [1]}");
			}

			MySQLData data = new MySQLData(jArray);

			return data;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
