package com.hy.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * @auther:hanshengjian
 * @date:2021/12/16
 *
 */
object UiUtil {
    fun dip2px(context: Context?, dpValue: Float): Int {
        if (context == null) {
            return dpValue.toInt()
        }
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun getScreenSize(context: Context): DisplayMetrics? {
        val dm = DisplayMetrics()
        try {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(dm)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dm
    }
}