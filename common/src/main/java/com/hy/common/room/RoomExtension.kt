package com.hy.common.room

import com.hy.common.base.BaseApp
import com.hy.common.extend.getApplication
import com.hy.common.repo.LocalDataManager

/**
 * @Author Lenovo
 */
fun AppDatabaseInstance():AppDatabase{
    return LocalDataManager.getInstance(getApplication())
}