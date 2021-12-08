package com.tuyrt.architecture.util;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by tuyrt7 on 2021/12/7.
 * 说明：
 */
public class AppGlobals {

    private static Application sApplication;

    public static Application get() {
        if (sApplication == null) {
            try {
                Method method = Class.forName("android.app.ActivityThread").
                        getDeclaredMethod("currentApplication");
                sApplication = (Application) method.invoke(null);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return sApplication;
    }
}
