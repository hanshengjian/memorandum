package com.hy.common.navigator

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

interface BedoneService : IProvider {
    //总数
    fun getBedoneSize(expression: (Int?, String?) -> Unit)

    //未分类
    fun getBedoneSizeNoType(expression: (Int?, String?) -> Unit)

    //分类
    fun getBedoneSizeByType(type: Int, expression: (Int?, String?) -> Unit)

    //
    fun addBedone(context: Context, type: Int)

    //获取删除代办数(Int?,String?) : (数量，错误信息)
    fun getDeletedBedoneSize(expression: (Int?, String?) -> Unit)
}