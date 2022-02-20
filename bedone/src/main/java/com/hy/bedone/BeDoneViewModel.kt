package com.hy.bedone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BeDoneViewModel : ViewModel() {
    var empty: MutableLiveData<Boolean> = MutableLiveData()

    fun addBedone() {

    }

    fun getBedones(type: Int) {

    }

    fun addClick() {
        //todo 弹出dialog
    }

}