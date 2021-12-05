package com.hy.utils

import java.text.SimpleDateFormat

object TimeUtil {
    fun toDateyyyy_MM_DD(timeStamp:Long):String{
        val sdf = SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(timeStamp)
    }
}