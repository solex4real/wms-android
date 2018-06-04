package com.whosmyserver.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.whosmyserver.adapter.RestaurantListAdapter;
import com.whosmyserver.adapter.ServersAdapter;
import com.whosmyserver.app.MainActivity;
import com.whosmyserver.app.R;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.model.Restaurant;

public class RatedFragment extends Fragment {

	// Restaurants json url
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String url = "http://bcminfo.bugs3.com/wms/api/controller/servers.php?method=getbyallid&id=5";
	private ProgressDialog pDialog;
	private List<Restaurant> restaurantList = new ArrayList<Restaurant>();
	private ListView listView;
	private ServersAdapter adapter;

	private ArrayList<String> nameList = new ArrayList<String>();
	private ArrayList<String> iconList = new ArrayList<String>();
	private ArrayList<String> ratingList = new ArrayList<String>();

	public RatedFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_restaurant,
				container, false);

		listView = (ListView) rootView.findViewById(R.id.list_restaurant);

		pDialog = new ProgressDialog(getActivity());
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();

		// Creating volley request obj
		JsonArrayRequest Req = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						hidePDialog();

						// Parsing json
						try {
							JSONArray jArray = new JSONArray(
									response.toString());
							for (int i = 0; i < jArray.length(); i++) {

								JSONObject obj = jArray.getJSONObject(i);

								nameList.add(obj.getString("name"));
								iconList.add(obj.getString("image_path"));
								ratingList.add(obj.getString("rating"));
							}
							adapter = new ServersAdapter(getActivity(),
									iconList, nameList, ratingList);
							listView.setAdapter(adapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						hidePDialog();
						// notifying list adapter about data changes
						// so that it renders the list view with updated data
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hidePDialog();

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(Req);

		return rootView;
	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}

}
