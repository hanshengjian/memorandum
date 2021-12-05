package com.hy.common.repo.note

import com.hy.common.data.Note
import com.hy.common.repo.BaseReq
import com.hy.datacompile.DataApi
import com.hy.datacompile.DataMethod

/**
 * @Author Lenovo
 */
@DataApi
interface NoteDataApi {
    @DataMethod
    fun addNote(newNote:Note):Int

    @DataMethod
    fun getNote():List<Note>

    @DataMethod
    fun getNote(id:Int):Note

    @DataMethod
    fun updateNote(updateNote:Note):Int
}