package com.hy.bedone

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hy.bedone.repo.BedoneDataApiRepository
import com.hy.bedone.widget.BedoneAddDialog
import com.hy.common.model.Bedone
import com.hy.common.repo.ReponseCall

class BeDoneViewModel : ViewModel() {
    var empty: MutableLiveData<Boolean> = MutableLiveData()
    var saveSuccess = MutableLiveData<Bedone>()
    var bedonesLiveData: MutableLiveData<List<Bedone>> = MutableLiveData()
    var type: Int = 0

    fun getBedones(type: Int) {
        if (type == 0) {
            BedoneDataApiRepository().getBedones(object : ReponseCall<List<Bedone>> {
                override fun onResponse(t: List<Bedone>) {
                    bedonesLiveData?.value = t
                }

                override fun onError(e: java.lang.Exception) {
                    bedonesLiveData?.value = mutableListOf()
                }
            })

        }
    }

    fun addClick(view: View) {
        val bedoneAddDialog = BedoneAddDialog(view.context, type) {
            it?.apply {
                BedoneDataApiRepository().addBedone(this, object : ReponseCall<Int> {
                    override fun onResponse(t: Int) {
                        saveSuccess.value = it
                    }

                    override fun onError(e: Exception) {
                        saveSuccess.value = null
                    }

                })
            }
        }
        bedoneAddDialog.show()
    }

}