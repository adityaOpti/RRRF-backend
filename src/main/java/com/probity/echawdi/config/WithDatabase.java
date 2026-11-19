package com.probity.echawdi.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.probity.echawdi.utils.DataSourceType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface WithDatabase {
    DataSourceType value() default DataSourceType.echmst;
}