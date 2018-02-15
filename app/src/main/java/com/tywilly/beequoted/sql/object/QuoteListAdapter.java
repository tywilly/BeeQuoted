package com.tywilly.beequoted.sql.object;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tywilly.beequoted.R;


public class QuoteListAdapter extends ArrayAdapter<ObjectItem> {

	Context context;
	ArrayList<ObjectItem> objects;

	int offset = 0;

	public QuoteListAdapter(Context context, int textViewResourceId,
			ArrayList<ObjectItem> objects) {
		super(context, textViewResourceId, objects);

		this.context = context;
		this.objects = objects;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			convertView = inflater.inflate(R.layout.quote_list_view_layout,
					parent, false);

		}
		TextView quoteView = (TextView) convertView
				.findViewById(R.id.textView1);

		quoteView.setText(objects.get(position).quote);

		convertView.setTag(objects.get(position).ID);

		TextView authorView = (TextView) convertView
				.findViewById(R.id.textView2);

		authorView.setText("Posted By: " + objects.get(position).author);

		return convertView;
	}

}
