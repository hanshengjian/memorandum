package com.hy.dicmanager.router

import android.content.Context
import com.hy.common.data.DicType
import com.hy.common.navigator.DicManagerService
import com.hy.common.room.AppDatabase
import com.hy.common.room.AppDatabaseInstance

/**
 * @auther:hanshengjian
 * @date:2021/12/16
 *
 */
class DicManagerServiceImpl:DicManagerService {
    override fun getDicList(page: Int): List<DicType> {
       return AppDatabaseInstance().dicTypeDao().getDicTypes(page)
    }

    override fun addDicType(page: Int, title: String): Int {

    }

    override fun init(context: Context?) {

    }
}