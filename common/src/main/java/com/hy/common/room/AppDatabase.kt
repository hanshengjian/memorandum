package com.hy.common.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hy.common.model.Bedone
import com.hy.common.model.DicType
import com.hy.common.model.Note
import com.hy.common.room.dao.BedoneDao
import com.hy.common.room.dao.DicTypeDao
import com.hy.common.room.dao.NoteDao

/**
 * @Author Lenovo
 * 注意entities的要添加所有数据表类
 */
@Database(entities = [Note::class, DicType::class, Bedone::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun noteDao(): NoteDao
    abstract fun dicTypeDao(): DicTypeDao
    abstract fun bedoneDao(): BedoneDao
}