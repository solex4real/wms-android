package com.whosmyserver.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.whosmyserver.app.R;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.model.Restaurant;
import com.whosmyserver.model.recentData;
import com.whosmyserver.util.StringParser;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class RecentListAdapter extends BaseAdapter{
	
	private Activity activity;
    private LayoutInflater inflater;
    private List<recentData> recentItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
 
    public RecentListAdapter(Activity activity, List<recentData> recentItems) {
        this.activity = activity;
        this.recentItems = recentItems;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return recentItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return recentItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 if (inflater == null)
	            inflater = (LayoutInflater) activity
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null)
	            convertView = inflater.inflate(R.layout.list1_row, null);
	 
	        if (imageLoader == null)
	            imageLoader = AppController.getInstance().getImageLoader();
	        NetworkImageView thumbNail = (NetworkImageView) convertView
	                .findViewById(R.id.thumbnail);
	        TextView title = (TextView) convertView.findViewById(R.id.title);
	        TextView rating = (TextView) convertView.findViewById(R.id.rating);
	        TextView address = (TextView) convertView.findViewById(R.id.address);
	        TextView date = (TextView) convertView.findViewById(R.id.status);
	 
	        // getting Restaurant data for the row
	        recentData m = recentItems.get(position);
	 
	        // thumbnail image
	        thumbNail.setImageUrl("http://bcminfo.bugs3.com/wms/api/images/"+m.getImagePath(), imageLoader);
	         
	        // title
	        title.setText(m.getName());
	         
	        // rating
	        //rating.setText("Rating: " + String.valueOf(m.getRating()));
	               
	        // address
	        
	        address.setText(m.getAddress());
	         
	        // date
	        date.setText(StringParser.getDateTitle(m.getDate()));
	 
	        return convertView;
	}

	
}
