package com.whosmyserver.adapter;

import com.whosmyserver.fragment.PendingFragment;
import com.whosmyserver.fragment.RatedFragment;
import com.whosmyserver.fragment.RestaurantFragment;
import com.whosmyserver.fragment.ServerFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class RatingTabPagerAdapter  extends FragmentStatePagerAdapter{

	public RatingTabPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int i) {
		// TODO Auto-generated method stub
		switch (i) {
        case 0:
            //Fragement for Android Tab
            return new PendingFragment();
        case 1:
           //Fragment for Ios Tab
            return new RatedFragment();
        }
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
