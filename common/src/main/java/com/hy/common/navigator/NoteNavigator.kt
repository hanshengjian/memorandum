package com.hy.common.navigator

import com.hy.common.arouter.Param
import com.hy.common.arouter.Route
import com.hy.common.navigator.NoteNavigator.Companion.NOTE_SERVICE_PATH

/**
 * @Author Lenovo
 * 路由协议，定义route和param
 */
interface NoteNavigator : BaseNavigator {

    @Route(path = EDIT_PAGE_PATH)
    fun enterEditPage(@Param(name = NOTE_ID) id: Int)

    @Route(path = EDIT_PAGE_PATH)
    fun addPage()

    @Route(path = NOTE_SERVICE_PATH)
    fun getNoteService():NoteService

    companion object{
        //path
        const val EDIT_PAGE_PATH: String = "/note/edit_page"
        //service
        const val NOTE_SERVICE_PATH:String = "/note/service"
        //param
        const val NOTE_ID = "note_id"
    }
}