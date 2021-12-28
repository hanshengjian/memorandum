package com.hy.note.route

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.hy.common.navigator.NoteNavigator
import com.hy.common.navigator.NoteService
import com.hy.common.repo.ReponseCall
import com.hy.note.repo.NoteRepository

/**
 * @auther:hanshengjian
 * @date:2021/12/28
 *
 */
@Route(path = NoteNavigator.NOTE_SERVICE_PATH)
class NoteServiceImpl : NoteService {
    override fun getNoteSize(expression: (Int?, String?) -> Unit) {
        val noteRepository = NoteRepository()
        noteRepository.getNotesSize(object : ReponseCall<Int> {
            override fun onResponse(t: Int) {
                expression.invoke(t, "")
            }

            override fun onError(e: Exception) {
                expression.invoke(null, e.message)
            }

        })
    }

    override fun getNotesSizeNoType(expression: (Int?, String?) -> Unit) {
        val noteRepository = NoteRepository()
        noteRepository.getNotesSizeNoType(object : ReponseCall<Int> {
            override fun onResponse(t: Int) {
                expression.invoke(t, "")
            }

            override fun onError(e: Exception) {
                expression.invoke(null, e.message)
            }

        })
    }

    override fun init(context: Context?) {

    }
}