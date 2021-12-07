package com.hy.common.repo.coroutines

import com.hy.common.data.Note
import com.hy.common.repo.BaseReq
import com.hy.datacompile.DataApi
import com.hy.datacompile.DataMethod

/**
 * @Author Lenovo
 */
@DataApi
interface NoteDataApiCoroutine {
    @DataMethod
    suspend fun addNote(newNote:Note):Int

    @DataMethod
    suspend fun getNote():List<Note>

    @DataMethod
    suspend fun getNote(id:Int):Note

    @DataMethod
    suspend fun updateNote(updateNote:Note):Int
}