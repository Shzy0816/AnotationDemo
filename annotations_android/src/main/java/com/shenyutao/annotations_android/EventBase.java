package com.shenyutao.annotations_android;

/**
 * @author Shzy
 */

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 通过这个注解实现 注解多态
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    // setOnClickListener
    String listenerSetter();

    Class<?> listenerType();

    String callbackMethod();
}
