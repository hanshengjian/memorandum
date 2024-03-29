package com.hy.bedone.route

import android.content.Context
import cody.bus.ElegantBus
import com.alibaba.android.arouter.facade.annotation.Route
import com.hy.bedone.repo.BedoneDataApiRepository
import com.hy.bedone.widget.BedoneAddDialog
import com.hy.common.HyVariable
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
        //type 特殊类型
        BedoneDataApiRepository().getBedonesSizeByType(HyVariable.NO_TYPE,
            object : ReponseCall<Int> {
                override fun onResponse(t: Int) {
                    expression?.invoke(t, "")
                }

                override fun onError(e: Exception) {
                    //todo 打印错误日志
                    expression?.invoke(null, e.message)
                }

            })
    }

    override fun getBedoneSizeByType(type: Int, expression: (Int?, String?) -> Unit) {
        if (type == HyVariable.ALL_TYPE) {
            //q全部
            BedoneDataApiRepository().getBedonSize(object : ReponseCall<Int> {
                override fun onResponse(t: Int) {
                    expression?.invoke(t, "")
                }

                override fun onError(e: Exception) {
                    //todo 打印错误日志
                    expression?.invoke(null, e.message)
                }

            })
        } else if (type == HyVariable.DELETE_TYPE) {
            //删除
            BedoneDataApiRepository().getDeletedBedoneSize(object : ReponseCall<Int> {
                override fun onResponse(t: Int) {
                    expression?.invoke(t, "")
                }

                override fun onError(e: Exception) {
                    //todo 打印错误日志
                    expression?.invoke(null, e.message)
                }

            })
        } else {
            BedoneDataApiRepository().getBedonesSizeByType(type, object : ReponseCall<Int> {
                override fun onResponse(t: Int) {
                    expression?.invoke(t, "")
                }

                override fun onError(e: Exception) {
                    //todo 打印错误日志
                    expression?.invoke(null, e.message)
                }

            })
        }

    }


    override fun addBedone(context: Context, type: Int) {
        val bedoneAddDialog = BedoneAddDialog(
            context,
            if (type == HyVariable.ALL_TYPE) HyVariable.NO_TYPE else type
        ) {
            it?.apply {
                BedoneDataApiRepository().addBedone(this, object : ReponseCall<Int> {
                    override fun onResponse(t: Int) {
                        ElegantBus.getDefault("bedoneSaveState").post(type);
                    }

                    override fun onError(e: Exception) {
                        ElegantBus.getDefault("bedoneSaveState").post(type);
                    }

                })
            }
        }
        bedoneAddDialog.show()
    }

    override fun getDeletedBedoneSize(expression: (Int?, String?) -> Unit) {
        BedoneDataApiRepository().getDeletedBedoneSize(object : ReponseCall<Int> {
            override fun onResponse(t: Int) {
                expression?.invoke(t, "")
            }

            override fun onError(e: Exception) {
                //todo 打印错误日志
                expression?.invoke(null, e.message)
            }

        })
    }

    override fun init(context: Context?) {

    }
}