package com.hy.bedone

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hy.bedone.repo.BedoneDataApiRepository
import com.hy.bedone.widget.BedoneAddDialog
import com.hy.common.repo.ReponseCall

class BeDoneViewModel : ViewModel() {
    var empty: MutableLiveData<Boolean> = MutableLiveData()
    var saveSuccess = MutableLiveData<Boolean>()
    var type: Int = 0

    fun addBedone() {

    }

    fun getBedones(type: Int) {

    }

    fun addClick(view: View) {
        val bedoneAddDialog = BedoneAddDialog(view.context, type) {
            it?.apply {
                BedoneDataApiRepository().addBedone(this, object : ReponseCall<Int> {
                    override fun onResponse(t: Int) {
                        saveSuccess.value = true
                    }

                    override fun onError(e: Exception) {
                        saveSuccess.value = false
                    }

                })
            }
        }
        bedoneAddDialog.show()
    }

}