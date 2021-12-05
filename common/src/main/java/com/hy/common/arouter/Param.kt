package com.hy.common.arouter

/**
 * @Author Lenovo
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Param(val name:String) {
}