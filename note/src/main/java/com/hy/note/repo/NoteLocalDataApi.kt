package com.hy.note.repo

import com.hy.common.model.Note
import com.hy.common.room.AppDatabaseInstance
import java.lang.Exception

/**
 * @Author Lenovo
 * 本地数据源
 */
class NoteLocalDataApi : NoteDataApi {
    @Throws(Exception::class)
    override fun  addNote(newNote: Note):Int {
        try {
            AppDatabaseInstance().noteDao().addNote(newNote)
            return 0
        }catch (e:Exception){
            throw e;
        }
    }

    override fun getNote(): List<Note> {
        try {
            return AppDatabaseInstance().noteDao().getNotes();
        }catch (e:Exception){
            return mutableListOf()
        }
    }

    override fun getNote(id: Int): Note {
        try {
            return AppDatabaseInstance().noteDao().getNote(id)
        }catch (e:Exception){
            return Note()
        }
    }

    override fun getNotesByType(type: Int): List<Note> {
        try {
            return AppDatabaseInstance().noteDao().getNotesByType(type)
        }catch (e:Exception){
            return mutableListOf()
        }
    }

    override fun updateNote(updateNote: Note): Int {
        try {
            AppDatabaseInstance().noteDao().updateNote(updateNote)
            return 1
        }catch (e:Exception){
            return -1
        }
    }

    override fun getNotesSize(): Int {
        try {
            return AppDatabaseInstance().noteDao().getNotesSize()
        }catch (e:Exception){
            return -1
        }
    }
}