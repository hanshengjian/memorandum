package com.hy.common.room.dao

import androidx.room.*
import com.hy.common.model.Bedone

@Dao
interface BedoneDao {
    @Insert
    fun addBedone(note: Bedone)

    @Update
    fun updateBedone(bedone: Bedone)

    @Query("select * from bedone order by  create_time desc")
    fun getBedones(): List<Bedone>

    @Query("select * from bedone where id=:id")
    fun getBedone(id: Int): Bedone;

    @Query("select * from bedone where type=:type")
    fun getBedonesByType(type: Int): List<Bedone>

    @Query("select count(1) from bedone ")
    fun getBedonesSize(): Int

    @Query("select count(1) from bedone where type=0")
    fun getBedoneSizeNoType(): Int

    @Query("select * from bedone where type=0")
    fun getBedonesByNoType(): List<Bedone>

    @Delete
    fun deleteBedone(bedone: Bedone)
}