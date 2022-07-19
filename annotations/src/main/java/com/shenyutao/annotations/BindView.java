package com.shenyutao.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shzy
 * 注解，单独没有意义
 * 注解 + APT  用于生成一些java文件 bufferKnife Dogger2 hilt DataBinding ......
 * 注解 + 反射，动态代理  retrofit lifecycle
 * 注解 + 代码埋点  AspactJ Arouter
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface BindView {
    int value();
}
