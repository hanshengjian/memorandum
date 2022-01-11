package com.hy.datacompile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auther:hanshengjian
 * @date:2022/1/10
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface JavaDataApi {
    Class<?> local() default Object.class;
}
