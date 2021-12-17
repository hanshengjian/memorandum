package com.hy.common.navigator

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider
import com.hy.common.data.DicType

/**
 * @auther:hanshengjian
 * @date:2021/12/16
 *
 */
interface DicManagerService:IProvider {
    fun getDicList(page:Int):List<DicType>

    fun addDicType(page:Int,title:String):Int
}