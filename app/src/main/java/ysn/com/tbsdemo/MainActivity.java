package ysn.com.tbsdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ysn.com.tbsdemo.page.FileBrowsActivity;
import ysn.com.tbsdemo.utils.FileSelectUtils;
import ysn.com.tbsdemo.utils.UShareEntry;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Disposable subscribe = new RxPermissions(this)
                .request(UShareEntry.PERMISSION_LIST)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(granted -> {
                    if (!granted) {
                        Toast.makeText((MainActivity.this), ("不给权限你是来搞笑的吗？"), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        findViewById(R.id.main_activity_open_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileSelectUtils.selectFile(MainActivity.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            String filePath = FileSelectUtils.onFileResult(this, requestCode, resultCode, data);
            Intent intent = new Intent(MainActivity.this, FileBrowsActivity.class);
            intent.putExtra(FileBrowsActivity.EXTRA_FILE_PATH,filePath);
            startActivity(intent);
        }
    }
}
