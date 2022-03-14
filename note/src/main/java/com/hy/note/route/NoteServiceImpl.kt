package com.hy.note.route

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.hy.common.HyVariable
import com.hy.common.navigator.NoteNavigator
import com.hy.common.navigator.NoteService
import com.hy.common.repo.ReponseCall
import com.hy.note.repo.NoteDataApiRepository

/**
 * @auther:hanshengjian
 * @date:2021/12/28
 *
 */
@Route(path = NoteNavigator.NOTE_SERVICE_PATH)
class NoteServiceImpl : NoteService {
    override fun getNoteSize(expression: (Int?, String?) -> Unit) {
        val noteRepository = NoteDataApiRepository()
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
        val noteRepository = NoteDataApiRepository()
        noteRepository.getNoteSizeByType(HyVariable.NO_TYPE, object : ReponseCall<Int> {
            override fun onResponse(t: Int) {
                expression.invoke(t, "")
            }

            override fun onError(e: Exception) {
                expression.invoke(null, e.message)
            }

        })
    }

    override fun getNoteSizeByType(type: Int, expression: (Int?, String?) -> Unit) {
        val noteRepository = NoteDataApiRepository()
        if (type == HyVariable.ALL_TYPE) {
            //全部
            noteRepository.getNotesSize(object : ReponseCall<Int> {
                override fun onResponse(t: Int) {
                    expression.invoke(t, "")
                }

                override fun onError(e: Exception) {
                    expression.invoke(null, e.message)
                }

            })
        } else if (type == HyVariable.DELETE_TYPE) {
            //最近删除
            noteRepository.getDeletedNoteSize(object : ReponseCall<Int> {
                override fun onResponse(t: Int) {
                    expression.invoke(t, "")
                }

                override fun onError(e: Exception) {
                    expression.invoke(null, e.message)
                }

            })
        } else {
            noteRepository.getNoteSizeByType(type, object : ReponseCall<Int> {
                override fun onResponse(t: Int) {
                    expression.invoke(t, "")
                }

                override fun onError(e: Exception) {
                    expression.invoke(null, e.message)
                }

            })
        }

    }

    override fun getDeletedNoteSize(expression: (Int?, String?) -> Unit) {
        val noteRepository = NoteDataApiRepository()
        noteRepository.getDeletedNoteSize(object : ReponseCall<Int> {
            override fun onResponse(t: Int) {
                expression.invoke(t, "")
            }

            override fun onError(e: java.lang.Exception) {
                expression.invoke(null, e.message)
            }

        })
    }

    override fun init(context: Context?) {

    }
}