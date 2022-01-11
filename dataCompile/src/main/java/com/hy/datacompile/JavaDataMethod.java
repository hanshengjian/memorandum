package com.hy.datacompile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auther:hanshengjian
 * @date:2022/1/10
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface JavaDataMethod {
    Class<?> entity() default Object.class;
}
