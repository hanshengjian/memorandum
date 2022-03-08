package com.hy.memorandum.otherdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.hy.memorandum.R;

import org.libpag.PAGFile;
import org.libpag.PAGView;

public class PagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag);
        final Runtime runtime = Runtime.getRuntime();
        final long usedMemInKB = (runtime.totalMemory() - runtime.freeMemory()) / 1024L;
        // final long maxHeapSizeInMB=runtime.maxMemory() / 1048576L;
        // final long availHeapSizeInMB = maxHeapSizeInMB - usedMemInMB;

        Log.i("使用内存占用大小", "usedMemInKB:" + usedMemInKB);


        RelativeLayout backgroundView = findViewById(R.id.background_view);
        final PAGView pagView = new PAGView(this);
        pagView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        backgroundView.addView(pagView);
        PAGFile pagFile1 = null;
        pagFile1 = PAGFile.Load(getAssets(), "shuangbianlian.pag");
        pagView.setComposition(pagFile1);
        pagView.setRepeatCount(-1);
        pagView.play();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //答应内存占用大小
                //每5秒
                while (true) {
                    try {
                        Thread.sleep(5000);

                        final Runtime runtime = Runtime.getRuntime();
                        final long usedMemInKB = (runtime.totalMemory() - runtime.freeMemory()) / 1024L;
                        // final long maxHeapSizeInMB=runtime.maxMemory() / 1048576L;
                        // final long availHeapSizeInMB = maxHeapSizeInMB - usedMemInMB;

                        Log.i("使用内存占用大小", "usedMemInKB:" + usedMemInKB);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}