package com.whosmyserver.app;

import com.whosmyserver.adapter.SigninAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

public class SigninActivity extends FragmentActivity {

	SigninAdapter pageAdapter;
	int pageId = 0;
	public static ViewPager pager;
	int currentPage = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_signin);
		pageAdapter = new SigninAdapter(getSupportFragmentManager());
		pager = (ViewPager) findViewById(R.id.signin_pager);
		pager.setAdapter(pageAdapter);

	}

	public void JumpToPage1(View view) {
		pager.setCurrentItem(1);
	}

	public void JumpToPage2(View view) {
		pager.setCurrentItem(2);
	}

}
