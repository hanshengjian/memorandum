package com.hy.common.navigator

/**
 * @Author Lenovo
 */
data class NavigatorMethod(var path: String = "",
                           var params: MutableList<NatigatorParam> = mutableListOf<NatigatorParam>()) {
}