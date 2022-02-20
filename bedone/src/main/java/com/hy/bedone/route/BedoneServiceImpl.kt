package com.hy.bedone.route

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.hy.bedone.repo.BedoneDataApiRepository
import com.hy.common.navigator.BedoneNavigator
import com.hy.common.navigator.BedoneService
import com.hy.common.repo.ReponseCall

@Route(path = BedoneNavigator.BEDONE_SERVICE_PATH)
class BedoneServiceImpl : BedoneService {
    override fun getBedoneSize(expression: (Int?, String?) -> Unit) {
        BedoneDataApiRepository().getBedonSize(object : ReponseCall<Int> {
            override fun onResponse(t: Int) {
                expression?.invoke(t, "")
            }

            override fun onError(e: Exception) {
                //todo 打印错误日志
                expression?.invoke(null, e.message)
            }

        })
    }

    override fun getBedoneSizeNoType(expression: (Int?, String?) -> Unit) {

    }

    override fun init(context: Context?) {

    }
}