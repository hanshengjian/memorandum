package com.hy.common.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hy.common.model.Note

/**
 * @Author Lenovo
 */
@Dao
interface NoteDao {
    @Insert
    fun addNote(note:Note)

    @Update
    fun updateNote(note:Note)

    @Query("select * from note")
    fun getNotes():List<Note>

    @Query("select * from note where id=:id")
    fun getNote(id:Int):Note;

    @Query("select * from note where type=:type")
    fun getNotesByType(type:Int):List<Note>

    @Query("select count(1) from note ")
    fun getNotesSize():Int
}