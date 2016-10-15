package com.qzl.qianggexinwen.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.qzl.qianggexinwen.R;

/**
 *自定义的加载弹窗样式
 * Created by Qzl on 2016-10-09.
 */

public class CommonProgressDialog extends Dialog {

    public CommonProgressDialog(Context context) {
        super(context,R.style.commonProgressDialog);

        setContentView(R.layout.commonprogressdialog);

        //显示在屏幕中间
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    /**
     * 设置加载消息的方法
     * @param s
     */
    public void setMessage(String s){
        TextView textView = (TextView) this.findViewById(R.id.tv_loading);
        textView.setText(s);
    }
}
