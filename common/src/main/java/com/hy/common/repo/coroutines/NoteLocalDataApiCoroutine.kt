package com.hy.common.repo.coroutines

import com.hy.common.data.Note
import com.hy.common.repo.BaseReq
import com.hy.common.repo.note.NoteDataApi
import com.hy.common.room.AppDatabaseInstance
import java.lang.Exception

/**
 * @Author Lenovo
 * 本地数据源
 */
class NoteLocalDataApiCoroutine : NoteDataApiCoroutine {
    @Throws(Exception::class)
    override suspend fun  addNote(newNote: Note):Int {
        try {
            AppDatabaseInstance().noteDao().addNote(newNote)
            return 0
        }catch (e:Exception){
            throw e;
        }
    }

    override suspend fun getNote(): List<Note> {
        try {
            return AppDatabaseInstance().noteDao().getNotes();
        }catch (e:Exception){
            return mutableListOf()
        }
    }

    override suspend fun getNote(id: Int): Note {
        try {
            return AppDatabaseInstance().noteDao().getNote(id)
        }catch (e:Exception){
            return Note()
        }
    }

    override suspend fun updateNote(updateNote: Note): Int {
        try {
            AppDatabaseInstance().noteDao().updateNote(updateNote)
            return 1
        }catch (e:Exception){
            return -1
        }
    }
}