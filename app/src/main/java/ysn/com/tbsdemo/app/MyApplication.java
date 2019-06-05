package ysn.com.tbsdemo.app;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

/**
 * @Author yangsanning
 * @ClassName MyApplication
 * @Description 一句话概括作用
 * @Date 2019/6/5
 * @History 2019/6/5 author: description:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化tbs
        QbSdk.initX5Environment(this, null);
    }
}
