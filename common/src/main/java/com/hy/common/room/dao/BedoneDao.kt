package com.hy.common.room.dao

import androidx.room.*
import com.hy.common.model.Bedone

@Dao
interface BedoneDao {
    @Insert
    fun addBedone(note: Bedone)

    @Update
    fun updateBedone(bedone: Bedone)

    @Query("select * from bedone where deleteFlag=0 order by  create_time desc ")
    fun getBedones(): List<Bedone>

    @Query("select * from bedone where id=:id")
    fun getBedone(id: Int): Bedone;

    @Query("select * from bedone where type=:type and deleteFlag=0 order by  create_time desc")
    fun getBedonesByType(type: Int): List<Bedone>

    @Query("select count(1) from bedone where deleteFlag=0")
    fun getBedonesSize(): Int

    @Query("select count(1) from bedone where type=0 and deleteFlag=0")
    fun getBedoneSizeNoType(): Int

    @Query("select count(1) from bedone where type=:type and deleteFlag=0")
    fun getBedoneSizeByType(type: Int): Int

    @Query("select * from bedone where type=0 and deleteFlag=0 order by  create_time desc")
    fun getBedonesByNoType(): List<Bedone>

    @Query("select * from bedone where deleteFlag=1 order by  create_time desc")
    fun getDeletedBedones(): List<Bedone>

    @Query("select count(1) from bedone where deleteFlag=1")
    fun getDeletedBedonesSize(): Int

    @Delete
    fun deleteBedone(bedone: Bedone)
}