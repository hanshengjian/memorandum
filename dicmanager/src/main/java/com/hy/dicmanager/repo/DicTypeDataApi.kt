package com.hy.dicmanager.repo

import com.hy.common.model.DicType

/**
 * @auther:hanshengjian
 * @date:2021/12/8
 *
 */
interface DicTypeDataApi {
    fun addDicType(newDic:DicType):Int

    fun deleteDicType(delectDic:DicType):Int

    fun updateDicType(updateDicType:DicType):Int

    fun getDicTypes(page:Int):List<DicType>
}