package com.lhd.j2s.trans.annotation;

import java.lang.annotation.*;

/**
 * 字典翻译分组注解
 * @author lhd
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictTranslating {
    DictTrans[] value() default {};
}
