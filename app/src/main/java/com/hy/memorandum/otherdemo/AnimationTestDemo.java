package com.hy.memorandum.otherdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hy.memorandum.R;

public class AnimationTestDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test_demo);


        findViewById(R.id.gif_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimationTestDemo.this, GifActivity.class));
            }
        });

        findViewById(R.id.lottie_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimationTestDemo.this, LottieActivity.class));
            }
        });

        findViewById(R.id.pag_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimationTestDemo.this, PagActivity.class));
            }
        });
    }
}