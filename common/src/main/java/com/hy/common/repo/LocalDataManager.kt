package com.hy.common.repo

import android.content.Context
import androidx.room.Room
import com.hy.common.room.AppDatabase

/**
 * @Author Lenovo
 */
object LocalDataManager {
    var mDb: AppDatabase?=null
    @Synchronized
    fun getInstance(context: Context):AppDatabase {
       return if (mDb != null) mDb!! else Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "memorandum.db"
        ).build()
    }
}