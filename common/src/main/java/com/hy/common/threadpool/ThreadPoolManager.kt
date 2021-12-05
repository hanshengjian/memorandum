package com.hy.common.threadpool

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

/**
 * @Author Lenovo
 */
object  ThreadPoolManager {
    val threadPool = Executors.newFixedThreadPool(5)
    val mainHandler = Handler(Looper.getMainLooper())
}