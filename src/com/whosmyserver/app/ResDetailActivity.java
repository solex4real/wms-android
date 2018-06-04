package com.whosmyserver.app;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.util.CircledNetworkImageView;
import com.whosmyserver.web.restaurant;
import com.whosmyserver.web.servers;

public class ResDetailActivity extends Activity implements OnClickListener {

	TextView txtTime, txtFoodType;
	NetworkImageView thumbNail, icon;
	ImageView iconFav;
	LinearLayout Wrapper;
	String id, Resname, ResImage, ResAddress;

	String result;
	JSONObject json_data = new JSONObject();
	private ProgressDialog pDialog;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_layout);

		// Get arguments
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("id");

		thumbNail = (NetworkImageView) findViewById(R.id.res_banner);
		icon = (NetworkImageView) findViewById(R.id.res_logo);
		iconFav = (ImageView) findViewById(R.id.fav_icon);
		txtTime = (TextView) findViewById(R.id.res_dates);
		txtFoodType = (TextView) findViewById(R.id.res_type);
		Wrapper = (LinearLayout) findViewById(R.id.servers_wrapper);

		new loadRestaurant().execute();

	}

	public class loadRestaurant extends AsyncTask<String, integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(ResDetailActivity.this);
			// Showing progress dialog before making http request
			pDialog.setMessage("Loading...");
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			// get search result
			String result = restaurant
					.Get("http://bcminfo.bugs3.com/wms/api/controller/restaurants.php?method=getbyid",
							id);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				JSONArray jArray = new JSONArray(result);
				json_data = jArray.getJSONObject(0);
				Resname = json_data.getString("name");
				ResImage = json_data.getString("banner_path");
				ResAddress = json_data.getString("address");
				txtFoodType.setText(json_data.getString("food_type"));
				thumbNail.setImageUrl(
						"http://bcminfo.bugs3.com/wms/api/images/"
								+ json_data.getString("banner_path"),
						imageLoader);
				icon.setImageUrl("http://bcminfo.bugs3.com/wms/api/images/"
						+ json_data.getString("icon_path"), imageLoader);
				txtTime.setText("Mon - Sun 9am - 12pm");
				if(json_data.getString("type").trim().equals("1")){
					new loadRestaurants().execute();
				}else{
					new loadServers().execute();
				}
				
			} catch (JSONException e) {
				Log.e("Getting Resturant Error", e.toString());
			}

			
			// hidePDialog();

		}

	}

	public class loadServers extends AsyncTask<String, integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			// get search result
			String result = servers
					.GetAllbyId(
							"http://bcminfo.bugs3.com/wms/api/controller/servers.php?method=getbyallid",
							id);

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					// Add views
					// set up list view
					json_data = jArray.getJSONObject(i);
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					final View view = inflater.inflate(R.layout.servers_row,
							null);
					final String namestr = json_data.getString("name");
					final String servericon = json_data.getString("image_path");
					final String ratingstr = json_data.getString("rating");
					CircledNetworkImageView thumb = (CircledNetworkImageView) view
							.findViewById(R.id.thumb);
					ImageView availabilty = (ImageView) view
							.findViewById(R.id.availability);
					ImageView divider = (ImageView) view
							.findViewById(R.id.divider);
					TextView name = (TextView) view
							.findViewById(R.id.server_name);
					TextView rating = (TextView) view.findViewById(R.id.rating);

					thumb.setImageUrl(
							"http://bcminfo.bugs3.com/wms/api/images/"
									+ json_data.getString("image_path"),
							imageLoader);
					name.setText(json_data.getString("name"));
					rating.setText(json_data.getString("rating"));
					if (json_data.getString("name").equals("0")) {
						availabilty.setVisibility(view.VISIBLE);
					}

					if (i == (jArray.length() - 1)) {
						divider.setVisibility(view.GONE);
					}
					view.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							
							Thread menuThread = new Thread() {
								public void run() {
									Intent openMenu = new Intent(
											"com.whosmyserver.app.MenuActivity");
									openMenu.putExtra("name", namestr);
									openMenu.putExtra("rating", ratingstr);
									openMenu.putExtra("resname", Resname);
									openMenu.putExtra("thumb", servericon);
									openMenu.putExtra("resthumb", ResImage);
									openMenu.putExtra("address", ResAddress);
									openMenu.putExtra("id", id);
									startActivity(openMenu);
								}

							};
							menuThread.start();
						}
					});

					Wrapper.addView(view);
					Log.i("log_tag",
							"server row" + ": " + json_data.getString("name"));
				}
			} catch (JSONException e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
			}
			hidePDialog();
		}
	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public class loadRestaurants extends AsyncTask<String, integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// get search result
			result = restaurant
					.getAll("http://bcminfo.bugs3.com/wms/api/controller/restaurants.php?method=getall");

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {

					json_data = jArray.getJSONObject(i);
					// set up list view
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					final View view = inflater.inflate(
							R.layout.restaurant_list, null);
					TextView titleTxt = (TextView) view
							.findViewById(R.id.title);
					TextView addressTxt = (TextView) view
							.findViewById(R.id.address);
					NetworkImageView thumbNail = (NetworkImageView) view
							.findViewById(R.id.thumbnail);
					TextView ratingTxt = (TextView) view
							.findViewById(R.id.rating);
					TextView statusTxt = (TextView) view
							.findViewById(R.id.status);
					titleTxt.setText(json_data.getString("name"));
					addressTxt.setText(json_data.getString("address"));
					ratingTxt.setVisibility(View.GONE);
					statusTxt.setText("2.5 mi");
					thumbNail.setImageUrl(
							"http://bcminfo.bugs3.com/wms/api/images/"
									+ json_data.getString("icon_path"),
							imageLoader);
					final String id = json_data.getString("id");
					view.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Thread detailThread = new Thread() {
								public void run() {
									Intent openDetail = new Intent(
											"com.whosmyserver.app.ResDetailActivity");
									try {
										openDetail.putExtra("id", id);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										Log.e("Error opening ResDetailActivity",
												e.toString());
									}
									startActivity(openDetail);

								}

							};
							detailThread.start();
						}
					});
					Wrapper.addView(view);

				}
			} catch (JSONException e) {
				Log.e("Loading Restaurants Error", e.toString());
			}

			hidePDialog();
		}

	}

}
