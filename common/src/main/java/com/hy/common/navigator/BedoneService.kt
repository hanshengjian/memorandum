package com.hy.common.navigator

import com.alibaba.android.arouter.facade.template.IProvider

interface BedoneService : IProvider {
    //总数
    fun getBedoneSize(expression: (Int?, String?) -> Unit)

    //未分类
    fun getBedoneSizeNoType(expression: (Int?, String?) -> Unit)
}