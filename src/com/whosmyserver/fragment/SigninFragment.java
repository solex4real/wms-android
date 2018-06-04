package com.whosmyserver.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.whosmyserver.app.R;
import com.whosmyserver.helper.sqliteData;
import com.whosmyserver.model.userData;
import com.whosmyserver.web.users;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SigninFragment extends Fragment implements OnClickListener {

	private View rootView = null;

	private int loginType = 1;

	private Runnable uploadUser;

	EditText username, password;

	Button btnSignin;

	Dialog pDialog;

	private FragmentActivity myContext;

	sqliteData db;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_signin, container, false);

		db = new sqliteData(myContext);
		username = (EditText) rootView.findViewById(R.id.username);
		password = (EditText) rootView.findViewById(R.id.password);
		btnSignin = (Button) rootView.findViewById(R.id.btn_signin);
		btnSignin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new getUserInfo().execute();
			}
		});

		return rootView;
	}

	public class getUserInfo extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(myContext, "Please wait...",
					"Getting User Data...", true);
			Thread thread = new Thread(null, uploadUser,
					"MultibreifsBackground");
			thread.start();
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			String result = users
					.getUserInfo(
							// "http://10.0.2.2/bcm/api/controller.php?method=LoginUsers",
							"http://bcminfo.bugs3.com/wms/api/controller/user.php?method=Login",
							username.getText().toString(), password.getText()
									.toString());
			return result;

		}

		protected void onPostExecute(String result) {
			// dismiss the dialog once product deleted
			super.onPostExecute(result);

			if (Character.toString(result.charAt(0)).equals("0")) {
				if (loginType == 0) {
					Toast.makeText(myContext.getApplicationContext(), "Failed",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(myContext.getApplicationContext(),
							"Invalid Login", Toast.LENGTH_LONG).show();
				}
				pDialog.dismiss();
			} else {

				// String id, String username, String password, String name,
				// String school, String others,
				// String likes,String groups, String favorites, String status,
				// String userpriv
				try {
					JSONArray jArray = new JSONArray(result);
					JSONObject json_data = jArray.getJSONObject(0);

					// Store values into my sqlite database
					db.addUserData(new userData(json_data.getString("id"),
							json_data.getString("username"), json_data
									.getString("password"), json_data
									.getString("name"), json_data
									.getString("email"), json_data
									.getString("image_path"), json_data
									.getString("status")));
					Toast.makeText(myContext.getApplicationContext(),
							"Login Successful", Toast.LENGTH_LONG).show();
					pDialog.dismiss();
					
					//Start main activity 
					Thread mainThread = new Thread() {
						public void run() {
							Intent openSignin = new Intent(
									"com.whosmyserver.app.MainActivity");
							startActivity(openSignin);
						}
					};
					mainThread.start();
				} catch (JSONException e) {
					pDialog.dismiss();
					Toast.makeText(myContext.getApplicationContext(),
							"Unable to Signin\ncheck internet", Toast.LENGTH_LONG).show();
					Log.e("log_tag", "Error parsing data " + e.toString());
				}

			}
			// pDialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		myContext = (FragmentActivity) activity;
		super.onAttach(activity);
	}

}
