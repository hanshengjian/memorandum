package com.hy.note.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hy.common.model.Note
import com.hy.common.navigator.DicManagerNavigator
import com.hy.common.navigator.NavigatorManager
import com.hy.common.repo.ReponseCall
import com.hy.note.repo.NoteRepository
import com.hy.utils.TimeUtil

/**
 * @Author Lenovo
 */
class NoteEditViewModel : ViewModel() {
    var noteId: Int? = null
    val content = MutableLiveData<String?>()
    var saveSuccess = MutableLiveData<Boolean>()
    var title = MutableLiveData<String?>()
    var time = MutableLiveData<String?>("今天" + TimeUtil.toDateHH_mm(System.currentTimeMillis()))
    var type = MutableLiveData<Int?>(0)
    var deleteSuccess = MutableLiveData<Boolean>()

    fun saveNote() {
        //保存笔记
        content.value?.let {
            var titleValue = "";
            if (title.value == null) {
                titleValue = content.value!!
            } else {
                titleValue = title.value!!
            }
            val note = Note(title = titleValue, content = content.value!!)
            type?.let {
                note.type = if(it.value==null) 0 else it.value!!
            }
            note.createTime = System.currentTimeMillis()
            noteId?.let {
                val resObj = object : ReponseCall<Int> {
                    override fun onResponse(t: Int) {
                        saveSuccess.value = true
                    }

                    override fun onError(e: Exception) {
                        saveSuccess.value = false
                    }
                }
                if(it>0){
                    note.id = it.toLong()
                    NoteRepository().updateNote(note, resObj)
                }else{
                    NoteRepository().addNote(note,resObj)
                    if(note.type>0 && NoteMemCache.dicType?.id == note.type){
                        //更新分类条数
                        NoteMemCache.dicType?.size = NoteMemCache.dicType?.size?.plus(1)!!
                        NavigatorManager.getNavigator(DicManagerNavigator::class.java)?.getDicManager()
                            ?.updateDicType(NoteMemCache.dicType!!){code,e->
                                //更新type成功
                            }
                    }
                }
            }
        }
    }


    fun getNote(page:Int,noteId: Int) {
        NoteRepository().getNote(noteId, object : ReponseCall<Note> {
            override fun onResponse(t: Note) {
                title.value = t.title
                content.value = t.content
                time.value = TimeUtil.toDateyyyy_MM_DD(t.createTime)
                type.value = t.type

            }

            override fun onError(e: Exception) {
            }
        })

    }
}