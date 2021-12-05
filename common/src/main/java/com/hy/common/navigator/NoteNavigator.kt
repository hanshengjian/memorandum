package com.hy.common.navigator

import com.hy.common.arouter.Param
import com.hy.common.arouter.Route

/**
 * @Author Lenovo
 * 路由协议，定义route和param
 */
interface NoteNavigator : BaseNavigator {

    @Route(path = EDIT_PAGE_PATH)
    fun enterEditPage(@Param(name = NOTE_ID) id: Int)

    @Route(path = EDIT_PAGE_PATH)
    fun addPage()

    companion object{
        //path
        const val EDIT_PAGE_PATH: String = "/note/edit_page"
        //param
        const val NOTE_ID = "note_id"
    }
}