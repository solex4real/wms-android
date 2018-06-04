package com.whosmyserver.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.whosmyserver.adapter.RecentListAdapter;
import com.whosmyserver.adapter.RestaurantAdapter;
import com.whosmyserver.adapter.RestaurantListAdapter;
import com.whosmyserver.app.MainActivity;
import com.whosmyserver.app.R;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.helper.sqliteData;
import com.whosmyserver.helper.sqliteRecentData;
import com.whosmyserver.model.Restaurant;
import com.whosmyserver.model.recentData;
import com.whosmyserver.model.userData;
import com.whosmyserver.web.restaurant;

public class RecentFragment extends Fragment {

	// Restaurants json url
	private static final String TAG = MainActivity.class.getSimpleName();
	// private static final String url =
	// "http://api.androidhive.info/json/movies.json";
	private ProgressDialog pDialog;
	private List<recentData> restaurantList = new ArrayList<recentData>();
	private ListView listView;
	private RecentListAdapter adapter;
	private FragmentActivity myContext;
	sqliteRecentData db;

	public RecentFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_restaurant,
				container, false);

		
		db = new sqliteRecentData(myContext);
		listView = (ListView) rootView.findViewById(R.id.list_restaurant);
		
		//new loadRestaurants().execute();
		
		List<recentData> info = db.getAllData();
		for(int i = 0; i < info.size(); i++){
			recentData recentdata = info.get(i);
			restaurantList.add(recentdata);
		}
		adapter = new RecentListAdapter(getActivity(), restaurantList);
		listView.setAdapter(adapter);
		// Adding request to request queue
		// AppController.getInstance().addToRequestQueue(movieReq);

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		myContext=(FragmentActivity) activity;
	}
	
	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}

	public class loadRestaurants extends AsyncTask<String, integer, String> {

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

			// Parsing json
			
			List<recentData> info = db.getAllData();
			for(int i = 0; i < info.size(); i++){
				recentData recentdata = info.get(i);
				restaurantList.add(recentdata);
			}
			
			/*
			try {
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					
					
					
					JSONObject obj = jArray.getJSONObject(i);
					recentData restaurant = new recentData();
					restaurant.setId(obj.getString("id"));
					restaurant.setName(obj.getString("name"));
					restaurant.setImagePath(obj.getString("icon_path"));
					restaurant.setAddress(obj.getString("address"));
					restaurant.setType(obj.getString("food_type"));
					restaurant.setDate("4/9/2015");

					// adding restaurants to restaurant array
					restaurantList.add(restaurant);
					db.addData(restaurant);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			*/
			adapter = new RecentListAdapter(getActivity(), restaurantList);
			listView.setAdapter(adapter);
			
			hidePDialog();
		}
	}

}
