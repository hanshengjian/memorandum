package com.hy.datacompile

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

/**
 * @Author Lenovo
 */
@Target(AnnotationTarget.CLASS)
@Retention(RetentionPolicy.CLASS)
/**
 * 有一个坑，需要采用java的Retention注解
 */
annotation class DataApi(val local: KClass<*>)
