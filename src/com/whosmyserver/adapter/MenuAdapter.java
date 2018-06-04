package com.whosmyserver.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.whosmyserver.app.R;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.model.Restaurant;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter implements OnClickListener{
	
	private Activity activity;
    private LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private View[] gridView;
    //private FrameLayout frame; 
    //private FrameLayout[] frame;
    private int pos;
    
	private Context mContext;
    private final ArrayList<String> thumb;
    private final ArrayList<String> name;
    private final ArrayList<String> price;
    public MenuAdapter(Context c,ArrayList<String> thumb,ArrayList<String> name, ArrayList<String> price ) {
        mContext = c;
        this.name = name;
        this.thumb = thumb;
        this.price = price;
        gridView = new View[name.size()];
        
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position,View convertView, final ViewGroup parent) {
		// TODO Auto-generated method stub
		if (inflater == null)
            inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        		convertView = inflater.inflate(R.layout.food_view, null);
        final View view =convertView;
 
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail1);
	 TextView txtName = (TextView)convertView.findViewById(R.id.name);
	 TextView txtPrice = (TextView)convertView.findViewById(R.id.price);
	 final FrameLayout frame = (FrameLayout)view.findViewById(R.id.select_food);
	 view.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	});
	 /*
	 frame.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(frame.getVisibility() !=View.VISIBLE){
				frame.setVisibility(View.VISIBLE);
			}else{
				frame.setVisibility(View.VISIBLE);
			}
			
		}
	});
	*/
	 pos = position;
	 	//Setup Values
        thumbNail.setImageUrl("http://bcminfo.bugs3.com/wms/api/images/"+thumb.get(position), imageLoader);
        txtName.setText(name.get(position));
        txtPrice.setText("$"+price.get(position));
        
        //frame.setOnClickListener(this); 
        
        
        
        
		return view;
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//final FrameLayout frame = (FrameLayout)v.findViewById(R.id.select_food);
		if(v.getVisibility() !=View.VISIBLE){
			v.setVisibility(View.VISIBLE);
		}else{
			v.setVisibility(View.GONE);
		}
		
	}
	
	

}
