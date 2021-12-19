package com.hy.common.navigator

import com.hy.common.arouter.Route

/**
 * @auther:hanshengjian
 * @date:2021/12/16
 *
 */
interface DicManagerNavigator:BaseNavigator {

    @Route(path = ADD_DIC_TYPE_SERVICE)
    fun getDicManager():DicManagerService;

    companion object{
        //path
        const val ADD_DIC_TYPE_SERVICE: String = "/dictype/service"
        //param
        const val NOTE_ID = "note_id"
    }
}