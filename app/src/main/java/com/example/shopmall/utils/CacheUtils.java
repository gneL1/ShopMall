package com.example.shopmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.shopmall.MyApplication;

import java.util.Map;
import java.util.Set;

public class CacheUtils {

    /**
     * Gets string.
     *得到保持的String类型的数据
     * @param mContext the context
     * @param key     the key
     * @return the string
     */
    public static String getString(Context mContext,String key) {
        if (mContext != null){
            System.out.println("context传进来了");
        }
        else {
            System.out.println("context没传进来");
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ShoppingGoods",Context.MODE_PRIVATE);
        return  sharedPreferences.getString(key,"");
    }

    /**
     * Save string.
     *保存String类型的数据
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void saveString(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("ShoppingGoods",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
}
