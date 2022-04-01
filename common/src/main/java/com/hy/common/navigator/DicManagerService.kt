package com.hy.common.navigator

import com.alibaba.android.arouter.facade.template.IProvider
import com.hy.common.model.DicType

/**
 * @auther:hanshengjian
 * @date:2021/12/16
 * expression是高阶函数，参数含义见各个接口说明
 */
interface DicManagerService:IProvider {
    //根据page，获取所有的分类.(List<DicType>?,String?) : (分类的数组，错误信息)
    fun getDicList(page:Int,expression:(List<DicType>?,String?)->Unit)

    //添加分类.(Int,String?) : (添加的返回值，大于0代表成功，错误信息)
    fun addDicType(page:Int,title:String,expression:(Int,String?)->Unit)

    //根据id,获取分类.(DicType?,String?) :(分类，错误信息)
    fun getDicType(id:Int,expression:(DicType?,String?)->Unit)

    //更新分类 (Int?, String?)：(更新的返回值，大于0代表更新成功，错误信息)
    fun updateDicType(dicType: DicType,expression: (Int?, String?) -> Unit)

    fun addDefaultType();

}