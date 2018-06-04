package com.whosmyserver.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.whosmyserver.adapter.RestaurantListAdapter;
import com.whosmyserver.adapter.TabPagerAdapter;
import com.whosmyserver.app.MainActivity;
import com.whosmyserver.app.R;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.helper.sqliteData;
import com.whosmyserver.model.Restaurant;
import com.whosmyserver.model.userData;
import com.whosmyserver.web.restaurant;

import android.R.integer;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.ActionBar.TabListener;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.renderscript.Double2;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HomeFragment extends Fragment implements TabListener,
		OnPageChangeListener {

	// Restaurants json url
	private static final String url = "http://bcminfo.bugs3.com/wms/api/controller/yelp_search.php";
	String CurrentPos = "";
	String result;
	JSONObject json_data = new JSONObject();
	private ProgressDialog pDialog;
	private List<Restaurant> restaurantList = new ArrayList<Restaurant>();
	private ListView listView;
	private RestaurantListAdapter adapter;
	private LinearLayout restWrapper;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	private MapFragment fragment;
	private GoogleMap map;
	// private Activity myContext = getActivity();
	// Log tag
	private static final String TAG = MainActivity.class.getSimpleName();
	private View rootView = null;

	double longitude;
	double latitude;

	private ViewPager viewPager;
	private TabPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Servers", "Restaurants" };
	private FragmentActivity myContext;

	sqliteData db;

	
	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_home, container, false);

		// Check for users on devices
		db = new sqliteData(myContext);
		/*

		Thread signinThread = new Thread() {
			public void run() {
				Intent openSignin = new Intent(
						"com.whosmyserver.app.SigninActivity");
				startActivity(openSignin);
			}
		};

		try {
			List<userData> userinfo = db.getAllUserData();
			userData userdata = userinfo.get(0);
			String val = userinfo.get(0).getStatus();
			Log.e("Relsuts of data base", userdata.getStatus().toString());
			if (Integer.parseInt(val) == 0) {
				//db.deleteUserData(userdata);
				//signinThread.start();
			}
			// txtDisp.setText("There is records");
		} catch (Exception e) {
			// txtDisp.setText("There is no records");
			// Signin Activity
			//signinThread.start();
		}

	*/
		/*
		 * 
		 * // Initilization viewPager = (ViewPager)
		 * rootView.findViewById(R.id.viewpager_2); actionBar =
		 * myContext.getActionBar(); mAdapter = new
		 * TabPagerAdapter(myContext.getSupportFragmentManager());
		 * 
		 * viewPager.setAdapter(mAdapter); actionBar.setHomeButtonEnabled(true);
		 * actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		 * 
		 * // Adding Tabs for (String tab_name : tabs) {
		 * actionBar.addTab(actionBar.newTab().setText(tab_name)
		 * .setTabListener(this)); } viewPager.setAdapter(mAdapter);
		 * viewPager.setOnPageChangeListener(new OnPageChangeListener() {
		 * 
		 * @Override public void onPageSelected(int position) { // TODO
		 * Auto-generated method stub
		 * actionBar.setSelectedNavigationItem(position); }
		 * 
		 * @Override public void onPageScrolled(int arg0, float arg1, int arg2)
		 * { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onPageScrollStateChanged(int arg0) { // TODO
		 * Auto-generated method stub
		 * 
		 * } });
		 */

		// List View Wrapper
		restWrapper = (LinearLayout) rootView
				.findViewById(R.id.restaurant_wrapper);
		imageLoader = AppController.getInstance().getImageLoader();
		// Get current location
		LocationManager lm = (LocationManager) this.getActivity()
				.getSystemService(Context.LOCATION_SERVICE);
		Location location = lm
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		longitude = location.getLongitude();
		latitude = location.getLatitude();
		CurrentPos = Double.toString(latitude) + ","
				+ Double.toString(longitude);
		listView = (ListView) rootView.findViewById(R.id.list1);
		listView.setScrollContainer(false);
		new loadfiles().execute();

		/*
		 * pDialog = new ProgressDialog(getActivity()); // Showing progress
		 * dialog before making http request pDialog.setMessage("Loading...");
		 * pDialog.show();
		 * 
		 * // Creating volley request obj JsonArrayRequest movieReq = new
		 * JsonArrayRequest(url, new Response.Listener<JSONArray>() {
		 * 
		 * @Override public void onResponse(JSONArray response) { Log.d(TAG,
		 * response.toString()); hidePDialog();
		 * 
		 * // Parsing json for (int i = 0; i < response.length(); i++) { try {
		 * 
		 * JSONObject obj = response.getJSONObject(i); Restaurant restaurant =
		 * new Restaurant(); restaurant.setTitle(obj.getString("title"));
		 * restaurant.setThumbnailUrl(obj .getString("image_url"));
		 * restaurant.setRating(((Number) obj .get("rating")).doubleValue());
		 * restaurant.setStatus(obj.getInt("distance"));
		 * 
		 * // Genre is json array JSONArray addressArry = obj
		 * .getJSONArray("address"); ArrayList<String> address = new
		 * ArrayList<String>(); for (int j = 0; j < addressArry.length(); j++) {
		 * address.add((String) addressArry.get(j)); }
		 * restaurant.setAddress(address);
		 * 
		 * // adding restaurants to restaurant array
		 * restaurantList.add(restaurant);
		 * 
		 * } catch (JSONException e) { e.printStackTrace(); }
		 * 
		 * }
		 * 
		 * // notifying list adapter about data changes // so that it renders
		 * the list view with updated data adapter.notifyDataSetChanged(); } },
		 * new Response.ErrorListener() {
		 * 
		 * @Override public void onErrorResponse(VolleyError error) {
		 * VolleyLog.d(TAG, "Error: " + error.getMessage()); hidePDialog();
		 * 
		 * } });
		 */

		// Adding request to request queue
		// AppController.getInstance().addToRequestQueue(movieReq);

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

	public class loadfiles extends AsyncTask<String, integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			// Showing progress dialog before making http request
			pDialog.setMessage("Loading...");
			pDialog.show();
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
									openDetail.putExtra("image_url",
											restaurant.getThumbnailUrl());
									openDetail.putExtra("icon_url", "");
									openDetail.putExtra("id", "");
									openDetail.putExtra("type", "yelp");
									openDetail.putExtra("date",
											"Monday - Sunday");
									openDetail.putExtra("time", "8-12");
									openDetail
											.putExtra("contact",
													"123-444-5555,restaurant@email.com");
									openDetail.putExtra("address", addressVal);
									startActivity(openDetail);
								}

							};
							detailThread.start();

						}
					});

					// view.setBackgroundResource(R.drawable.list1_row_selector);

					restWrapper.addView(view);

				}
			} catch (Exception e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
			}
			// adapter = new RestaurantListAdapter(getActivity(),
			// restaurantList);
			// listView.setAdapter(adapter);
			hidePDialog();

		}

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		myContext = (FragmentActivity) activity;
		super.onAttach(activity);

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
