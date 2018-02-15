package com.tywilly.beequoted.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.tywilly.beequoted.R;
import com.tywilly.beequoted.Settings;
import com.tywilly.beequoted.sql.MySQL;
import com.tywilly.beequoted.sql.MySQLData;
import com.tywilly.beequoted.sql.object.ObjectItem;
import com.tywilly.beequoted.sql.object.QuoteListAdapter;

public class ListViewActivity extends ListActivity {

	private int listMode = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		final ProgressDialog dialog = ProgressDialog.show(this, "Loading...",
				"Loading quotes!");

		final ArrayList<ObjectItem> objectsA = new ArrayList<ObjectItem>();
		final MySQL sql = new MySQL(Settings.DB_HOST, Settings.DB_PORT, Settings.DB_USERNAME, Settings.DB_PASSWORD, Settings.DB_DATABASE);

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				MySQLData data;

				if (listMode == 0) {
					data = sql.query("SELECT quote, author, id FROM Quotes");
				} else if (listMode == 1) {
					data = sql.query("SELECT quote, author, id FROM Quotes ORDER BY votes ASC");
				} else if(listMode == 2) {
					data = sql.query("SELECT quote, author, id FROM Quotes WHERE ");
				}else{
					data = sql.query("SELECT * FROM Quotes");
				}

				for (int i = 0; i < data.getString("quote").length; i++) {

					objectsA.add(new ObjectItem(data.getString("quote")[i],
							data.getString("author")[i], data.getInt("id")[i]));

				}

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						QuoteListAdapter adapter = new QuoteListAdapter(
								ListViewActivity.this,
								R.layout.quote_list_view_layout, objectsA);

						setListAdapter(adapter);

						dialog.cancel();

					}
				});

			}

		});

		thread.start();

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub

		

	}

}
