package com.hy.bedone.repo

import com.hy.common.model.Bedone
import com.hy.datacompile.DataApi
import com.hy.datacompile.DataMethod

@DataApi(local = BedoneLocalDataApi::class)
interface BedoneDataApi {
    @DataMethod
    fun addBedone(bedone: Bedone): Int

    @DataMethod
    fun getBedonSize(): Int

    @DataMethod
    fun getBedones(): List<Bedone>

    @DataMethod
    fun getBedonesByType(type: Int): List<Bedone>

    @DataMethod
    fun getBedonesSizeByType(type: Int): Int

    @DataMethod
    fun getDeletedBedones(): List<Bedone>

    @DataMethod
    fun getDeletedBedoneSize(): Int


}