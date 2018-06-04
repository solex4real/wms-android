package com.whosmyserver.adapter;

import java.util.List;

import com.whosmyserver.fragment.RestaurantFragment;
import com.whosmyserver.fragment.ServerFragment;
import com.whosmyserver.fragment.SigninFragment;
import com.whosmyserver.fragment.SignupFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SigninAdapter extends FragmentPagerAdapter{

	
	public SigninAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		switch (position) {
        case 0:
            //Fragement for Singin Page
            return new SigninFragment();
        case 1:
           //Fragment for Signup Page
            return new SignupFragment();
        }
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
