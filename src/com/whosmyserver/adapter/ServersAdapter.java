package com.whosmyserver.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.whosmyserver.app.R;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.model.Restaurant;
import com.whosmyserver.util.CircledNetworkImageView;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ServersAdapter extends BaseAdapter{
	
	
    private LayoutInflater inflater;
    private Context mContext;
    private final ArrayList<String> thumb;
    private final ArrayList<String> name;
    private final ArrayList<String> rating;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
 
    public ServersAdapter(Activity activity, ArrayList<String> thumb,ArrayList<String> name, ArrayList<String> rating) {
    	mContext = activity;
        this.name = name;
        this.thumb = thumb;
        this.rating = rating;
        
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return name.get(position);
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
	            inflater = (LayoutInflater) mContext
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null)
	            convertView = inflater.inflate(R.layout.servers_row, null);
	 
	        if (imageLoader == null)
	            imageLoader = AppController.getInstance().getImageLoader();
	        CircledNetworkImageView thumbNail = (CircledNetworkImageView) convertView
	                .findViewById(R.id.thumb);
	        TextView title = (TextView) convertView.findViewById(R.id.server_name);
	        ImageView divider = (ImageView) convertView.findViewById(R.id.divider);
	        divider.setVisibility(View.GONE);
	        //TextView rating = (TextView) convertView.findViewById(R.id.rating);
	        TextView ratingTxt = (TextView) convertView.findViewById(R.id.rating);
	        //TextView year = (TextView) convertView.findViewById(R.id.status);
	 
	        
	 
	        // thumbnail image
	        thumbNail.setImageUrl("http://bcminfo.bugs3.com/wms/api/images/"+thumb.get(position), imageLoader);
	         
	        // title
	        title.setText(name.get(position));
	         
	        // rating
	        //rating.setText("Rating: " + String.valueOf(m.getRating()));
	         
	        
	        ratingTxt.setText(rating.get(position));
	         
	        // release status
	        //year.setText(String.valueOf(m.getStatus())+"mi");
	 
	        return convertView;
	}

	
}
