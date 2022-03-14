package com.hy.bedone.repo

import com.hy.common.model.Bedone
import com.hy.common.room.AppDatabaseInstance
import com.hy.utils.LogUtil

class BedoneLocalDataApi : BedoneDataApi {
    override fun addBedone(bedone: Bedone): Int {
        try {
            AppDatabaseInstance().bedoneDao().addBedone(bedone)
            return 1
        } catch (e: Exception) {
            LogUtil.i("Bedone", "addBedone fail msg:" + e.message)
            throw e
        }
    }

    override fun getBedonSize(): Int {
        try {
            return AppDatabaseInstance().bedoneDao().getBedonesSize()
        } catch (e: Exception) {
            LogUtil.i("Bedone", "getBedonSize fail msg:" + e.message)
            throw e
        }
    }

    override fun getBedones(): List<Bedone> {
        try {
            return AppDatabaseInstance().bedoneDao().getBedones()
        } catch (e: Exception) {
            LogUtil.i("Bedone", "getBedones fail msg:" + e.message)
            throw e
        }
    }

    override fun getBedonesByType(type: Int): List<Bedone> {
        try {
            return AppDatabaseInstance().bedoneDao().getBedones()
        } catch (e: Exception) {
            LogUtil.i("Bedone", "getBedonesByType fail msg:" + e.message)
            throw e
        }
    }

    override fun getBedonesSizeByType(type: Int): Int {
        try {
            return AppDatabaseInstance().bedoneDao().getBedoneSizeByType(type)
        } catch (e: Exception) {
            LogUtil.i("Bedone", "getBedonesByType fail msg:" + e.message)
            throw e
        }
    }

    override fun getDeletedBedones(): List<Bedone> {
        try {
            return AppDatabaseInstance().bedoneDao().getDeletedBedones()
        } catch (e: Exception) {
            LogUtil.i("Bedone", "getBedonesByType fail msg:" + e.message)
            throw e
        }
    }

    override fun getDeletedBedoneSize(): Int {
        try {
            return AppDatabaseInstance().bedoneDao().getDeletedBedonesSize()
        } catch (e: Exception) {
            LogUtil.i("Bedone", "getBedonesByType fail msg:" + e.message)
            throw e
        }
    }
}