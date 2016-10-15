package com.qzl.qianggexinwen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qzl.qianggexinwen.MsgDetailActivity;
import com.qzl.qianggexinwen.R;
import com.qzl.qianggexinwen.adapter.FirstPageAdapter;
import com.qzl.qianggexinwen.bean.BannerBean;
import com.qzl.qianggexinwen.bean.Data;
import com.qzl.qianggexinwen.utils.JsonUtils;
import com.qzl.qianggexinwen.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面的fragment
 * Created by Qzl on 2016-10-09.
 */

public class FirstPageFragment extends Fragment implements JsonUtils.CallBackListener, SwipeRefreshLayout.OnRefreshListener {
    private ProgressDialogUtils mUtils;

    private List<Data> item_list; //记录当前真正要显示的数据
    private List<Data>[] msg_list;//保存了所有数据的集合数组
    private int now_num = 7;//记录当前存放的数据条数
    private int LOADNUM = 4;//每次属性加载的条数

    private int lastVisibleItem;//记录最后一个位置的下标
    private int mPosition;//记录当前存在的页面
    private RecyclerView mRecyclerView;

    private JsonUtils mJsonUtils; //获取数据的工具类
    private BannerBean mBannerBean;//存储轮播图数据对象

    private FirstPageAdapter mAdapter;
    private SwipeRefreshLayout mSwip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        getData();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FirstPageAdapter(getContext(), item_list, mBannerBean);
        mRecyclerView.setAdapter(mAdapter);
        initListener();

        mSwip = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        //设置进度刷新进度颜色
        mSwip.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light);
        mSwip.setOnRefreshListener(this);

        return view;
    }

    //给上拉实现事件监听
    private void initListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //滚动状太发生变化时监听
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //RecyclerView.SCROLL_STATE_IDLE:滑动条处于停止状太
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    //执行更新操作
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            now_num += LOADNUM;
                            initData();
                        }
                    }, 1000);
                }
            }

            //滚动时监听
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = lm.findLastVisibleItemPosition();//找到最后一条条目
            }
        });

        mAdapter.setMyItemClickListener(new FirstPageAdapter.MyItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
//                System.out.println("onClick-----position="+position);
                String url = item_list.get(position - 1).getUrl();
                Intent intent = new Intent(getActivity(), MsgDetailActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("position",mPosition);
                startActivity(intent);
            }
        });
    }

    //回调的更新界面的方法
    @Override
    public void upData(List<Data>[] msg_list) {
        //关闭弹窗
        mUtils.closeProgressDialog();
        this.msg_list = msg_list;
        initData();//更新数据，重新分配数据，去填充界面
    }

    //分配数据，填充布局
    private void initData() {
        if (msg_list != null) {
            String[] img = new String[3];
            String[] title = new String[3];
            String[] toUrl = new String[3];
            item_list.clear();//清空当前有的数据

            List<Data> data = msg_list[mPosition];
            for (int i = 0; i < 3; i++) {
                img[i] = data.get(i).getThumbnail();
                title[i] = data.get(i).getTitle();
                toUrl[i] = data.get(i).getUrl();
            }
            mBannerBean.setImg_url(img);
            mBannerBean.setTitle(title);
            mBannerBean.setToUrl(toUrl);

            for (int i = 3; i < now_num; i++) {
                item_list.add(data.get(i));
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    //获取网络数据的方法
    public void getData() {
        //轮播视图和数据集合的初始化
        item_list = new ArrayList<>();
        mBannerBean = new BannerBean();
        //初始化弹窗Utils
        mUtils = new ProgressDialogUtils();
        //注册本类去监听数据加载状太
        mJsonUtils = new JsonUtils(this);

        //加载数据
        mJsonUtils.getResult();

        //显示弹窗
        mUtils.showProgressDialog(getActivity(), "loading...");
    }

    //下拉刷新方法回调
    @Override
    public void onRefresh() {
        now_num += LOADNUM; //刷新操作执行后，多显示几条数据
        initData();
        mSwip.setRefreshing(false);//设置组件的刷新状太
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    //滑动时，更新数据
    public void setPosotion(int position) {
        mPosition = position;
        initData();
    }
}
