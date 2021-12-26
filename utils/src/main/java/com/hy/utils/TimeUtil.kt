package com.hy.utils

import java.text.SimpleDateFormat

object TimeUtil {
    fun toDateyyyy_MM_DD(timeStamp:Long):String{
        val sdf = SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(timeStamp)
    }

    fun toDateHH_mm(timeStamp:Long):String{
        val sdf = SimpleDateFormat("HH:mm");
        return sdf.format(timeStamp)
    }
}