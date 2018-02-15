package com.tywilly.beequoted.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.tywilly.beequoted.R;
import com.tywilly.beequoted.Settings;
import com.tywilly.beequoted.sql.MySQL;
import com.tywilly.beequoted.sql.MySQLData;

public class QuoteViewActivity extends Activity {

	private int id;

	private int totalVotes = 0;
	private int dailyVotes = 0;

	MySQL sql = new MySQL(Settings.DB_HOST, Settings.DB_PORT, Settings.DB_USERNAME,
			Settings.DB_PASSWORD, Settings.DB_DATABASE);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.quote_view_activity_layout);

		final TextView quoteView = (TextView) findViewById(R.id.textView1);
		final TextView authorView = (TextView) findViewById(R.id.textView2);
		final TextView statsView = (TextView) findViewById(R.id.textView3);
		final TextView testTetx = (TextView) findViewById(R.id.textView3);

		final Button upVoteBtn = (Button) findViewById(R.id.button1);

		id = this.getIntent().getExtras().getInt("ID");

		Thread netThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				final MySQLData data = sql.query("SELECT * FROM Quotes WHERE id="
						+ id);

				final String posterName = sql.query("SELECT username FROM Users WHERE id='" + data.getString("user_id")[0] + "'").getString("username")[0];

				totalVotes = data.getInt("votes")[0];
				dailyVotes = data.getInt("votes")[0];

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						quoteView.setText(data.getString("quote")[0]);

						authorView.setText("Posted By: " + posterName);
						statsView.setText("Votes: " + totalVotes);
					}

				});

			}

		});

		netThread.start();

		upVoteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				totalVotes++;
				dailyVotes++;

				Thread netThread = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						sql.query("UPDATE Quotes SET dailyVotes=" + dailyVotes + ", votes=" + totalVotes + " WHERE id=" + id);

					}

				});

				netThread.start();

			}
		});
	}

}
