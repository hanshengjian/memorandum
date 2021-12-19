package com.hy.common.navigator

import java.lang.reflect.Method

/**
 * @Author Lenovo
 */
data class NavigatorMethod(var path: String = "",
                           var params: MutableList<NatigatorParam> = mutableListOf<NatigatorParam>(),
                           var originMethod:Method) {
}