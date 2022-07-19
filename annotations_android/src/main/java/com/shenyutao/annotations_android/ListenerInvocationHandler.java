package com.shenyutao.annotations_android;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Shzy
 */
public class ListenerInvocationHandler implements InvocationHandler {
    private Object activity;
    private Method activityMethod;

    public ListenerInvocationHandler(Object activity, Method activityMethod) {
        this.activity = activity;
        this.activityMethod = activityMethod;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        return activityMethod.invoke(activity,objects);
    }
}
