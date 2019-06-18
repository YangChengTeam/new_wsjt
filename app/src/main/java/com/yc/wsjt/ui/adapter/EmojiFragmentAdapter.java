package com.yc.wsjt.ui.adapter;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


public class EmojiFragmentAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;
    private List<String> titles;

    public EmojiFragmentAdapter(FragmentManager fragmentManager, Fragment[] listFragments, List<String> titles) {
        super(fragmentManager);
        this.fragments = listFragments;
        this.titles = titles;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    //重写这个方法，将设置每个Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

}