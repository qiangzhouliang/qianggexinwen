package com.qzl.qianggexinwen;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.qzl.qianggexinwen.adapter.MainTabAdapter;
import com.qzl.qianggexinwen.fragment.FirstPageFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout; //顶部标题选项布局
    private ViewPager mViewPager;

    private FirstPageFragment mFragment;

    private List<FirstPageFragment> mFirstFragment;//存放fragment的集合
    private String[] mList_title;//存放标题

    private MainTabAdapter mAdapter_title; //标题的适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();

        initListener();

    }

    //单击事件的监听
    private void initListener() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //tab被选中或点击的事件回调
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                System.out.println("onTabSelected: ----->"+tab.getPosition());
                int position = tab.getPosition();
                for (int i = 0; i < mFirstFragment.size(); i++) {
                    mFirstFragment.get(position).setPosotion(position);
                }
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initData() {
        mList_title = getResources().getStringArray(R.array.tab_title);
        mFirstFragment = new ArrayList<>();

        //通过标题栏个数来创建fragment
        for (int i = 0; i < mList_title.length; i++) {
            FirstPageFragment first = new FirstPageFragment();
            mFirstFragment.add(first);
        }
    }

    //初始化布局
    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_title);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mAdapter_title = new MainTabAdapter(getSupportFragmentManager(),mFirstFragment,mList_title);
        mViewPager.setAdapter(mAdapter_title);

        //TabLayout 绑定viewPager
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
