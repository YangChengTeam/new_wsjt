package com.yc.wsjt.ui.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.ViewGroup;

import java.util.List;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MyFragmentAdapter(FragmentManager fragmentManager, List<Fragment> list) {
        super(fragmentManager);
        this.fragments = list;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}