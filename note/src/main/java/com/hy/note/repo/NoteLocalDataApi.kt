package com.hy.note.repo

import com.hy.common.model.Note
import com.hy.common.room.AppDatabaseInstance
import com.hy.utils.LogUtil

/**
 * @Author Lenovo
 * 本地数据源
 */
class NoteLocalDataApi : NoteDataApi {

    companion object {
        private const val TAG = "NoteLocalDataApi"
    }

    @Throws(Exception::class)
    override fun addNote(newNote: Note): Int {
        try {
            AppDatabaseInstance().noteDao().addNote(newNote)
            return 0
        } catch (e: Exception) {
            throw e;
        }
    }

    override fun getNotes(): List<Note> {
        try {
            return AppDatabaseInstance().noteDao().getNotes();
        } catch (e: Exception) {
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
        } catch (e: Exception) {
            return -1
        }
    }

    override fun getNotesSizeNoType(): Int {
        try {
            return AppDatabaseInstance().noteDao().getNoteSizeNoType()
        } catch (e: Exception) {
            return -1
        }
    }

    override fun getNotesByNoType(): List<Note> {
        try {
            return AppDatabaseInstance().noteDao().getNotesByNoType()
        } catch (e: Exception) {
            return mutableListOf()
        }
    }

    override fun getDeletedNotes(): List<Note> {
        try {
            return AppDatabaseInstance().noteDao().getDeletedNotes()
        } catch (e: Exception) {
            return mutableListOf()
        }
    }

    override fun getDeletedNoteSize(): Int {
        try {
            return AppDatabaseInstance().noteDao().getDeletedNotesSize()
        } catch (e: Exception) {
            return 0
        }
    }

    override fun deleteNote(note: Note): Int {
        try {
            note.deleteFlag = 1
            AppDatabaseInstance().noteDao().deleteNote(note)
            return 0
        } catch (e: Exception) {
            LogUtil.i(TAG, e.message + "")
            return -1
        }
    }

    override fun getNoteSizeByType(type: Int): Int {
        try {
            return AppDatabaseInstance().noteDao().getNoteSizeByType(type)
        } catch (e: Exception) {
            return 0
        }
    }
}