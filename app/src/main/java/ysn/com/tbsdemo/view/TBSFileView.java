package ysn.com.tbsdemo.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

import ysn.com.tbsdemo.utils.FileUtils;

/**
 * @Author yangsanning
 * @ClassName TBSFileView
 * @Description Tbs 文件预览 view
 * @Date 2019/6/5
 * @History 2019/6/5 author: description:
 */
public class TBSFileView extends FrameLayout implements TbsReaderView.ReaderCallback {

    private static String TAG = "test";

    private String tbsReaderTempPath;

    private Context context;
    private TbsReaderView tbsReaderView;

    public TBSFileView(Context context) {
        this(context, null, 0);
    }

    public TBSFileView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TBSFileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.tbsReaderView = getTbsReaderView();
        this.addView(tbsReaderView, new LinearLayout.LayoutParams(-1, -1));
    }

    private TbsReaderView getTbsReaderView() {
        if (tbsReaderView == null) {
            tbsReaderView = new TbsReaderView(context, this);
        }
        return tbsReaderView;
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        Log.e(TAG, "onCallBackAction: " + integer);
    }

    public void brows(File file) {
        if (file != null && !TextUtils.isEmpty(file.toString())) {
            // 解决没有TbsReaderTemp文件夹存在导致加载文件失败
            FileUtils.ensureFolderExists(getTbsReaderTempPath());

            //加载文件
            Bundle bundle = new Bundle();
            bundle.putString("filePath", file.toString());
            bundle.putString("tempPath", getTbsReaderTempPath());

            this.tbsReaderView = getTbsReaderView();
            boolean canOpen = this.tbsReaderView.preOpen(getFileType(file.toString()), false);
            if (canOpen) {
                this.tbsReaderView.openFile(bundle);
            }else {
                Log.e(TAG, "文件不可打开");
            }
        } else {
            Log.e(TAG, "文件路径为空");
        }
    }

    /***
     * 获取文件类型
     */
    private String getFileType(String filePath) {
        String str = "";

        if (TextUtils.isEmpty(filePath)) {
            Log.d(TAG, "文件路径为空");
            return str;
        }
        Log.d(TAG, "文件路径:" + filePath);
        int i = filePath.lastIndexOf('.');
        if (i <= -1) {
            Log.d(TAG, "找不到文件后缀");
            return str;
        }

        str = filePath.substring(i + 1);
        Log.d(TAG, "文件类型: " + str);
        return str;
    }

    public String getTbsReaderTempPath() {
        if (tbsReaderTempPath == null) {
            tbsReaderTempPath = FileUtils.getSDCardPath() + "TbsReaderTemp";
        }
        return tbsReaderTempPath;
    }

    public void setTbsReaderTempPath(String tbsReaderTempPath) {
        this.tbsReaderTempPath = tbsReaderTempPath;
    }

    public void destroy() {
        if (tbsReaderView != null) {
            tbsReaderView.onStop();
        }
    }
}