package com.tywilly.beequoted.account;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.tywilly.beequoted.MainActivity;
import com.tywilly.beequoted.R;
import com.tywilly.beequoted.Settings;
import com.tywilly.beequoted.sql.MySQL;
import com.tywilly.beequoted.sql.MySQLData;

public class AccountCreationActivity extends Activity {

	EditText loginUsername;
	EditText loginPassword;

	Button loginButton;

	EditText registerUsername;
	EditText registerPassword;
	EditText registerEmail;

	Button registerButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.account_creation_layout);

		loginUsername = (EditText) findViewById(R.id.editText1);
		loginPassword = (EditText) findViewById(R.id.editText2);

		loginButton = (Button) findViewById(R.id.button1);
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				final ProgressDialog pro = new ProgressDialog(
						AccountCreationActivity.this);

				pro.show();

				Thread netThread = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						MySQL sql = new MySQL(Settings.DB_HOST,
								Settings.DB_PORT, Settings.DB_USERNAME,
								Settings.DB_PASSWORD, Settings.DB_DATABASE);

						MySQLData data = sql
								.query("SELECT id FROM Users WHERE username='"
										+ loginUsername.getText().toString()
										+ "' AND password='"
										+ loginPassword.getText().toString()
										+ "'");

						Editor edit = getSharedPreferences("Account", 0).edit();

						edit.putInt("accountID", data.getInt("id")[0]);

						edit.commit();

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								pro.hide();

								Intent intent = new Intent(
										AccountCreationActivity.this,
										MainActivity.class);

								startActivity(intent);
							}
						});

					}

				});

				netThread.start();

			}
		});

		registerUsername = (EditText) findViewById(R.id.editText3);
		registerPassword = (EditText) findViewById(R.id.editText4);
		registerEmail = (EditText) findViewById(R.id.editText5);

		registerButton = (Button) findViewById(R.id.button2);
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Thread netThread = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						
						
					}

				});

				netThread.start();
				
			}
		});

	}
}
