package mobilsafe.example.mxw.dashen4.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by MXW on 2020/5/29.
 * @desc 用来获取和系统信息相关数据
 */

public class SystemInfoUtils {
    //获得对应报名版本信息
    public static String getVersionName(Context c) throws PackageManager.NameNotFoundException {
        PackageManager pm = c.getPackageManager();
        PackageInfo info = pm.getPackageInfo(c.getPackageName(), 0);
        String vn = info.versionName;
        return vn;
    }
    public static int getVersionCode(Context c) throws PackageManager.NameNotFoundException {
        PackageManager pm = c.getPackageManager();
        PackageInfo info = pm.getPackageInfo(c.getPackageName(), 0);
        int vc = info.versionCode;
        return vc;
    }
}
