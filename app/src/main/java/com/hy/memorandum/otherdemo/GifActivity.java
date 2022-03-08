package com.hy.memorandum.otherdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hy.memorandum.R;

public class GifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        final Runtime runtime = Runtime.getRuntime();
        final long usedMemInKB = (runtime.totalMemory() - runtime.freeMemory()) / 1024L;
        // final long maxHeapSizeInMB=runtime.maxMemory() / 1048576L;
        // final long availHeapSizeInMB = maxHeapSizeInMB - usedMemInMB;

        Log.i("使用内存占用大小", "usedMemInKB:" + usedMemInKB);
        ImageView imageView = findViewById(R.id.gif_iv);
        Glide.with(this).asGif().load(R.drawable.shuangbianlian).into(imageView);


        new Thread(new Runnable() {
            @Override
            public void run() {
                //答应内存占用大小
                //每5秒
                while (true) {
                    try {
                        Thread.sleep(5000);

                        final Runtime runtime = Runtime.getRuntime();
                        final double usedMemInKB = (runtime.totalMemory() - runtime.freeMemory()) / 1024L;
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