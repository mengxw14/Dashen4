package mobilsafe.example.mxw.dashen4.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import mobilsafe.example.mxw.dashen4.bean.ProcessInfo;

public class SystemTaskUitls {


    /**
     * 获取设备上面的所有进程信息
     * @param context 上下文
     * @return 返回进程集合
     */
    public static List<ProcessInfo> getAllPorcess(Context context){
        List<ProcessInfo> infos = new ArrayList<>();
        ActivityManager am  = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        PackageManager pm = context.getPackageManager();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info:runningAppProcesses) {
            String processName = info.processName;//进程名称 app的包名
            long size = am.getProcessMemoryInfo(new int[]{info.pid})[0].getTotalPrivateDirty() * 1024;
            try {
                PackageInfo packageInfo = pm.getPackageInfo(processName, 0);
                String name = packageInfo.applicationInfo.loadLabel(pm).toString();
                Drawable icon = packageInfo.applicationInfo.loadIcon(pm);
                int flages = packageInfo.applicationInfo.flags;
                boolean is = false;
                if((flages & ApplicationInfo.FLAG_SYSTEM) == 0){
                    //说明是一个用户进程
                    is = true;
                }
                ProcessInfo processInfo = new ProcessInfo(processName,name,icon,size,is,false);
                infos.add(processInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return infos;
    }
}
