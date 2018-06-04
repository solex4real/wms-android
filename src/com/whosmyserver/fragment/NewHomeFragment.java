package com.whosmyserver.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.whosmyserver.app.R;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.fragment.HomeFragment.loadfiles;
import com.whosmyserver.model.Restaurant;
import com.whosmyserver.web.restaurant;

import android.R.integer;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class NewHomeFragment extends Fragment {

	public NewHomeFragment() {
	}

	private View rootView = null;
	double longitude;
	double latitude;

	// Restaurants json url
	private static final String url = "http://bcminfo.bugs3.com/wms/api/controller/yelp_search.php";
	String CurrentPos = "";
	String result;
	JSONObject json_data = new JSONObject();
	private List<Restaurant> restaurantList = new ArrayList<Restaurant>();
	private ProgressDialog pDialog;
	private LinearLayout restWrapper1, restWrapper;
	LocationManager lm;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_home2, container, false);

		// List View Wrapper
		restWrapper1 = (LinearLayout) rootView
				.findViewById(R.id.restaurant_yelp_nearby);
		restWrapper = (LinearLayout) rootView
				.findViewById(R.id.restaurant_wms_nearby);
		// Get current location
		lm = (LocationManager) this.getActivity()
				.getSystemService(Context.LOCATION_SERVICE);
		Location location = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
		try{
			longitude = location.getLongitude();
			latitude = location.getLatitude();
		}catch(Exception e){
			longitude = 37.786138600000001;
			latitude = -122.40262130000001;
		}

		CurrentPos = Double.toString(latitude) + ","
				+ Double.toString(longitude);
		

		//new loadYelp().execute();
		new loadWms().execute();
		
		
		return rootView;
	}

	@Override
	public void onDestroyView() { // TODO Auto-generated method
		super.onDestroyView();

	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}

	private void startPDialog() {
		
			pDialog = new ProgressDialog(getActivity());
			// Showing progress dialog before making http request
			pDialog.setMessage("Loading...");
			pDialog.show();
	}
	
	public class loadYelp extends AsyncTask<String, integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String url_tag = "http://api.yelp.com/v2/search?term=food&ll="
					+ CurrentPos + "&limit=20";
			// get online result
			result = restaurant.yelp(url, url_tag);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				// JSONArray jArray = new JSONArray(result);
				// for(int i=0;i<jArray.length();i++){
				// json_data = jArray.getJSONObject(i);
				JSONObject json = new JSONObject(result);
				JSONArray businesses;
				businesses = json.getJSONArray("businesses");
				for (int i = 0; i < businesses.length(); i++) {
					JSONObject business = businesses.getJSONObject(i);

					System.out.println(business.getString("name"));
					final Restaurant restaurant = new Restaurant();
					final String resPhone = business.getString("display_phone");
					restaurant.setTitle(business.getString("name"));
					restaurant.setThumbnailUrl(business.getString("image_url"));
					restaurant.setRating(((Number) business.get("rating"))
							.doubleValue());
					restaurant.setStatus(((Number) business.get("distance"))
							.doubleValue());

					// Address is json array
					JSONObject location = business.getJSONObject("location");
					// JSONArray address = location.getJSONArray("address");
					JSONArray addressArry = location
							.getJSONArray("display_address");
					ArrayList<String> address = new ArrayList<String>();
					for (int j = 0; j < addressArry.length(); j++) {
						address.add((String) addressArry.get(j));
					}
					restaurant.setAddress(address);

					// adding restaurants to restaurant array
					restaurantList.add(restaurant);
					// myList.add(json_data.getString("about"));
					// Log.i("log_tag","about"+": " +
					// json_data.getString("about"));

					// set up list view
					LayoutInflater inflater = (LayoutInflater) getActivity()
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					final View view = inflater.inflate(
							R.layout.restaurant_list, null);
					TextView titleTxt = (TextView) view
							.findViewById(R.id.title);
					titleTxt.setText(business.getString("name"));
					TextView addressTxt = (TextView) view
							.findViewById(R.id.address);

					// address
					String addressStr = "";
					for (String str : restaurant.getAddress()) {
						addressStr += str + ", ";
					}
					addressStr = addressStr.length() > 0 ? addressStr
							.substring(0, addressStr.length() - 2) : addressStr;
					addressTxt.setText(addressStr);

					TextView ratingTxt = (TextView) view
							.findViewById(R.id.rating);
					ratingTxt.setText(Double.toString(((Number) business
							.get("rating")).doubleValue()));
					TextView statusTxt = (TextView) view
							.findViewById(R.id.status);
					statusTxt.setText(Double.toString((double) Math
							.round((((Number) business.get("distance"))
									.doubleValue() * 0.000621371) * 10) / 10)
							+ " mi");
					NetworkImageView thumbNail = (NetworkImageView) view
							.findViewById(R.id.thumbnail);
					// thumbnail image
					final String addressVal = addressStr;
					thumbNail.setImageUrl(business.getString("image_url"),
							imageLoader);
					view.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							// FragmentTransaction ft =
							// getActivity().getFragmentManager().beginTransaction();

							Thread detailThread = new Thread() {
								public void run() {
									Intent openDetail = new Intent(
											"com.whosmyserver.app.DetailActivity");
									openDetail.putExtra("name",
											restaurant.getTitle());
									openDetail.putExtra("image_url", "");
									openDetail.putExtra("icon_url",
											restaurant.getThumbnailUrl());
									openDetail.putExtra("id", "");
									openDetail.putExtra("type", "yelp");
									openDetail.putExtra("date",
											"Monday - Sunday");
									openDetail.putExtra("time", "8-12");
									openDetail
											.putExtra("contact",resPhone);
									openDetail.putExtra("address", addressVal);
									startActivity(openDetail);
								}

							};
							detailThread.start();

						}
					});

					// view.setBackgroundResource(R.drawable.list1_row_selector);

					restWrapper1.addView(view);

				}
			} catch (Exception e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
			}
			// adapter = new RestaurantListAdapter(getActivity(),
			// restaurantList);
			// listView.setAdapter(adapter);
			hidePDialog();
			//new loadWms().execute();

		}

	}

	public class loadWms extends AsyncTask<String, integer, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			startPDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// get search result
			String result = restaurant
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
					LayoutInflater inflater = (LayoutInflater) getActivity()
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
										openDetail.putExtra("id",
												id);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										Log.e("Error opening ResDetailActivity", e.toString());
									}
									startActivity(openDetail);

								}

							};
							detailThread.start();
						}
					});
					restWrapper.addView(view);

				}
			} catch (JSONException e) {
					Log.e("Loading Restaurants Error", e.toString());
			}
			//new loadYelp().execute();
			hidePDialog();
		}

	}
	
}
