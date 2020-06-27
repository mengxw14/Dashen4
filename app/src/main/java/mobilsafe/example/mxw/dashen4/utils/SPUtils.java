package mobilsafe.example.mxw.dashen4.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MXW on 2020/5/29.
 * 用来管理sharepreference
 */

public class SPUtils {

    public static SharedPreferences getSp(Context c){
        SharedPreferences sp = c.getSharedPreferences(Constant.UPDATE,c.MODE_PRIVATE);
        return sp;
    }
    public static boolean getBoolean(Context c,String key){
        SharedPreferences sp = getSp(c);
        return sp.getBoolean(key,false);
    }
    public static void putBoolean(Context c,String key,boolean state){
        SharedPreferences sp = getSp(c);
        sp.edit().putBoolean(key,state).commit();
    }
    public static String getString(Context c,String key){
        SharedPreferences sp = getSp(c);
        return sp.getString(key,null);
    }
    public static void putString(Context c,String key,String value){
        SharedPreferences sp = getSp(c);
        sp.edit().putString(key,value).commit();
    }
}
