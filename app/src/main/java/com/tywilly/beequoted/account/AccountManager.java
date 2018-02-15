package com.tywilly.beequoted.account;

import android.content.SharedPreferences;

public class AccountManager {
	
	
	
	private static int userId = 0;
	
	public static int getAccountID(){
		return userId;
	}
	
	public static void loadAccount(SharedPreferences prefs){
	
		userId = prefs.getInt("accountID", 0);
		
	}
	
}
