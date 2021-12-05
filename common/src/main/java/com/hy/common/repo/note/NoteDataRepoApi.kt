package com.hy.common.repo.note

import com.hy.common.data.Note
import com.hy.common.repo.BaseReq
import com.hy.common.repo.ReponseCall

/**
 * @Author Lenovo
 */
interface NoteDataRepoApi {

    fun addNote(newNote:Note, reponse: ReponseCall<Int>?): BaseReq<Int>

    fun getNotes(reponse:ReponseCall<List<Note>>?):BaseReq<List<Note>>;

    fun getNote(id:Int,reponse:ReponseCall<Note>?):BaseReq<Note>

    fun updateNote(newNote:Note, reponse: ReponseCall<Int>?): BaseReq<Int>
}