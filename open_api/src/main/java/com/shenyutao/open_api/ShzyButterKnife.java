package com.shenyutao.open_api;

import android.app.Activity;

/**
 * @author shzy
 */
public class ShzyButterKnife {
    public static void bind(Activity activity){
        String name = activity.getClass().getName() + "_ViewBinding";
        try {
            Class<?> aClass = Class.forName(name);
            IBinder iBinder = (IBinder) aClass.newInstance();
            iBinder.bind(activity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
