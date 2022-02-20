package com.hy.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.hy.utils.LogUtil;
import com.hy.utils.UiUtil;

/**
 * @auther:hanshengjian
 * @date:2021/12/29
 */
public class MemRecyclerViewItem extends HorizontalScrollView {

    private static final String TAG = "MemRecyclerViewItem";
    private ScrollCallbackListener scrollCallbackListener = null;

    public MemRecyclerViewItem(Context context) {
        this(context, null);

    }

    public MemRecyclerViewItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MemRecyclerViewItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private boolean isLeft = true;//默认左边
    private int rightLayoutWidth;
    private int leftLayoutWidth;
    private int range;

    private void init(Context context, AttributeSet attrs) {
        leftLayoutWidth = UiUtil.INSTANCE.getScreenSize(context).widthPixels - UiUtil.INSTANCE.dip2px(context, 30);
        rightLayoutWidth = UiUtil.INSTANCE.dip2px(context, 200);
        range = UiUtil.INSTANCE.dip2px(context, 20);
    }

    public int getRightLayoutWidth() {
        return rightLayoutWidth;
    }

    public void setRightLayoutWidth(int rightLayoutWidth) {
        this.rightLayoutWidth = rightLayoutWidth;
    }

    public int getLeftLayoutWidth() {
        return leftLayoutWidth;
    }

    public void setLeftLayoutWidth(int leftLayoutWidth) {
        this.leftLayoutWidth = leftLayoutWidth;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setScrollCallbackListener(ScrollCallbackListener scrollCallbackListener) {
        this.scrollCallbackListener = scrollCallbackListener;
    }

    public void apply() {
        isLeft = true;
        changeLayout();
        scrollTo(0, 0);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    private void changeLayout() {
        try {
            ViewGroup mainlayout = (ViewGroup) getChildAt(0);
            ViewGroup left = (ViewGroup) mainlayout.getChildAt(0);
            ViewGroup right = (ViewGroup) mainlayout.getChildAt(1);
            if (left.getMeasuredWidth() == leftLayoutWidth && right.getMeasuredWidth() == rightLayoutWidth) {
                LogUtil.INSTANCE.i(TAG, "changeLayout:no change");
                return;
            }
            ViewGroup.LayoutParams layoutParams = left.getLayoutParams();
            layoutParams.width = leftLayoutWidth;
            left.setLayoutParams(layoutParams);
            ViewGroup.LayoutParams layoutParams2 = right.getLayoutParams();
            layoutParams2.width = rightLayoutWidth;
            right.setLayoutParams(layoutParams2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //回复上一个状态
        if (scrollCallbackListener != null) {
            scrollCallbackListener.onResetLast();
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }
        if (ev.getAction() == MotionEvent.ACTION_CANCEL || ev.getAction() == MotionEvent.ACTION_UP) {
            Log.i(TAG, "up");

            if (isLeft) {
                if (getScrollX() > range) {
                    isLeft = false;
                    scrollTo(rightLayoutWidth, 0);
                    if (scrollCallbackListener != null) {
                        scrollCallbackListener.onScrolled(this);
                    }
                } else {
                    scrollTo(0, 0);
                }
            } else {
                if (getScrollX() < (rightLayoutWidth - range)) {
                    isLeft = true;
                    scrollTo(0, 0);
                } else {
                    if (scrollCallbackListener != null) {
                        scrollCallbackListener.onScrolled(this);
                    }
                    scrollTo(rightLayoutWidth, 0);
                }
            }

        }
        Log.i(TAG, "end");
        return super.onTouchEvent(ev);
    }

    public interface ScrollCallbackListener {
        public void onScrolled(MemRecyclerViewItem item);

        public void onResetLast();
    }

    /**
     * 回复状态
     */
    public void reset() {
        scrollTo(0, 0);
    }
}
