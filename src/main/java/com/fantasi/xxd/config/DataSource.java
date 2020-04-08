package com.fantasi.xxd.config;

import java.lang.annotation.*;

/**
 * @author xxd
 * @date 2019/12/27 14:19
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DBTypeEnum value() default DBTypeEnum.db1Source;
}
