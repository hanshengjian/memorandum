package com.hy.note.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hy.common.model.Note
import com.hy.common.navigator.NavigatorManager
import com.hy.common.navigator.NoteNavigator
import com.hy.common.repo.ReponseCall
import com.hy.note.repo.NoteRepository
import com.hy.utils.LogUtil

/**
 * @Author Lenovo
 */
class NoteListViewModel : ViewModel() {
    companion object {
        const val TAG = "NoteListViewModel"
    }

    var deleteNote = MutableLiveData<Note>()
    var empty: MutableLiveData<Boolean> = MutableLiveData(false)
    var notesLiveData: MutableLiveData<List<Note>>? = MutableLiveData(mutableListOf())

    fun getNotes(type: Int = 0): MutableLiveData<List<Note>>? {

        val resObj = object : ReponseCall<List<Note>> {
            override fun onResponse(t: List<Note>) {
                if (!t.isEmpty()) {
                    notesLiveData?.value = t
                } else {
                    notesLiveData?.value = mutableListOf()
                }

            }

            override fun onError(e: Exception) {
                LogUtil.i(TAG, "" + e.message)
                notesLiveData?.value = mutableListOf()
            }
        }
        if (type == -1) {
            NoteRepository().getNotes(resObj)
        } else if (type == 0) {
            NoteRepository().getNotesByNoType(resObj)
        } else {
            NoteRepository().getNotesByType(type, resObj)
        }

        return notesLiveData

    }

    fun deleteNote(note: Note?) {
        note?.let {
            NoteRepository().deleteNote(it, object : ReponseCall<Int> {
                override fun onResponse(t: Int) {
                    deleteNote.value = note
                }

                override fun onError(e: java.lang.Exception) {
                    deleteNote.value = null
                }

            })
        }
    }


    fun addClick() {
        NavigatorManager.getNavigator(NoteNavigator::class.java)?.addPage()
    }

    fun editPage(id: Int) {
        NavigatorManager.getNavigator(NoteNavigator::class.java)?.enterEditPage(id)
    }

}