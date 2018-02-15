package com.tywilly.beequoted;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.net.URL;
import java.util.ArrayList;

import com.tywilly.beequoted.*;
import com.tywilly.beequoted.account.AccountCreationActivity;
import com.tywilly.beequoted.account.AccountManager;
import com.tywilly.beequoted.activities.ComposeActivity;
import com.tywilly.beequoted.activities.QuoteViewActivity;
import com.tywilly.beequoted.activities.UserViewActivity;
import com.tywilly.beequoted.sql.MySQL;
import com.tywilly.beequoted.sql.MySQLData;
import com.tywilly.beequoted.sql.object.ObjectItem;
import com.tywilly.beequoted.sql.object.QuoteListAdapter;

public class MainActivity extends Activity {

    Tab hotTab;
    Tab topTab;
    Tab newTab;

    ListView listView;
    ProgressBar spinner;

    EditText editText;

    int lastItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        com.tywilly.beequoted.account.AccountManager.loadAccount(getSharedPreferences("Account", 0));

        listView = (ListView) findViewById(R.id.listView1);

        editText = (EditText) findViewById(R.id.editText1);
        editText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(MainActivity.this,
                        ComposeActivity.class);

                startActivity(intent);

            }
        });
        //editText.setEnabled(false);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setIndeterminate(true);
        spinner.setVisibility(Spinner.INVISIBLE);

        final ActionBar actionBar = this.getActionBar();

        actionBar.show();

        // actionBar.setTitle("");

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int arg2,
                                    long arg3) {

                Intent intent = new Intent(MainActivity.this,
                        QuoteViewActivity.class);

                intent.putExtra("ID", (Integer) v.getTag());

                startActivity(intent);

            }
        });

        listView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
            }
        });


        inflateList();

    }

    private void inflateList() {

        spinner.setVisibility(Spinner.VISIBLE);

        listView.animate().setDuration(500).alpha(0);
        listView.setVisibility(View.INVISIBLE);

        final ArrayList<ObjectItem> objectsA = new ArrayList<ObjectItem>();
        final MySQL sql = new MySQL(Settings.DB_HOST, Settings.DB_PORT,
                Settings.DB_USERNAME, Settings.DB_PASSWORD,
                Settings.DB_DATABASE);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                MySQLData data;

                //data = sql.query("SELECT quote, username, `Quotes`.id FROM Quotes, Users WHERE `Quotes`.user_id = `Users`.id AND `Users`.following LIKE `Quotes`.user_id");
                data = sql.query("SELECT * FROM Quotes");

                for (int i = 0; i < data.getString("quote").length; i++) {

                    objectsA.add(new ObjectItem(data.getString("quote")[i],
                            sql.query("SELECT username FROM Users WHERE id='" + data.getInt("user_id")[i] + "'").getString("username")[0], data.getInt("id")[i]));

                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        QuoteListAdapter adapter = new QuoteListAdapter(
                                MainActivity.this,
                                R.layout.quote_list_view_layout, objectsA);

                        listView.setAdapter(adapter);

                        listView.animate().setDuration(500).alpha(1);
                        listView.setVisibility(View.VISIBLE);
                        spinner.setVisibility(Spinner.INVISIBLE);

                    }
                });

            }

        });

        thread.start();
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        // TODO Auto-generated method stub

        if (item.getItemId() == R.id.item1) {

            if (AccountManager.getAccountID() != 0) {

                Intent intent = new Intent(this, UserViewActivity.class);

                intent.putExtra("accountID", AccountManager.getAccountID());

                this.startActivity(intent);

            } else {
                Intent intent = new Intent(this, AccountCreationActivity.class);
                this.startActivity(intent);
            }
        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflator = getMenuInflater();

        inflator.inflate(R.menu.action_bar_menu, menu);

        Thread netThread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                try {

                    MySQL sql = new MySQL(Settings.DB_HOST, Settings.DB_PORT,
                            Settings.DB_USERNAME, Settings.DB_PASSWORD,
                            Settings.DB_DATABASE);

                    MySQLData data = sql
                            .query("SELECT profile_image_url FROM Users WHERE id="
                                    + com.tywilly.beequoted.account.AccountManager
                                    .getAccountID());

                    URL newurl = new URL(data.getString("profile_image_url")[0]);

                    final BitmapDrawable mIcon_val = new BitmapDrawable(
                            getResources(), newurl.openConnection()
                            .getInputStream());

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            menu.getItem(0).setIcon(mIcon_val);
                        }

                    });

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        netThread.start();

        return true;
    }
}
