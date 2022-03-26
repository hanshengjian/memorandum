package com.hy.common.widget;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.hy.common.manager.ActivityManager;
import com.hy.common.threadpool.ThreadPoolManager;

import java.io.File;
import java.util.concurrent.ExecutionException;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * @auther:hanshengjian
 * @date:2022/3/25
 */
public class FlutterNativeImageChannel implements MethodChannel.MethodCallHandler, FlutterPlugin {

    private static final String TAG = "FlutterNativeImageChannel";
    private static final String CHANNEL_ROUTER = "com.memorandum/native_image";

    private Context mContext;
    private MethodChannel channel;

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("getNativeImage")) {
            ThreadPoolManager.INSTANCE.getThreadPool().submit(new Runnable() {
                @Override
                public void run() {
                    //获取图片
                    String url = (String) call.arguments;
                    //Glide 加载url，获取sd卡地址
                    //获取当前的FlutterActivity
                    try {
                        File mfile = Glide.with(ActivityManager.peek()).asFile().load(url).submit().get();
                        ThreadPoolManager.INSTANCE.getMainHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                result.success(mfile.getAbsolutePath());
                            }
                        });
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        channel = new MethodChannel(binding.getBinaryMessenger(), CHANNEL_ROUTER);
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {

    }
}
