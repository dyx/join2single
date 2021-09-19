package com.lhd.j2s.trans.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 字典翻译注解
 * @author lhd
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictTrans {
    String typeCode();
    String readFieldName();
    String writeFieldName() default "";
}
