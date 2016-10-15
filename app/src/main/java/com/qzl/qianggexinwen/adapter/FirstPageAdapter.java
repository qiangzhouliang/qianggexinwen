package com.qzl.qianggexinwen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qzl.qianggexinwen.R;
import com.qzl.qianggexinwen.bean.BannerBean;
import com.qzl.qianggexinwen.bean.Data;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

/**
 * Created by Qzl on 2016-10-12.
 */

public class FirstPageAdapter extends RecyclerView.Adapter {

    private final int TYPE_HEAD = 0; //表示首个位置，直接显示轮播图片
    private final int TYPE_NORMAL = 1; //表示正常的 item 布局
    private final int TYPE_FOOT = 2; //表示刷新布局

    private Context mContext;
    private List<Data> item_data;//轮播图片的路径
    private BannerBean mBannerBean;


    public FirstPageAdapter(Context context, List<Data> banner_url, BannerBean bean) {
        this.mContext = context;
        this.item_data = banner_url;
        this.mBannerBean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_HEAD) {
            //此处要创建顶部banner的viewHolder
            holder = new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.itemheader_banner, parent, false));
        } else if (viewType == TYPE_FOOT) {
            holder = new FootViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent, false));
        } else if (viewType == TYPE_NORMAL) {
            holder = new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_firstfragment, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BannerViewHolder) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);//设置圆形指示器
            bannerViewHolder.mBanner.setBannerTitle(mBannerBean.getTitle());
            bannerViewHolder.mBanner.setImages(mBannerBean.getImg_url());

        } else if (holder instanceof ItemViewHolder) {
            //处理每个item里面的布局
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mSimpleDraweeView.setImageURI(item_data.get(position - 1).getThumbnail());
            itemViewHolder.mTextView.setText(item_data.get(position - 1).getTitle());

            if (mListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onClick(v, position);
                    }
                });
            }
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            //判断有数据时，在显示底部加载布局
            if (item_data.size() > 0) {
                footViewHolder.progress_lin.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (item_data != null && item_data.size() > 0) {
            return item_data.size() + 1 + 1;
        } else {
            return 0;
        }
    }

    //告诉创建什么类型的viewHolder
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position + 1 == getItemCount()) {
            return TYPE_FOOT;
        } else {
            return TYPE_NORMAL;
        }
    }

    //正常的item的布局管理
    class ItemViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mSimpleDraweeView;
        TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mSimpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.draweeView);
            mTextView = (TextView) itemView.findViewById(R.id.news_text);
        }
    }

    //首位的banner
    class BannerViewHolder extends RecyclerView.ViewHolder {

        Banner mBanner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            mBanner = (Banner) itemView.findViewById(R.id.banner);
        }
    }

    //底部刷新的视图
    class FootViewHolder extends RecyclerView.ViewHolder {
        LinearLayout progress_lin;

        public FootViewHolder(View itemView) {
            super(itemView);
            progress_lin = (LinearLayout) itemView.findViewById(R.id.progress_lin);
        }
    }

    //recycleView的单击事件回调接口
    public interface MyItemClickListener {
        void onClick(View itemView, int position);
    }

    //本类中保存一个接口的引用
    private MyItemClickListener mListener;

    public void setMyItemClickListener(MyItemClickListener listener) {
        mListener = listener;
    }
}
