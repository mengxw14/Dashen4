package mobilsafe.example.mxw.dashen4.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.adapter.ProcessAdapter;
import mobilsafe.example.mxw.dashen4.bean.ProcessInfo;
import mobilsafe.example.mxw.dashen4.utils.Start;
import mobilsafe.example.mxw.dashen4.utils.SystemTaskUitls;

public class ProgressManager extends AppCompatActivity {

    private TextView pm_taskcount;//运行中的进程个数
    private TextView pm_men;//剩余内存和总内存
    private LinearLayout ll_loading;//正在加载数据
    private ListView pm_lv;
    private List<ProcessInfo> infos;
    private ProcessAdapter adapter;
    private Button checkAll;
    private Button backCheck;
    private Button clean;
    private Button mSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        setContentView(R.layout.activity_progress_manager);
        pm_taskcount = findViewById(R.id.pm_progresscount);
        pm_men = findViewById(R.id.pm_mem);
        ll_loading = findViewById(R.id.ll_loading);
        pm_lv = findViewById(R.id.pm_lv);
        checkAll = findViewById(R.id.pm_checkall);
        backCheck = findViewById(R.id.pm_backcheck);
        clean = findViewById(R.id.pm_uncheck);
        mSet = findViewById(R.id.pm_setting);
    }
    private void initData(){
        //PackageManager packageManager = getPackageManager();设备的静态信息
        //获取运行中的进程和已用内存和总内存
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        pm_taskcount.setText("运行中的进程：" + runningAppProcesses.size() + "个");
        ActivityManager.MemoryInfo outeinfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outeinfo);
        String availMen = Formatter.formatFileSize(this,outeinfo.availMem);
        String totalMem = Formatter.formatFileSize(this, outeinfo.totalMem);
        pm_men.setText("剩余/总内存：" + availMen + "/" + totalMem);
        try {
            File file = new File("/proc/meminfo/");
            FileInputStream fis = new FileInputStream(file);
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            //读了一行
            String line = reader.readLine();
            for(char c : line.toCharArray()){
                if(c>='0'&&c<='9'){
                    buffer.append(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ll_loading.setVisibility(View.VISIBLE);
        new Thread(){
            @Override
            public void run() {
                infos = SystemTaskUitls.getAllPorcess(getApplicationContext());
                adapter = new ProcessAdapter(getApplicationContext(),infos);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pm_lv.setAdapter(adapter);
                        ll_loading.setVisibility(View.VISIBLE);
                    }
                });
            }
        }.start();
    }
    private void initEvent(){
        //点击listview事件
        pm_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //选中了 check取反
                infos.get(i).setChecked(!infos.get(i).isChecked());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //点击全选事件
        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < infos.size(); i ++){
                    infos.get(i).setChecked(true);
                }
                adapter.notifyDataSetChanged();
            }
        });
        //反选事件
        backCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < infos.size(); i ++){
                    infos.get(i).setChecked(!infos.get(i).isChecked());
                }
                adapter.notifyDataSetChanged();
            }
        });
        //清楚所有选择项目
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                for(int i = 0; i < infos.size(); i ++){
                    if (infos.get(i).isChecked()){
                        am.killBackgroundProcesses(infos.get(i).getPackName());
                        infos.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        mSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start.Activity(getApplication(),PmSetting.class);
            }
        });
    }
}