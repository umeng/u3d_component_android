package com.umeng.commonsdk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.util.Log;

/**
 * Created by wangfei on 17/9/22.
 */

public class UMU3DCommonSDK {
    public static void init(Context context, String appkey, String channel, int type, String secret){
      initCocos("Unity","3.0");
        UMConfigure.init(context,appkey,channel,type,secret);
    }
    public static void initCocos(String v,String t){

        Method method = null;
        try {
            Log.e("xxxxxx","v="+v+"  t="+t);
            Class<?> config = Class.forName("com.umeng.commonsdk.UMConfigure");
            method = config.getDeclaredMethod("setWraperType", String.class, String.class);
            method.setAccessible(true);
            method.invoke(null, v,t);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void setLog(boolean able){
        Log.e("cocos2d-x","success");
        UMConfigure.setLogEnabled(able);
    }
}
