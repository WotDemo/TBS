package ysn.com.tbsdemo.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

import ysn.com.tbsdemo.R;
import ysn.com.tbsdemo.view.TBSFileView;

/**
 * @Author yangsanning
 * @ClassName FileBrowsActivity
 * @Description 不同格式文件浏览
 * @Date 2019/6/5
 * @History 2019/6/5 author: description:
 */
public class FileBrowsActivity extends AppCompatActivity {

    public static String EXTRA_FILE_PATH = "extraUrl";

    TBSFileView tbsFileView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_brows);

        tbsFileView = findViewById(R.id.file_brows_activity_view);
        tbsFileView.brows(new File(getIntent().getStringExtra(EXTRA_FILE_PATH)));
    }

    @Override
    public void onDestroy() {
        if (null != tbsFileView) {
            tbsFileView.destroy();
        }
        super.onDestroy();
    }
}
