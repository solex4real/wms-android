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
import com.whosmyserver.adapter.RestaurantAdapter;
import com.whosmyserver.adapter.RestaurantListAdapter;
import com.whosmyserver.adapter.ServersAdapter;
import com.whosmyserver.app.MainActivity;
import com.whosmyserver.app.R;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.fragment.RestaurantFragment.loadRestaurants;
import com.whosmyserver.model.Restaurant;
import com.whosmyserver.web.restaurant;

public class ServerFragment extends Fragment{
	
	// Restaurants json url
		private static final String TAG = MainActivity.class.getSimpleName();
		private ProgressDialog pDialog;
		private List<Restaurant> restaurantList = new ArrayList<Restaurant>();
		private ListView listView;
		private ServersAdapter adapter;

		private ArrayList<String> nameList = new ArrayList<String>();
		private ArrayList<String> iconList = new ArrayList<String>();
		private ArrayList<String> ratingList = new ArrayList<String>();
		
public ServerFragment(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_servers, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_servers);
		
        
        new loadServers().execute();
        
        return rootView;
    }
    
    private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}
    public class loadServers extends AsyncTask<String, integer, String> {

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
					.getAll("http://bcminfo.bugs3.com/wms/api/controller/servers.php?method=getbyallid&id=5");

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
					
					nameList.add(obj.getString("name"));
					iconList.add(obj.getString("image_path"));
					ratingList.add(obj.getString("rating"));

					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			adapter = new ServersAdapter(getActivity(), iconList, nameList, ratingList );
			listView.setAdapter(adapter);
			
			hidePDialog();
		}
	}

}
