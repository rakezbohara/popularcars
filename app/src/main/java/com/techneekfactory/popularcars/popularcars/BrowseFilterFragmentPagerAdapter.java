package com.techneekfactory.popularcars.popularcars;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


public class BrowseFilterFragmentPagerAdapter extends FragmentPagerAdapter {

    private final String[] tabTitle = new String[]{"Make", "BodyType", "Price"};

    private FilterByBrandFragment filterByBrandFragment = null;
    private FilterByBodyTypeFragment filterByBodyTypeFragment = null;
    private FilterByPriceFragment filterByPriceFragment = null;


    public BrowseFilterFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {


        Log.d("Tab:" , String.valueOf(position));
        switch (position)
        {
            case 0:
                if(filterByBodyTypeFragment == null) filterByBrandFragment = new FilterByBrandFragment();
                return filterByBrandFragment;


            case 1:
                if(filterByBodyTypeFragment == null) filterByBodyTypeFragment = new FilterByBodyTypeFragment();
                return filterByBodyTypeFragment;


            case 2:
                if(filterByPriceFragment == null) filterByPriceFragment = new FilterByPriceFragment();
                return  filterByPriceFragment;

        }
return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
