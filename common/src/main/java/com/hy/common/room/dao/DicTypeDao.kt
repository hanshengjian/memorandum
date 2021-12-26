package com.hy.common.room.dao

import androidx.room.*
import com.hy.common.model.DicType

/**
 * @auther:hanshengjian
 * @date:2021/12/8
 */
@Dao
interface DicTypeDao {
    @Insert
    fun addDicType(dicType: DicType)

    @Update
    fun updateDicType(dicType: DicType)

    @Query("select * from dic_type where page=:page")
    fun getDicTypes(page:Int):List<DicType>

    @Query("select * from dic_type where id=:id and page=:page")
    fun getDicType(page:Int,id:Int): DicType;

    @Delete
    fun deleteDicType(dicType: DicType)
}