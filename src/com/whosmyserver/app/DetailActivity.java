package com.whosmyserver.app;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.whosmyserver.app.R;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.util.CircledNetworkImageView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {

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

	public DetailActivity() {
	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_details);
		ImageLoader imageLoader = AppController.getInstance().getImageLoader();

		// Get arguments
		Bundle bundle = getIntent().getExtras();
		resName = bundle.getString("name");
		image_url = bundle.getString("image_url");
		icon_url = bundle.getString("icon_url");
		resId = bundle.getString("id");
		resAddress = bundle.getString("address");
		resDate = bundle.getString("date");
		resTime = bundle.getString("time");
		resContacts = bundle.getString("contact");

		TextView txtName = (TextView) findViewById(R.id.res_name);
		TextView txtTitle = (TextView) findViewById(R.id.restaurant_name);
		TextView txtAddress = (TextView)findViewById(R.id.res_address);
		TextView txtTime = (TextView) findViewById(R.id.res_time);
		TextView txtDate = (TextView) findViewById(R.id.res_date);
		TextView txtNum = (TextView) findViewById(R.id.res_number);
		TextView txtEmail = (TextView) findViewById(R.id.res_email);
		NetworkImageView imgRes = (NetworkImageView) findViewById(R.id.res_image);
		CircledNetworkImageView imgLogo = (CircledNetworkImageView) findViewById(R.id.res_logo);

		if (resId.equals("")) {
			// Implement data
			txtName.setText(resName);
			txtTitle.setText(resName);
			txtAddress.setText(resAddress);
			txtTime.setTextColor(R.color.red);
			txtTime.setText("Not available");
			txtDate.setText(resDate);
			txtNum.setText(resContacts);
			txtEmail.setText("Work");
			//imgRes.setImageUrl(image_url, imageLoader);
			//if (icon_url.equals("")) {
				imgLogo.setImageUrl(icon_url, imageLoader);
			//}
		}

	}

}
