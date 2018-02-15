package com.tywilly.beequoted.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tywilly.beequoted.MainActivity;
import com.tywilly.beequoted.R;
import com.tywilly.beequoted.Settings;
import com.tywilly.beequoted.account.AccountManager;
import com.tywilly.beequoted.sql.MySQL;

public class ComposeActivity extends Activity {

	EditText editText;

	Button postButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.compose_activity_layout);

		overridePendingTransition(R.anim.slide_in_bottm,
				android.R.anim.slide_out_right);

		editText = (EditText) findViewById(R.id.editText1);

		editText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				if (hasFocus) {
					editText.setText("");
				} else {
					editText.setText("Enter Quote...");
				}

			}
		});

		postButton = (Button) findViewById(R.id.post_btn);

		postButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						MySQL sql = new MySQL(Settings.DB_HOST,
								Settings.DB_PORT, Settings.DB_USERNAME,
								Settings.DB_PASSWORD, Settings.DB_DATABASE);

						sql.query("INSERT INTO Quotes (`quote`, `user_id`) VALUES ('"
								+ editText.getText().toString()
								+ "', '"
								+ AccountManager.getAccountID() + "')");

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								Toast.makeText(ComposeActivity.this,
										"New post added!", Toast.LENGTH_SHORT)
										.show();

								Intent intent = new Intent(
										ComposeActivity.this,
										MainActivity.class);

								startActivity(intent);

							}
						});

					}

				});

				thread.start();

			}
		});

	}

}
