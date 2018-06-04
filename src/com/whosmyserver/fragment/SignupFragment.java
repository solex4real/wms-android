package com.whosmyserver.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.whosmyserver.app.R;
import com.whosmyserver.fragment.SigninFragment.getUserInfo;
import com.whosmyserver.helper.sqliteData;
import com.whosmyserver.model.userData;
import com.whosmyserver.web.users;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignupFragment extends Fragment implements OnClickListener {

	private View rootView = null;
	TextView editName, editUsername, editPassword1, editPassword2, editEmail,
			editPhone;
	Button btnCancel, btnSignin, btnSignup;
	ImageView thumb;
	String imagePath = "";
	private String url = "http://bcminfo.bugs3.com/wms/api/controller/user.php?method=register";
	Dialog pDialog;
	private Runnable uploadUser;

	sqliteData db;
	private FragmentActivity myContext;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_signup, container, false);

		// Get user phone number
		TelephonyManager tMgr = (TelephonyManager) myContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String mPhoneNumber = tMgr.getLine1Number();

		editName = (TextView) rootView.findViewById(R.id.edit_name);
		editUsername = (TextView) rootView.findViewById(R.id.edit_username);
		editPassword1 = (TextView) rootView.findViewById(R.id.edit_password1);
		editPassword2 = (TextView) rootView.findViewById(R.id.edit_password2);
		editEmail = (TextView) rootView.findViewById(R.id.edit_email);
		editPhone = (TextView) rootView.findViewById(R.id.edit_phone);
		btnCancel = (Button) rootView.findViewById(R.id.cancel);
		btnSignin = (Button) rootView.findViewById(R.id.btn_signin);
		btnSignup = (Button) rootView.findViewById(R.id.btn_signup);
		thumb = (ImageView) rootView.findViewById(R.id.user_thumb);

		editPhone.setText(mPhoneNumber);
		thumb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"), 1);
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imagePath = "";
				thumb.setImageResource(R.drawable.avatar);
				btnCancel.setVisibility(View.GONE);
			}
		});
		/*

		btnSignin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

			}
		});
		*/
		btnSignup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new SignupUser().execute();
			}
		});

		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == myContext.RESULT_OK) {
			if (requestCode == 1) {
				// currImageURI is the global variable I’m using to hold the
				// content:
				btnCancel.setVisibility(View.VISIBLE);
				Uri currImageURI = data.getData();
				imagePath = getRealPathFromURI(currImageURI);
				if(imagePath.trim().length()==0){
					imagePath = "";
					Toast.makeText(myContext.getApplicationContext(), "unable to use image\nselect image from gallery",
							Toast.LENGTH_LONG).show();
				}else{
					thumb.setImageURI(currImageURI);
				}
			}
		}
	}

	// Convert the image URI to the direct file system path of the image file
	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		CursorLoader cursorLoader = new CursorLoader(
	            myContext, 
	            contentUri, proj, null, null, null);        
	  Cursor cursor = cursorLoader.loadInBackground();
	  
	  int column_index = 
	    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	  cursor.moveToFirst();
	  return cursor.getString(column_index); 
	}

	public class SignupUser extends AsyncTask<String, String, String> {

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

			String result = users.addUser(url, editName.getText().toString(),
					editUsername.getText().toString(), editPassword1.getText()
							.toString(), editPhone.getText().toString(),
					editEmail.getText().toString(), imagePath);
			String result1 = "0";
			try{
				//JSONArray jArray = new JSONArray(result);
				JSONObject json_data = new JSONObject(result);
				result1 = json_data.getString("id");
			}catch(Exception e){
				Log.e("log_tag", "Error " + e.toString());
			}
			return result1;

		}

		protected void onPostExecute(String result) {
			// dismiss the dialog once product deleted
			super.onPostExecute(result);
			
			Toast.makeText(myContext.getApplicationContext(), result,
					Toast.LENGTH_LONG).show();
			pDialog.dismiss();
			if (Character.toString(result.charAt(0)).equals("0")) {
				Toast.makeText(myContext.getApplicationContext(), "Failed",
						Toast.LENGTH_LONG).show();
				pDialog.dismiss();
			} else {
				try {
					String filetype = imagePath.substring(imagePath
							.lastIndexOf(".") + 1);
					// Store values into my sqlite database
					db = new sqliteData(myContext);
					db.addUserData(new userData(result, editUsername.getText()
							.toString(), editPassword1.getText().toString(),
							editName.getText().toString(), editEmail.getText()
									.toString(), "user_picture/" + result + "."
									+ filetype, "1"));

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
				} catch (Exception e) {
					pDialog.dismiss();
					Toast.makeText(myContext.getApplicationContext(), "Failed",
							Toast.LENGTH_LONG).show();
					Log.e("log_tag", "Error " + e.toString());
				}
			}
			// pDialog.dismiss();
		}
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		myContext = (FragmentActivity) activity;
		super.onAttach(activity);
	}

}
