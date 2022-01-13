package com.hy.dicmanager.repo

import com.hy.common.model.DicType
import com.hy.datacompile.DataApi
import com.hy.datacompile.DataMethod

/**
 * @auther:hanshengjian
 * @date:2021/12/8
 *
 */
@DataApi(local = DicTypeLocalDataApi::class)
interface DicTypeDataApi {

    @DataMethod
    fun addDicType(newDic: DicType): Int

    @DataMethod
    fun deleteDicType(delectDic: DicType): Int

    @DataMethod
    fun updateDicType(updateDicType: DicType): Int

    @DataMethod
    fun getDicTypes(page: Int): List<DicType>

    @DataMethod
    fun getDicType(id:Int):DicType
}