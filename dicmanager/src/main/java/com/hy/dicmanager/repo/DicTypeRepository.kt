package com.hy.dicmanager.repo

import com.hy.common.data.DicType
import com.hy.common.repo.BaseReq
import com.hy.common.repo.ReponseCall

/**
 * @auther:hanshengjian
 * @date:2021/12/8
 *
 */
class DicTypeRepository {
    fun addDicType(newDicType: DicType, reponse: ReponseCall<Int>?) {
    }

     fun getDicTypes(reponse: ReponseCall<List<DicType>>?) {
    }

     fun getDicType(id: Int, reponse: ReponseCall<DicType>?) {
    }

     fun updateDicType(newNote: DicType, reponse: ReponseCall<Int>?) {
    }

     fun deleteDicType(id: Int, reponse: ReponseCall<Int>){
    }

}