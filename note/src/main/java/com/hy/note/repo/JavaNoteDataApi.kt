package com.hy.note.repo

import com.hy.common.model.Note
import com.hy.datacompile.DataApi
import com.hy.datacompile.DataMethod
import com.hy.datacompile.JavaDataApi
import com.hy.datacompile.JavaDataMethod

/**
 * @Author Lenovo
 */
@JavaDataApi(local = NoteLocalDataApi::class)
interface JavaNoteDataApi {

    @JavaDataMethod
    fun addNote(newNote: Note): Int


    fun getNotes(): List<Note>


    fun getNote(id: Int): Note


    fun getNotesByType(type: Int): List<Note>


    fun updateNote(updateNote: Note): Int


    fun getNotesSize(): Int

    fun getNotesSizeNoType(): Int

    fun getNotesByNoType(): List<Note>

    fun deleteNote(note: Note): Int
}