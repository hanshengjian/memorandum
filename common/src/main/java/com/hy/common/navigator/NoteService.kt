package com.hy.common.navigator

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @auther:hanshengjian
 * @date:2021/12/28
 * expression是高阶函数，参数含义见各个接口说明
 */
interface NoteService : IProvider {
    //获取总笔记数 (Int?,String?) : (数量，错误信息)
    fun getNoteSize(expression: (Int?, String?) -> Unit)

    //获取未分类的笔记数 (Int?,String?) : (数量，错误信息)
    fun getNotesSizeNoType(expression: (Int?, String?) -> Unit)
}