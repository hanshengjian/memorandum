package com.hy.note.repo

import com.hy.common.model.Note
import com.hy.datacompile.DataApi
import com.hy.datacompile.DataMethod

/**
 * @Author Lenovo
 */
@DataApi(local = NoteLocalDataApi::class)
interface NoteDataApi {

    @DataMethod
    fun addNote(newNote: Note): Int

    @DataMethod
    fun getNotes(): List<Note>

    @DataMethod
    fun getNote(id: Int): Note

    @DataMethod
    fun getNotesByType(type: Int): List<Note>

    @DataMethod
    fun updateNote(updateNote: Note): Int

    @DataMethod
    fun getNotesSize(): Int

    @DataMethod
    fun getNotesSizeNoType(): Int

    @DataMethod
    fun getNotesByNoType(): List<Note>

    @DataMethod
    fun getDeletedNotes(): List<Note>

    @DataMethod
    fun getDeletedNoteSize(): Int

    @DataMethod
    fun deleteNote(note: Note): Int

    @DataMethod
    fun getNoteSizeByType(type: Int): Int
}