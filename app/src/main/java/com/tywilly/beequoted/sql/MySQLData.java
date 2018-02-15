package com.tywilly.beequoted.sql;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class MySQLData {

	JSONArray jArray;
	
	public MySQLData(JSONArray jArray){
		this.jArray = jArray;
	}
	
	public String[] getString(String name){

		ArrayList<String> list = new ArrayList<String>();
		
		for(int i=0;i<jArray.length();i++){
			try {
				list.add(jArray.getJSONObject(i).getString(name));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list.toArray(new String[list.size()]);
	}
	
	public Integer[] getInt(String name){

		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(int i=0;i<jArray.length();i++){
			try {
				list.add(jArray.getJSONObject(i).getInt(name));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list.toArray(new Integer[list.size()]);
	}
	
}
