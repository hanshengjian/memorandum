package com.hy.note.repo

import com.hy.common.model.Note
/**
 * @Author Lenovo
 */

interface NoteDataApi {

    fun addNote(newNote:Note):Int


    fun getNote():List<Note>


    fun getNote(id:Int):Note

    fun getNotesByType(type:Int):List<Note>


    fun updateNote(updateNote:Note):Int

    fun getNotesSize():Int
}