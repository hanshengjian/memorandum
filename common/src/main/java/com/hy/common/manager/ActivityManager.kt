package com.hy.common.manager

import android.app.Activity
import java.util.*

class ActivityManager {
    companion object{
        val stack:Stack<Activity> = Stack()

        @JvmStatic
        fun peek():Activity{
            return stack.peek()
        }

        @JvmStatic
        fun push(activity: Activity){
            stack.push(activity)
        }

        @JvmStatic
        fun pop(){
            stack.pop()
        }
    }
}