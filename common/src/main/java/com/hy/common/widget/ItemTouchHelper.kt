package com.hy.common.widget

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelper(val context: Context, listener: OnItemTouchListenter) :
    RecyclerView.SimpleOnItemTouchListener() {
    private var mRv: RecyclerView? = null

    private var mGestureDetector: GestureDetector = GestureDetector(context,object :
        GestureDetector.SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent?) {
            val childView = mRv?.findChildViewUnder(e?.x ?: 0f, e?.y ?: 0f)
            if (childView != null) {
                val position = mRv?.getChildLayoutPosition(childView)
                listener.onItemLongClick(position ?: 0, childView)
            }
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            val childView = mRv?.findChildViewUnder(e?.x ?: 0f, e?.y ?: 0f)
            if (childView != null) {
                val position = mRv?.getChildLayoutPosition(childView)
                listener.onItemClick(position ?: 0, childView)
                return true
            }
            return super.onSingleTapUp(e)
        }

    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (mRv == null) {
            mRv = rv
        }
        if (mGestureDetector.onTouchEvent(e)) {
            return true;
        }
        return false;
    }


    interface OnItemTouchListenter {
        fun onItemLongClick(position: Int, childView: View?) {}

        fun onItemClick(position: Int, childView: View?)
    }
}