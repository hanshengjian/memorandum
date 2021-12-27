package com.hy.note.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hy.common.model.Note
import com.hy.common.navigator.NavigatorManager
import com.hy.common.navigator.NoteNavigator
import com.hy.common.repo.ReponseCall
import com.hy.note.repo.NoteRepository
import java.lang.Exception

/**
 * @Author Lenovo
 */
class NoteListViewModel : ViewModel() {
    var empty: MutableLiveData<Boolean> = MutableLiveData(false)
    var notesLiveData: MutableLiveData<List<Note>>? = MutableLiveData(mutableListOf())

    fun getNotes(type:Int = 0): MutableLiveData<List<Note>>? {

        val resObj = object : ReponseCall<List<Note>> {
            override fun onResponse(t: List<Note>) {
                if (!t.isEmpty()) {
                    notesLiveData?.value = t
                } else {
                    empty.value = true
                }

            }

            override fun onError(e: Exception) {
                empty.value = true
            }
        }
        if(type == 0){
            NoteRepository().getNotes(resObj)
        }else{
            NoteRepository().getNotesByType(type,resObj)
        }

        return notesLiveData

    }


    fun addClick() {
        NavigatorManager.getNavigator(NoteNavigator::class.java)?.addPage()
    }

    fun editPage(id:Int) {
        NavigatorManager.getNavigator(NoteNavigator::class.java)?.enterEditPage(id)
    }

}