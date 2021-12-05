package com.hy.datacompile

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @Author Lenovo
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(RetentionPolicy.CLASS)
annotation class DataMethod {
}