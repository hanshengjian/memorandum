package com.hy.common.repo.coroutines

import com.hy.common.model.Note
import com.hy.common.repo.ReponseCall

/**
 * @Author Lenovo
 */
interface NoteDataRepoApiCoroutine {

    fun addNote(newNote:Note, reponse: ReponseCall<Int>?)

    fun getNotes(reponse:ReponseCall<List<Note>>?)

    fun getNote(id:Int,reponse:ReponseCall<Note>?)

    fun updateNote(newNote:Note, reponse: ReponseCall<Int>?)
}