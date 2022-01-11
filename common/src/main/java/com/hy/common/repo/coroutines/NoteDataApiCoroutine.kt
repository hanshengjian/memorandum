package com.hy.common.repo.coroutines

import com.hy.common.model.Note

/**
 * @Author Lenovo
 */
//@DataApi(local = NoteLocalDataApiCoroutine::class)
interface NoteDataApiCoroutine {
    // @DataMethod
    suspend fun addNote(newNote:Note):Int

    // @DataMethod
    suspend fun getNote():List<Note>

    // @DataMethod
    suspend fun getNote(id:Int):Note

    //  @DataMethod
    suspend fun updateNote(updateNote:Note):Int
}