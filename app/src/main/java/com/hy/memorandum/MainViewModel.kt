package com.hy.memorandum

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hy.common.navigator.BedoneNavigator
import com.hy.common.navigator.NavigatorManager
import com.hy.common.navigator.NoteNavigator

/**
 * @auther:hanshengjian
 * @date:2022/3/14
 *
 */
class MainViewModel : ViewModel() {

    var dicTypeSize = MutableLiveData<Int>()

    fun getDicTypeSize(id: Int, page: Int) {
        if (page == 0) {
            NavigatorManager.getNavigator(NoteNavigator::class.java)?.getNoteService()
                ?.getNoteSizeByType(id) { size, msg ->
                    dicTypeSize.value = size
                }
        } else {
            NavigatorManager.getNavigator(BedoneNavigator::class.java)?.getBedoneService()
                ?.getBedoneSizeByType(id) { size, msg ->
                    dicTypeSize.value = size
                }
        }
    }

}