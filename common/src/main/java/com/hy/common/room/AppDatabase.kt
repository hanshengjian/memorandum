package com.hy.common.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hy.common.data.Note

/**
 * @Author Lenovo
 */
@Database(entities = [Note::class],version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun noteDao():NoteDao
}