package com.hy.dicmanager.repo

import com.hy.common.data.DicType

/**
 * @auther:hanshengjian
 * @date:2021/12/8
 *
 */
class DicTypeLocalDataApi:DicTypeDataApi {
    override fun addDicType(newDic: DicType): Int {
        return 1
    }

    override fun deleteDicType(typeId: Int): Int {
        return 1
    }

    override fun updateDicType(updateDicType: DicType): Int {
        return 1
    }

    override fun getDicTypes(page: Int): List<DicType> {
        return mutableListOf()
    }
}