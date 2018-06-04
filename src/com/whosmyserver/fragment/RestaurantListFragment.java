package com.whosmyserver.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.whosmyserver.adapter.RestaurantListAdapter2;
import com.whosmyserver.app.MainActivity;
import com.whosmyserver.app.R;
import com.whosmyserver.model.Restaurant;
import com.whosmyserver.web.restaurant;

public class RestaurantListFragment extends Fragment {

	// Restaurants json url
	private static final String TAG = MainActivity.class.getSimpleName();
	// private static final String url =
	// "http://api.androidhive.info/json/movies.json";
	private ProgressDialog pDialog;
	private List<Restaurant> restaurantList = new ArrayList<Restaurant>();
	private ListView listView;
	private RestaurantListAdapter2 adapter;

	public RestaurantListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_restaurant,
				container, false);

		listView = (ListView) rootView.findViewById(R.id.list_restaurant);
		
		//new loadRestaurants().execute();

		// Adding request to request queue
		// AppController.getInstance().addToRequestQueue(movieReq);

		return rootView;
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
			try {
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {

					JSONObject obj = jArray.getJSONObject(i);
					Restaurant restaurant = new Restaurant();
					restaurant.setTitle(obj.getString("name"));
					ArrayList<String> address = new ArrayList<String>();
					address.add((String) obj.getString("address"));
					restaurant.setAddress(address);
					restaurant.setThumbnailUrl(obj.getString("icon_path"));
					restaurant.setFoodType(obj.getString("food_type"));

					// adding restaurants to restaurant array
					restaurantList.add(restaurant);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			adapter = new RestaurantListAdapter2(getActivity(), restaurantList);
			listView.setAdapter(adapter);
			
			hidePDialog();
		}
	}

}
