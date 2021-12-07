package com.hy.common.repo.note

import com.hy.common.data.Note
import com.hy.common.repo.BaseReq
import com.hy.common.repo.ReponseCall
import com.hy.common.threadpool.ThreadPoolManager
import java.lang.Exception

/**
 * @Author Lenovo
 */
class NoteRepository :NoteDataRepoApi{

    companion object{
        val TAG:String = "NoteRepository"
        val instance : NoteRepository = NoteRepository()
    }

    override fun addNote(newNote: Note, reponse: ReponseCall<Int>?): BaseReq<Int> {
        return object : BaseReq<Int>(){
            override fun asynclocal() {
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
        }
    }

    override fun getNotes(reponse: ReponseCall<List<Note>>?): BaseReq<List<Note>> {
        return object :BaseReq<List<Note>>(){
            override fun asynclocal() {
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
        }
    }
    override fun getNote(id: Int,reponse:ReponseCall<Note>?): BaseReq<Note> {
        return object :BaseReq<Note>(){
            override fun asynclocal() {
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

        }
    }

    override fun updateNote(newNote: Note, reponse: ReponseCall<Int>?): BaseReq<Int> {
        return object : BaseReq<Int>(){
            override fun asynclocal() {
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
    }
}