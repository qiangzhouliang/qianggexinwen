package com.qzl.qianggexinwen.utils;

import android.app.Activity;

import com.qzl.qianggexinwen.view.CommonProgressDialog;

/**
 * 弹窗工具类
 * Created by Qzl on 2016-10-10.
 */

public class ProgressDialogUtils {

    private static CommonProgressDialog mDialog;
    private Activity activity;

    //显示方法
    public void showProgressDialog(Activity activity, String msg) {
        this.activity = activity;
        if (mDialog == null) {
            mDialog = new CommonProgressDialog(activity);
        }
        if (msg == null) {
            msg = "正在加载...";
        }
        mDialog.setMessage(msg);
        //判断activity是否被关闭 && 没有显示的时候
        if (!activity.isFinishing() && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    //关闭方法
    public void closeProgressDialog() {
        if (mDialog != null && !activity.isFinishing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}
