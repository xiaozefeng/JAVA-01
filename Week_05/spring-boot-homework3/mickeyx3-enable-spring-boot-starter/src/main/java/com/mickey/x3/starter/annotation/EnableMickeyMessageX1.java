package com.mickey.x3.starter.annotation;

import com.mickey.x3.starter.config.MickeyX3AutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mickey
 * @date 2/15/21 17:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MickeyX3AutoConfiguration.class)
public @interface EnableMickeyMessageX1 {

}
