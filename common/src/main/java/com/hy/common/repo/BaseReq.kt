package com.hy.common.repo

import java.lang.Exception

/**
 * @Author Lenovo
 */
abstract class BaseReq<T> {
    abstract fun asynclocal()//异步
    abstract fun remote()
    abstract fun remoteLocal();
    abstract fun getLocal():T//同步
}