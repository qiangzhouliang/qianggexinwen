package com.qzl.qianggexinwen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MsgDetailActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detail);
        mWebView = (WebView) findViewById(R.id.webView_msgDetail);

        //打开页面时自适应
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true);//可根据任意比例缩放

        int position = getIntent().getIntExtra("position",0);
        String url = getUrlByPosition(position);
        mWebView.loadUrl(url);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_msg);
        setSupportActionBar(toolbar);

        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        //设置回退按钮的事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getUrlByPosition(int position) {
        String oldUrl = getIntent().getStringExtra("url");
        StringBuilder url = new StringBuilder();
        //获取网页形式路劲中间的数字
        String num = getMainNum(oldUrl);
        switch (position){
            case 0://要闻
                url = url.append("http://inews.ifeng.com/").append(num).append("/news.shtml");
                break;
            case 1://财经
                url = url.append("http://ifinance.ifeng.com/").append(num).append("/news.shtml");
                break;
            case 2://体育
                url = url.append("http://isports.ifeng.com/").append(num).append("/news.shtml");
                break;
            case 3://军事
                url = url.append("http://imil.ifeng.com/").append(num).append("/news.shtml");
                break;
            case 4://科技
                url = url.append("http://itech.ifeng.com/").append(num).append("/news.shtml");
                break;
            case 5://历史
                url = url.append("http://ihistory.ifeng.com/").append(num).append("/news.shtml");
                break;
            case 6://娱乐
                url = url.append("http://wemedia.ifeng.com/").append(num).append("/news.shtml");
                break;
        }
        return url.toString();
    }

    //截取中间数字的方法
    private String getMainNum(String oldUrl) {
        oldUrl = oldUrl.substring(oldUrl.lastIndexOf("/")+1,oldUrl.lastIndexOf("."));
        if (oldUrl.contains("_")){
            oldUrl = oldUrl.substring(0,oldUrl.lastIndexOf("_"));
        }
        return oldUrl;
    }
}
