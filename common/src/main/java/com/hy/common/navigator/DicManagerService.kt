package com.hy.common.navigator

import com.alibaba.android.arouter.facade.template.IProvider
import com.hy.common.model.DicType

/**
 * @auther:hanshengjian
 * @date:2021/12/16
 *
 */
interface DicManagerService:IProvider {
    fun getDicList(page:Int,expression:(List<DicType>?,String?)->Unit)
    fun addDicType(page:Int,title:String,expression:(Int,String?)->Unit)
    fun getDicType(id:Int,expression:(DicType?,String?)->Unit)
    fun updateDicType(dicType: DicType,expression: (Int?, String?) -> Unit)
}