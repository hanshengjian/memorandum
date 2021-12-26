package com.hy.dicmanager.repo

import com.hy.common.model.DicType
import com.hy.common.room.AppDatabaseInstance
import java.lang.Exception

/**
 * @auther:hanshengjian
 * @date:2021/12/8
 *
 */
class DicTypeLocalDataApi:DicTypeDataApi {
    override fun addDicType(newDic: DicType): Int {
        try {
            AppDatabaseInstance().dicTypeDao().addDicType(newDic)
            return 1
        }catch (e:Exception){
            throw e
        }
    }

    override fun deleteDicType(delectDic: DicType): Int {
        try {
            AppDatabaseInstance().dicTypeDao().deleteDicType(delectDic)
            return 1
        }catch (e:Exception){
            throw e
        }
    }


    override fun updateDicType(updateDicType: DicType): Int {
        try {
            AppDatabaseInstance().dicTypeDao().updateDicType(updateDicType)
            return 1
        }catch (e:Exception){
            throw e
        }
    }

    override fun getDicTypes(page: Int): List<DicType> {
        try {
            return AppDatabaseInstance().dicTypeDao().getDicTypes(page)
        }catch (e:Exception){
            throw e
        }
    }

    override fun getDicType(id: Int): DicType {
        try {
            return AppDatabaseInstance().dicTypeDao().getDicType(id)
        }catch (e:Exception){
            throw e
        }
    }
}