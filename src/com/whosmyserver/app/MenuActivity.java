package com.whosmyserver.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.whosmyserver.adapter.MenuAdapter;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.util.CircledNetworkImageView;
import com.whosmyserver.web.servers;

import android.R.integer;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

public class MenuActivity extends Activity {

	NetworkImageView resThumb;
	CircledNetworkImageView serverThumb;
	TextView serverName;
	RatingBar rating;
	String id, thumb, resthumb, name, ratingVal, resname, resaddress;
	Button btnMenu;
	GridView foodWrapper;

	String result;
	JSONObject json_data = new JSONObject();
	private ProgressDialog pDialog;
	private Dialog popupview;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	ArrayList<String> nameList = new ArrayList<String>();
	ArrayList<String> iconList = new ArrayList<String>();
	ArrayList<String> priceList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		// getActionBar().setBackgroundDrawable(new
		// ColorDrawable(Color.parseColor("#80212121")));
		setContentView(R.layout.menu_activity);

		// Get arguments
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("id");
		thumb = bundle.getString("thumb");
		resthumb = bundle.getString("resthumb");
		name = bundle.getString("name");
		resname = bundle.getString("resname");

		setTitle(resname);
		// resThumb = (NetworkImageView) findViewById(R.id.restaurant_bg);
		serverThumb = (CircledNetworkImageView) findViewById(R.id.server_thumb);
		serverName = (TextView) findViewById(R.id.name);
		// rating = (RatingBar) findViewById(R.id.rating);
		foodWrapper = (GridView) findViewById(R.id.menu_wrapper);

		// resThumb.setImageUrl("http://bcminfo.bugs3.com/wms/api/images/"
		// + resthumb, imageLoader);
		serverThumb.setImageUrl("http://bcminfo.bugs3.com/wms/api/images/"
				+ thumb, imageLoader);
		serverName.setText(name);
		// rating.setRating(Integer.parseInt(bundle.getString("rating")));

		btnMenu = (Button) findViewById(R.id.menu_btn);
		btnMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread MenuListThread = new Thread() {
					public void run() {
						Intent openList = new Intent(
								"com.whosmyserver.app.MenuListActivity");
						openList.putExtra("id",id);
						openList.putExtra(resname, "resname");
						startActivity(openList);
					}
				};
				MenuListThread.start();
			}
		});
		
		TextView txtName = (TextView) findViewById(R.id.res_name);
		TextView txtAddress = (TextView) findViewById(R.id.res_address);
		TextView txtTime = (TextView) findViewById(R.id.res_time);
		TextView txtDate = (TextView) findViewById(R.id.res_date);
		TextView txtNum = (TextView) findViewById(R.id.res_number);
		TextView txtEmail = (TextView) findViewById(R.id.res_email);

		txtName.setText(resname);
		txtAddress.setText(resaddress);
		txtTime.setBackgroundResource(R.color.red);
		txtTime.setText("Not available");
		txtDate.setText("Mon - Sun");
		txtNum.setText("323-485-6767");
		txtEmail.setText("email@mail.com");
		
		// new loadFood().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// return super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.server, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.rate_btn:
			// Inflate popup view
			popupview = new Dialog(MenuActivity.this);
			popupview.setContentView(R.layout.rate_layout);
			// popupview.setCancelable(false);
			// popupview.setCanceledOnTouchOutside(false);
			Button btnDone = (Button) popupview.findViewById(R.id.btn_rate);
			btnDone.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupview.dismiss();
				}
			});
			Window window = popupview.getWindow();
			window.setLayout(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			// popupview.setCancelable(false);
			popupview.setTitle("RATE");
			popupview.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * public class loadFood extends AsyncTask<String, integer, String> {
	 * 
	 * @Override protected void onPreExecute() { // TODO Auto-generated method
	 * stub super.onPreExecute(); pDialog = new
	 * ProgressDialog(MenuActivity.this); // Showing progress dialog before
	 * making http request pDialog.setMessage("Loading..."); pDialog.show(); }
	 * 
	 * @Override protected String doInBackground(String... params) { // TODO
	 * Auto-generated method stub // get search result String result = servers
	 * .GetAllbyId(
	 * "http://bcminfo.bugs3.com/wms/api/controller/foods.php?method=getall",
	 * id);
	 * 
	 * return result; }
	 * 
	 * @Override protected void onPostExecute(String result) { // TODO
	 * Auto-generated method stub super.onPostExecute(result);
	 * 
	 * try { JSONArray jArray = new JSONArray(result); for (int i = 0; i <
	 * jArray.length(); i++) { // Add views // set up list view json_data =
	 * jArray.getJSONObject(i); nameList.add(json_data.getString("name"));
	 * priceList.add(json_data.getString("price"));
	 * iconList.add(json_data.getString("image_path")); } } catch (JSONException
	 * e) { Log.e("Error", e.toString()); } // Setup Adapter MenuAdapter
	 * mAdapter = new MenuAdapter(MenuActivity.this, iconList, nameList,
	 * priceList); foodWrapper.setAdapter(mAdapter);
	 * 
	 * 
	 * hidePDialog(); }
	 * 
	 * }
	 */

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}

}
