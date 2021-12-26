package com.hy.dicmanager.repo

import com.hy.common.model.DicType
import com.hy.common.repo.ReponseCall
import com.hy.common.threadpool.ThreadPoolManager
import java.lang.Exception

/**
 * @auther:hanshengjian
 * @date:2021/12/8
 *
 */
class DicTypeRepository {
    fun addDicType(newDicType: DicType, reponse: ReponseCall<Int>?) {
        ThreadPoolManager.threadPool.execute {
            try {
                val t: Int = DicTypeLocalDataApi().addDicType(newDicType)
                ThreadPoolManager.mainHandler.post {
                    reponse?.onResponse(t)
                }
            } catch (e: Exception) {
                ThreadPoolManager.mainHandler.post {
                    reponse?.onError(e)
                }
            }

        }
    }

     fun getDicTypes(page:Int,reponse: ReponseCall<List<DicType>>?) {
         ThreadPoolManager.threadPool.execute {
             try {
                 val dicTypes = DicTypeLocalDataApi().getDicTypes(page)
                 ThreadPoolManager.mainHandler.post {
                     reponse?.onResponse(dicTypes)
                 }
             }catch (e:Exception){
                 ThreadPoolManager.mainHandler.post {
                     reponse?.onError(e)
                 }
             }
         }
    }

     fun getDicType(id: Int, reponse: ReponseCall<DicType?>?) {
         ThreadPoolManager.threadPool.execute{
             try {
                 val dicTypes = DicTypeLocalDataApi().getDicType(id)
                 ThreadPoolManager.mainHandler.post {
                     reponse?.onResponse(dicTypes)
                 }
             }catch (e:Exception){
                 ThreadPoolManager.mainHandler.post {
                     reponse?.onError(e)
                 }
             }
         }
    }

    fun updateDicType(updateDicType: DicType, reponse: ReponseCall<Int>?) {
        ThreadPoolManager.threadPool.execute{
            try {
                val code = DicTypeLocalDataApi().updateDicType(updateDicType)
                ThreadPoolManager.mainHandler.post {
                    reponse?.onResponse(code)
                }
            }catch (e:Exception){
                ThreadPoolManager.mainHandler.post {
                    reponse?.onError(e)
                }
            }
        }
    }

    fun deleteDicType(id: Int, reponse: ReponseCall<Int>){

    }

}