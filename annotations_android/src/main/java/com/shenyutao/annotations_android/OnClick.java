package com.shenyutao.annotations_android;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shzy
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
// 在扫描OnClick注解的时候可以获取到@EventBase中的参数
// 这也就可以区分注解了
@EventBase(listenerSetter = "setOnClickListener"
        , listenerType = View.OnClickListener.class
        , callbackMethod = "onClick")
public @interface OnClick {
    int[] value() default -1;
}
