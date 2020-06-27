package mobilsafe.example.mxw.dashen4.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.adapter.AppManagerAdapter;
import mobilsafe.example.mxw.dashen4.bean.AppInfo;

public class AppManagerActivity extends AppCompatActivity {
private TextView mRom;
private TextView mSDK;
private ListView mList;
    private List<AppInfo> infos;
    private PopupWindow popupWindow;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }
    private void initView(){
        setContentView(R.layout.activity_app_manager);
        mRom = findViewById(R.id.am_rom);
        mSDK = findViewById(R.id.am_sd);
        mList = findViewById(R.id.am_lv);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initData(){
        File ROMFile = Environment.getDataDirectory();
        long romFree = ROMFile.getFreeSpace();
        mRom.setText("内存可用：" + Formatter.formatFileSize(this,romFree));

        File SDFile = Environment.getExternalStorageDirectory();
        long freeSpace = SDFile.getFreeSpace();
        mRom.setText("SD卡可用：" + Formatter.formatFileSize(this,freeSpace));
        final List<AppInfo> list = getAllAppInfo(getApplicationContext());
        AppManagerAdapter adapter = new AppManagerAdapter(getApplicationContext(),list);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = new TextView(getApplicationContext());
                tv.setText(list.get(i).getPackname());
                tv.setTextColor(Color.RED);
                if(popupWindow != null){
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                popupWindow = new PopupWindow(tv,-2,-2);
                int[] outLocation = new int[2];
                view.getLocationInWindow(outLocation);
                popupWindow.showAtLocation(adapterView,
                        Gravity.LEFT+Gravity.TOP,60,outLocation[1]);
            }
        });
    }

    private List<AppInfo> getAllAppInfo(Context context){
        infos = new ArrayList<>();
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