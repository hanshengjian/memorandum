package com.hy.common.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

/**
 * @auther:hanshengjian
 * @date:2022/3/24
 */
public class MyView implements PlatformView, MethodChannel.MethodCallHandler {
    Context context;

    public MyView(Context context) {
        this.context = context;
        // MethodChannel methodChannel = new MethodChannel(messenger, "plugins.nightfarmer.top/myview_" + id);
        //  methodChannel.setMethodCallHandler(this);
    }

    @Override
    public View getView() {
        TextView myNativeView = new TextView(context);
        myNativeView.setText("我是来自Android的原生TextView");
        myNativeView.setBackgroundColor(Color.WHITE);
        return myNativeView;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {

    }

    private class ViewHolder {

    }

}
