package com.hy.common.repo

import java.lang.Exception

/**
 * @Author Lenovo
 */
interface ReponseCall<T> {
    fun onResponse(t: T)
    fun onError(e:Exception)
}