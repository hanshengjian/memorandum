package com.hy.dicmanager.router

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.hy.common.model.DicType
import com.hy.common.navigator.DicManagerNavigator
import com.hy.common.navigator.DicManagerService
import com.hy.common.repo.ReponseCall
import com.hy.common.room.AppDatabaseInstance
import com.hy.dicmanager.repo.DicTypeRepository
import java.lang.Exception

/**
 * @auther:hanshengjian
 * @date:2021/12/16
 *
 */
@Route(path = DicManagerNavigator.ADD_DIC_TYPE_SERVICE)
class DicManagerServiceImpl:DicManagerService {
    override fun getDicList(page: Int, expression: (List<DicType>?,String?) -> Unit) {
        val dicTypeRepository = DicTypeRepository()
        dicTypeRepository.getDicTypes(page, object:ReponseCall<List<DicType>>{
            override fun onResponse(t: List<DicType>) {
                expression.invoke(t,null)
            }

            override fun onError(e: Exception) {
                expression.invoke(null,e.message)
            }
        })
    }

    override fun addDicType(page: Int, title: String, expression: (Int,String?) -> Unit) {
        val dicTypeRepository = DicTypeRepository()
        val dicType = DicType(page = page,content = title,createTime = System.currentTimeMillis())
        dicTypeRepository.addDicType(dicType,object: ReponseCall<Int>{
            override fun onResponse(t: Int) {
                expression.invoke(t,"")
            }

            override fun onError(e: Exception) {
                expression.invoke(-1,e.message)
            }
        })
    }

    override fun init(context: Context?) {

    }
}