package com.whosmyserver.adapter;

import com.whosmyserver.app.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class SearchListAdapter extends CursorAdapter{

	public SearchListAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		// Find fields to populate in inflated template
		
	      TextView tvBody = (TextView) view.findViewById(R.id.search_item_name);
	      TextView tvPriority = (TextView) view.findViewById(R.id.search_item_des);
	      // Extract properties from cursor
	      String body = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
	      String priority = cursor.getString(cursor.getColumnIndexOrThrow("Description"));
	      // Populate fields with extracted properties
	      tvBody.setText(body);
	      tvPriority.setText(String.valueOf(priority));
		
	}

	@Override
	public View newView(Context context, Cursor arg1, ViewGroup parent) {
		// TODO Auto-generated method stub
		 return LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
	}

}
