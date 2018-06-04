package com.whosmyserver.fragment;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.whosmyserver.app.R;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.util.CircledNetworkImageView;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailFragment extends Fragment {

	// Define Variables
	private String image_url = null;
	private String icon_url = null;
	private String resName = null;
	private String resAddress = null;
	private String resId = null;
	private String resDate = null;
	private String resTime = null;
	private String resContacts = null;
	
	private FragmentActivity context;

	private View rootView = null;

	public DetailFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.restaurant_details, container,
				false);

		ImageLoader imageLoader = AppController.getInstance().getImageLoader();

		// Get arguments
		resName = getArguments().getString("name");
		image_url = getArguments().getString("image_url");
		icon_url = getArguments().getString("icon_url");
		resId = getArguments().getString("id");
		resAddress = getArguments().getString("address");
		resDate = getArguments().getString("date");
		resTime = getArguments().getString("time");
		resContacts = getArguments().getString("contact");

		TextView txtName = (TextView) rootView.findViewById(R.id.res_name);
		TextView txtAddress = (TextView) rootView
				.findViewById(R.id.res_address);
		TextView txtTime = (TextView) rootView.findViewById(R.id.res_time);
		TextView txtDate = (TextView) rootView.findViewById(R.id.res_date);
		TextView txtNum = (TextView) rootView.findViewById(R.id.res_number);
		TextView txtEmail = (TextView) rootView.findViewById(R.id.res_email);
		//NetworkImageView imgRes = (NetworkImageView) rootView
			//	.findViewById(R.id.res_image);
		CircledNetworkImageView imgLogo = (CircledNetworkImageView) rootView
				.findViewById(R.id.res_logo);

		if (resId != null) {
			// Implement data
			txtName.setText(resName);
			txtAddress.setText(resAddress);
			txtTime.setBackgroundResource(R.color.red);
			txtTime.setText("Not available");
			txtDate.setText(resDate);
			//txtNum.setText(resContacts);
			//txtEmail.setText("Work");
			//imgRes.setImageUrl(image_url, imageLoader);
			if (icon_url != null) {
				imgLogo.setImageUrl(icon_url, imageLoader);
			}
		}

		return rootView;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

}
