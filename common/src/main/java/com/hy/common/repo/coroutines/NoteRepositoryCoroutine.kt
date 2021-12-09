package com.hy.common.repo.coroutines

import com.hy.common.data.Note
import com.hy.common.exception.MemException
import com.hy.common.repo.ReponseCall
import kotlinx.coroutines.*

/**
 * @Author Lenovo
 * 协程版
 */
class NoteRepositoryCoroutine : NoteDataRepoApiCoroutine {

    companion object {
        val TAG: String = "NoteRepository"
        val instance: NoteRepositoryCoroutine = NoteRepositoryCoroutine()
    }

    override fun addNote(newNote: Note, reponse: ReponseCall<Int>?) {
        MainScope().launch {
            val deferred = async(Dispatchers.IO){
                NoteLocalDataApiCoroutine().addNote(newNote)
            }
            val id = deferred.await()
            if(id>0){
                reponse?.onResponse(id)
            }else{
                reponse?.onError(MemException("添加失败"))
            }
        }
    }

    override fun getNotes(reponse: ReponseCall<List<Note>>?) {
        MainScope().launch {
            val deferred = async(Dispatchers.IO){
                NoteLocalDataApiCoroutine().getNote()
            }
            val notes = deferred.await()
            if(notes!=null){
                reponse?.onResponse(notes)
            }else{
                reponse?.onError(MemException("获取失败"))
            }
        }
    }

    override fun getNote(id: Int, reponse: ReponseCall<Note>?) {
        MainScope().launch {
            val deferred = async(Dispatchers.IO){
                NoteLocalDataApiCoroutine().getNote(id)
            }
            val note = deferred.await()
            if(note!=null){
                reponse?.onResponse(note)
            }else{
                reponse?.onError(MemException("获取失败"))
            }
        }
    }

    override fun updateNote(newNote: Note, reponse: ReponseCall<Int>?) {
        MainScope().launch {
            val deferred = async(Dispatchers.IO){
                NoteLocalDataApiCoroutine().updateNote(newNote)
            }
            val id = deferred.await()
            if(id>0){
                reponse?.onResponse(id)
            }else{
                reponse?.onError(MemException("更新失败"))
            }
        }
    }
}