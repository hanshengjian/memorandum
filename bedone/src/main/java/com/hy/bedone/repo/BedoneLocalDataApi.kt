package com.hy.bedone.repo

import com.hy.common.model.Bedone
import com.hy.common.room.AppDatabaseInstance

class BedoneLocalDataApi : BedoneDataApi {
    override fun addBedone(bedone: Bedone): Int {
        try {
            AppDatabaseInstance().bedoneDao().addBedone(bedone)
            return 1
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getBedonSize(): Int {
        try {
            return AppDatabaseInstance().bedoneDao().getBedonesSize()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getBedones(): List<Bedone> {
        try {
            return AppDatabaseInstance().bedoneDao().getBedones()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getBedonesByType(type: Int): List<Bedone> {
        try {
            return AppDatabaseInstance().bedoneDao().getBedones()
        } catch (e: Exception) {
            throw e
        }
    }
}