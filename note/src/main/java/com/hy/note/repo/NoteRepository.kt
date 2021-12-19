package com.hy.note.repo

import com.hy.common.model.Note
import com.hy.common.repo.ReponseCall
import com.hy.common.threadpool.ThreadPoolManager
import java.lang.Exception

/**
 * @Author Lenovo
 */
class NoteRepository {

    companion object{
        val TAG:String = "NoteRepository"
    }

     fun addNote(newNote: Note, reponse: ReponseCall<Int>?) {
        ThreadPoolManager.threadPool.execute({
            try {
                val t:Int = NoteLocalDataApi().addNote(newNote)
                ThreadPoolManager.mainHandler.post {
                    reponse?.onResponse(t)
                }
            }catch (e:Exception){
                ThreadPoolManager.mainHandler.post {
                    reponse?.onError(e)
                }
            }

        })
    }

     fun getNotes(reponse: ReponseCall<List<Note>>?) {
        ThreadPoolManager.threadPool.execute(){
            try {
                val datas = NoteLocalDataApi().getNote();
                ThreadPoolManager.mainHandler.post {
                    reponse?.onResponse(datas)
                }

            }catch (e:Exception){
                ThreadPoolManager.mainHandler.post {
                    reponse?.onError(e)
                }

            }
        }
    }
     fun getNote(id: Int,reponse:ReponseCall<Note>?){
        ThreadPoolManager.threadPool.execute(){
            try {
                val datas = NoteLocalDataApi().getNote(id)
                ThreadPoolManager.mainHandler.post {
                    reponse?.onResponse(datas)
                }

            }catch (e:Exception){
                ThreadPoolManager.mainHandler.post {
                    reponse?.onError(e)
                }

            }
        }

    }

     fun updateNote(newNote: Note, reponse: ReponseCall<Int>?) {
        ThreadPoolManager.threadPool.execute({
            try {
                val t:Int = NoteLocalDataApi().updateNote(newNote)
                ThreadPoolManager.mainHandler.post {
                    reponse?.onResponse(t)
                }
            }catch (e:Exception){
                ThreadPoolManager.mainHandler.post {
                    reponse?.onError(e)
                }
            }

        })
    }
}