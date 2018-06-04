package com.whosmyserver.app;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.whosmyserver.adapter.NavDrawerListAdapter;
import com.whosmyserver.adapter.SearchListAdapter;
import com.whosmyserver.controller.AppController;
import com.whosmyserver.fragment.FavoriteFragment;
import com.whosmyserver.fragment.HomeFragment;
import com.whosmyserver.fragment.NewHomeFragment;
import com.whosmyserver.fragment.RatingFragment;
import com.whosmyserver.fragment.RecentFragment;
import com.whosmyserver.fragment.ServerFragment;
import com.whosmyserver.fragment.RestaurantListFragment;
import com.whosmyserver.helper.sqliteData;
import com.whosmyserver.model.NavDrawerItem;
import com.whosmyserver.model.userData;
import com.whosmyserver.util.CircledNetworkImageView;
import com.whosmyserver.web.pagecontent;
import com.whosmyserver.web.restaurant;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.R.integer;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SearchView.OnSuggestionListener;
import android.widget.TextView;
import android.widget.Toast;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class MainActivity extends FragmentActivity implements
		OnSuggestionListener {

	private String strId;
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// Search Components
	SearchView searchView;
	String result = "";
	private static final String[] SUGGESTIONS = { "Bauru", "Sao Paulo",
			"Rio de Janeiro", "Bahia", "Mato Grosso", "Minas Gerais",
			"Tocantins", "Rio Grande do Sul" };
	private static final String[] DESCRIPTIONS = {
			"1101 Beville Rd, South Daytona", "1101 Beville Rd, South Daytona",
			"1101 Beville Rd, South Daytona", "1101 Beville Rd, South Daytona",
			"1101 Beville Rd, South Daytona", "1101 Beville Rd, South Daytona",
			"1101 Beville Rd, South Daytona", "1101 Beville Rd, South Daytona" };
	ArrayList<String> listName = new ArrayList<String>();
	JSONObject json_data = new JSONObject();
	private Runnable loadSearch;
	private CursorAdapter mAdapter;


	// Fragments
	Fragment fragment1 = new HomeFragment();
	// Fragment fragment2 = new FavoriteFragment();

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	Fragment fragment = null;
	FragmentTransaction ft;
	FragmentManager fragmentManager;

	sqliteData db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Check for users on devices
		db = new sqliteData(this);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.nav_header, null);
		TextView txtName = (TextView) view.findViewById(R.id.title);
		CircledNetworkImageView thumbNail = (CircledNetworkImageView) view
				.findViewById(R.id.thumbnail);
		ImageLoader imageLoader = AppController.getInstance().getImageLoader();
		try {
			List<userData> userinfo = db.getAllUserData();
			userData userdata = userinfo.get(0);
			String name = userinfo.get(0).getName();
			String user_url = userinfo.get(0).getImagePath();
			if (user_url.trim().length() > 1) {
				thumbNail.setImageUrl(
						"http://bcminfo.bugs3.com/wms/api/images/" + user_url,
						imageLoader);
			}
			txtName.setText(name);
			// Log.e("Relsuts of data base", userdata.getStatus().toString());

			// txtDisp.setText("There is records");

		} catch (Exception e) {
			Log.e("Error from data base", e.toString());

		}

		ft = this.getFragmentManager().beginTransaction();
		fragmentManager = getFragmentManager();

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// Add header to list view
		mDrawerList.addHeaderView(view);

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1), true, "22"));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		// Drawable d = getResources().getDrawable(R.color.action_bg);
		// getActionBar().setBackgroundDrawable(d);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(1);
		}

	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchView = (SearchView) menu.findItem(R.id.search).getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));

		final MatrixCursor c = new MatrixCursor(new String[] { BaseColumns._ID,
				"Name", "Description" });
		mAdapter = new SearchListAdapter(getBaseContext(), c);
		searchView.setSuggestionsAdapter(mAdapter);
		searchView.setOnSuggestionListener(new OnSuggestionListener() {

			@Override
			public boolean onSuggestionSelect(int position) {
				// TODO Auto-generated method stub

				return false;
			}

			@Override
			public boolean onSuggestionClick(int position) {
				// TODO Auto-generated method stub
				try {
					JSONArray jArray = new JSONArray(result);
					json_data = jArray.getJSONObject(position);
					try {
						/*if (json_data.getString("type").trim().equals("1")) {
							strId = json_data.getString("id");
							displayView(7);
						} else {*/
							Thread detailThread = new Thread() {
								public void run() {
									Intent openDetail = new Intent(
											"com.whosmyserver.app.ResDetailActivity");
									try {
										openDetail.putExtra("id",
												json_data.getString("id"));
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									startActivity(openDetail);
								}
							};
							detailThread.start();
						//}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Log.e("Error opening ResDetailActivity", e.toString());
					}
					// }
					// };
					// detailThread.start();

					// json_data.getString("address");
				} catch (JSONException e) {

				}
				return false;
			}
		});
		// searchView.setSuggestionsAdapter(mAdapter);
		// searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				// Setup Search Suggestion Adapter
				
				final String[] query = { newText };
				new loadSearch().execute(query);
				return false;
			}

		});

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		if (fragment != null) {
			fragmentManager.beginTransaction().remove(fragment);
		}
		switch (position) {
		case 1:
			fragment = new NewHomeFragment();
			break;
		case 2:
			fragment = new FavoriteFragment();
			break;
		case 3:
			fragment = new RecentFragment();
			break;
		case 4:
			// fragment = new Fragment();
			break;
		case 5:
			fragment = new RatingFragment();
			break;
		case 6:
			// fragment = new WhatsHotFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			if(position<7){
			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			if (position > 0) {
				setTitle(navMenuTitles[position - 1]);
			} else {
				setTitle(navMenuTitles[position]);
			}

			mDrawerLayout.closeDrawer(mDrawerList);
			}else{
				setTitle("Results");
			}
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			// Toast.makeText(getApplicationContext(),
			// query, Toast.LENGTH_LONG).show();
			searchView.setQuery(query, false);
			// doSearch(query);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public class loadSearch extends AsyncTask<String, integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			// get search result
			result = restaurant
					.Find("http://bcminfo.bugs3.com/wms/api/controller/restaurants.php?method=find",
							arg0[0]);

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ArrayList<String> myAddress = new ArrayList<String>();
			ArrayList<String> myNames = new ArrayList<String>();
			try {
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					json_data = jArray.getJSONObject(i);
					myAddress.add(json_data.getString("address"));
					myNames.add(json_data.getString("name"));
					Log.i("log_tag",
							"about" + ": " + json_data.getString("name"));
				}
			} catch (JSONException e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
			}
			final MatrixCursor c = new MatrixCursor(new String[] {
					BaseColumns._ID, "Name", "Description" });
			/*
			 * for (int i=0; i<SUGGESTIONS.length; i++) { if
			 * (SUGGESTIONS[i].toLowerCase().startsWith(result.toLowerCase()))
			 * c.addRow(new Object[] {i, SUGGESTIONS[i], DESCRIPTIONS[i]}); }
			 */
			// John3:16
			for (int i = 0; i < myNames.size(); i++) {
				c.addRow(new Object[] { i, myNames.get(i), myAddress.get(i) });
			}
			// Cursor c1;
			mAdapter.changeCursor(c);
		}

	}

	@Override
	public boolean onSuggestionSelect(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSuggestionClick(int position) {
		// TODO Auto-generated method stub
		return false;
	}

}
