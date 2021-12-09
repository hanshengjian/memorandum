package com.hy.common.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hy.common.data.DicType
import com.hy.common.data.Note
import com.hy.common.room.dao.DicTypeDao
import com.hy.common.room.dao.NoteDao

/**
 * @Author Lenovo
 */
@Database(entities = [Note::class],version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun noteDao(): NoteDao
    abstract fun dicTypeDao(): DicTypeDao
}