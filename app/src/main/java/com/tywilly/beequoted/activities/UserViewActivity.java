package com.tywilly.beequoted.activities;

import java.net.URL;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.tywilly.beequoted.MainActivity;
import com.tywilly.beequoted.R;
import com.tywilly.beequoted.Settings;
import com.tywilly.beequoted.account.AccountManager;
import com.tywilly.beequoted.sql.MySQL;
import com.tywilly.beequoted.sql.MySQLData;

public class UserViewActivity extends Activity {

	int accountID = 0;

	ImageView profilePic;

	TextView userName;

	ActionBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.user_view_layout);

		profilePic = (ImageView) findViewById(R.id.imageView1);

		userName = (TextView) findViewById(R.id.textView1);

		accountID = getIntent().getExtras().getInt("accountID");

		bar = this.getActionBar();

		Thread netThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				MySQL sql = new MySQL(Settings.DB_HOST, Settings.DB_PORT,
						Settings.DB_USERNAME, Settings.DB_PASSWORD,
						Settings.DB_DATABASE);

				final MySQLData data = sql
						.query("SELECT * FROM Users WHERE id=" + accountID);

				URL url;
				final Bitmap bmp;
				try {
					url = new URL(data.getString("profile_image_url")[0]);

					bmp = BitmapFactory.decodeStream(url.openStream());

					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							profilePic.setImageBitmap(bmp);

							bar.setTitle(data.getString("username")[0]);

						}

					});

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});

		netThread.start();

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		if(accountID == AccountManager.getAccountID()){
			
			if(item.getItemId() == R.id.item1){
				
				Editor edit = getSharedPreferences("Account", 0).edit();
				
				edit.putInt("accountID", 0);
				
				edit.commit();
				
				Intent intent = new Intent(this, MainActivity.class);
				
				this.startActivity(intent);
				
			}
			
		}else{
			
			
			
		}
		
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		if(accountID == AccountManager.getAccountID()){
			
			MenuInflater inflator = getMenuInflater();
			
			inflator.inflate(R.menu.account_view_menu, menu);
			
		}
		
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
}
