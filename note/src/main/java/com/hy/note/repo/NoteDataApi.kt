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
    fun getNote(): List<Note>

    @DataMethod
    fun getNote(id: Int): Note

    fun getNotesByType(type: Int): List<Note>

    fun updateNote(updateNote: Note): Int

    fun getNotesSize(): Int

    fun getNotesSizeNoType(): Int

    fun getNotesByNoType(): List<Note>

    fun delete(note: Note): Int
}