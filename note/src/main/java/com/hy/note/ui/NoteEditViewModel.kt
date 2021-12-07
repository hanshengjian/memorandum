package com.hy.note.ui

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hy.common.base.BaseApp
import com.hy.common.data.Note
import com.hy.common.extend.getApplication
import com.hy.common.repo.LocalDataManager
import com.hy.common.repo.ReponseCall
import com.hy.common.repo.coroutines.NoteRepositoryCoroutine
import com.hy.common.repo.note.NoteRepository
import java.lang.Exception

/**
 * @Author Lenovo
 */
class NoteEditViewModel:ViewModel() {
    var noteId:Int ?=null
    val content = MutableLiveData<String?>()
    var saveSuccess = MutableLiveData<Boolean>()

    fun saveNote() {
        //保存笔记
        if(noteId!! ==0){
            content.value?.let {
                val note = Note(content = content.value!!)
                note.createTime = System.currentTimeMillis()
                NoteRepositoryCoroutine.instance.addNote(note,object : ReponseCall<Int>{
                    override fun onResponse(t: Int) {
                        saveSuccess.value = true
                       // Toast.makeText(getApplication(),"保存成功",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Exception) {
                        saveSuccess.value = false
                        //Toast.makeText(getApplication(),"保存失败",Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }else {
            //这个流程有问题
            NoteRepositoryCoroutine.instance.getNote(noteId!!,object :ReponseCall<Note>{
                override fun onResponse(t: Note) {
                    NoteRepositoryCoroutine.instance.updateNote(t,object:ReponseCall<Int>{
                        override fun onResponse(t: Int) {
                            saveSuccess.value = true
                        }

                        override fun onError(e: Exception) {
                            saveSuccess.value = false
                        }

                    })
                }

                override fun onError(e: Exception) {

                }

            })
        }
    }
}