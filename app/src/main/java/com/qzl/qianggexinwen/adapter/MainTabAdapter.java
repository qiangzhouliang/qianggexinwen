package com.qzl.qianggexinwen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qzl.qianggexinwen.fragment.FirstPageFragment;

import java.util.List;

/**
 * tablayout 的适配器
 * Created by Qzl on 2016-10-09.
 */

public class MainTabAdapter extends FragmentPagerAdapter{

    private List<FirstPageFragment> mList_fragment;
    private String[] mList_title;
    public MainTabAdapter(FragmentManager fm, List<FirstPageFragment> list_fragment, String[] list_title) {
        super(fm);
        mList_fragment = list_fragment;
        mList_title = list_title;
    }

    @Override
    public Fragment getItem(int position) {
        return mList_fragment.get(position);
    }

    @Override
    public int getCount() {
        return mList_fragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList_title[position];
    }
}
