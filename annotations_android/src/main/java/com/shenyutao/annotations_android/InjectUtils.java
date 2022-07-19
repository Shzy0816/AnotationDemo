package com.shenyutao.annotations_android;

import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * @author Shzy
 */
public class InjectUtils {
    public static void inject(Object context){
        injectClick(context);
    }

    private static void injectClick(Object context){
        Class<?> contextClazz = context.getClass();
        Method[] methods = contextClazz.getDeclaredMethods();
        for (Method method : methods) {
            // 获取方法的所有注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<?> annotationClass = annotation.annotationType();
                // 处理所有注解中 又包含EventBase注解的注解
                EventBase eventBase = annotationClass.getAnnotation(EventBase.class);
                if(eventBase == null) {
                    continue;
                }
                // 获取EventBase注解中传入的参数
                String listenerSetter = eventBase.listenerSetter();
                Class<?> listenerType = eventBase.listenerType();
                String callbackMethod = eventBase.callbackMethod();

                //动态代理
                Method valueMethod = null;
                try {
                    // 执行OnClick注解中的value方法，获取到所有id
                    valueMethod = annotationClass.getDeclaredMethod("value");
                    int[] ids = (int[]) valueMethod.invoke(annotation);
                    for (int id : ids) {
                        Method findViewById = contextClazz.getMethod("findViewById",int.class);
                        View view = (View) findViewById.invoke(context,id);
                        if(view == null){
                            continue;
                        }
                        ListenerInvocationHandler listenerInvocationHandler = new ListenerInvocationHandler(context,method);
                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                new Class[]{listenerType}
                                ,listenerInvocationHandler);

                        Method setOnClickListener = view.getClass().getMethod(listenerSetter,listenerType);
                        setOnClickListener.invoke(view,proxy);
                    }
                }catch (Exception e){

                }

            }
        }

    }
}
