package mobilsafe.example.mxw.dashen4.bean;

import android.graphics.drawable.Drawable;

public class ProcessInfo {

    private boolean isChecked;
    private String packName;//包名 进程名
    private String name;//对应的进程名称
    private Drawable icon;//软件图标
    private long size;//
    private boolean isUserProcess;//是否为用户进程

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isUserProcess() {
        return isUserProcess;
    }

    public void setUserProcess(boolean userProcess) {
        isUserProcess = userProcess;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public ProcessInfo(String packName, String name, Drawable icon, long size, boolean isUserProcess, boolean isChecked) {
        this.packName = packName;
        this.name = name;
        this.icon = icon;
        this.size = size;
        this.isUserProcess = isUserProcess;
        this.isChecked = isChecked;
    }
}
