package com.hy.memorandum.otherdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.hy.memorandum.R;

public class LottieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);

        LottieAnimationView lottieAnimationView = findViewById(R.id.lottie_view);
        lottieAnimationView.setImageAssetsFolder("images/");
        lottieAnimationView.setAnimation("data.json");
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();

        final Runtime runtime = Runtime.getRuntime();
        final long usedMemInKB = (runtime.totalMemory() - runtime.freeMemory()) / 1024L;
        // final long maxHeapSizeInMB=runtime.maxMemory() / 1048576L;
        // final long availHeapSizeInMB = maxHeapSizeInMB - usedMemInMB;

        Log.i("使用内存占用大小", "usedMemInKB:" + usedMemInKB);
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