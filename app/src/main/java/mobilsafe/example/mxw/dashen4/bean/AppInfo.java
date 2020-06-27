package mobilsafe.example.mxw.dashen4.bean;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//所有安装软件的信息
public class AppInfo {
    private String name;//软件名称
    private String packname;//软件的包名
    private long size;
    private boolean isRom;//是否安装在手机内存里面
    private Drawable icon;// 软件的图片
    private int Id;

    public AppInfo(String name, String packname, long size, boolean isRom, Drawable icon,int Id) {
        this.name = name;
        this.packname = packname;
        this.size = size;
        this.isRom = isRom;
        this.icon = icon;
        this.Id = Id;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isRom() {
        return isRom;
    }

    public void setRom(boolean rom) {
        isRom = rom;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }


    public List<AppInfo> getAllAppInfo(Context context){
        List<AppInfo> infos = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        //获得当前所有安装在该设备上面的所有数据集合
        List<PackageInfo> infoList = pm.getInstalledPackages(0);
        int i = 0;
        for (PackageInfo info:
             infoList) {
            //包名
            String packname = info.packageName;
            //图标
            Drawable icon = info.applicationInfo.loadIcon(pm);
            //名称
            String name = info.applicationInfo.loadLabel(pm).toString();
            //app路径
            String path = info.applicationInfo.sourceDir;
            //统计app大小
            File file = new File(path);
            long size = file.length();
            int flags = info.applicationInfo.flags;
            //判断是否为安装在SD卡上
            Boolean isRom = true;
            if((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == 0){
                isRom = false;
            }else {
                isRom = true;
            }
            AppInfo appInfo = new AppInfo(name,packname,size,isRom,icon,i);
            infos.add(appInfo);
            i ++;
        }
        return infos;
    }
}
