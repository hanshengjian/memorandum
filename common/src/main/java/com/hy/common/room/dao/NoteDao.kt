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

    @Query("select * from note where deleteFlag=0 order by  create_time desc")
    fun getNotes(): List<Note>

    @Query("select * from note where id=:id")
    fun getNote(id: Int): Note;

    @Query("select * from note where type=:type and deleteFlag=0")
    fun getNotesByType(type: Int): List<Note>

    @Query("select count(1) from note where deleteFlag=0")
    fun getNotesSize(): Int

    @Query("select count(1) from note where type=0 and deleteFlag=0")
    fun getNoteSizeNoType(): Int

    @Query("select count(1) from note where type=:type and deleteFlag=0")
    fun getNoteSizeByType(type: Int): Int

    @Query("select * from note where type=0 and deleteFlag=0 order by  create_time desc")
    fun getNotesByNoType(): List<Note>

    @Query("select * from note where deleteFlag=1 order by  create_time desc")
    fun getDeletedNotes(): List<Note>

    @Query("select count(1) from note where deleteFlag=1")
    fun getDeletedNotesSize(): Int

    @Update
    fun deleteNote(note: Note)
}