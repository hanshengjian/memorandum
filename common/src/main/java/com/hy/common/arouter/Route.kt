package com.hy.common.arouter

/**
 * @Author Lenovo
 */

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Route(val path:String) {

}