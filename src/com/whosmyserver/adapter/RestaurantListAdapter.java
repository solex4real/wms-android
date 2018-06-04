package com.whosmyserver.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.whosmyserver.app.R;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.model.Restaurant;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class RestaurantListAdapter extends BaseAdapter{
	
	private Activity activity;
    private LayoutInflater inflater;
    private List<Restaurant> restaurantItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
 
    public RestaurantListAdapter(Activity activity, List<Restaurant> restaurantItems) {
        this.activity = activity;
        this.restaurantItems = restaurantItems;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return restaurantItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return restaurantItems.get(position);
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
	        TextView year = (TextView) convertView.findViewById(R.id.status);
	 
	        // getting Restaurant data for the row
	        Restaurant m = restaurantItems.get(position);
	 
	        // thumbnail image
	        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
	         
	        // title
	        title.setText(m.getTitle());
	         
	        // rating
	        rating.setText("Rating: " + String.valueOf(m.getRating()));
	         
	        // address
	        String addressStr = "";
	        for (String str : m.getAddress()) {
	            addressStr += str + ", ";
	        }
	        addressStr = addressStr.length() > 0 ? addressStr.substring(0,
	                addressStr.length() - 2) : addressStr;
	        address.setText(addressStr);
	         
	        // release status
	        year.setText(String.valueOf(m.getStatus())+"mi");
	 
	        return convertView;
	}

	
}
