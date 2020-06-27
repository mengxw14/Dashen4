package mobilsafe.example.mxw.dashen4.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by MXW on 2020/6/7.
 */

public class ServiceStartUtils {

    public static boolean IsServiceRunning(Context context,String clazz){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取服务
        List<ActivityManager.RunningServiceInfo> serviceInfos = am.getRunningServices(100);
        for(ActivityManager.RunningServiceInfo info : serviceInfos){
            if(clazz.equals(info.service.getClassName())){
                //如果找到 说明服务是开启的
                return true;
            }
        }
        return false;
    }
}
