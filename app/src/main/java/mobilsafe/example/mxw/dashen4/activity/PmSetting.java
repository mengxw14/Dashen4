package mobilsafe.example.mxw.dashen4.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.receiver.LockReceiver;
import mobilsafe.example.mxw.dashen4.utils.Constant;
import mobilsafe.example.mxw.dashen4.utils.SPUtils;
import mobilsafe.example.mxw.dashen4.views.SettingItemView;

public class PmSetting extends AppCompatActivity {
    private SettingItemView mlock;
    private boolean isLock;
    private LockReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }
    private void initView(){
        setContentView(R.layout.activity_pm_setting);
        mlock = findViewById(R.id.pm_setting_lock);
    }
    private void initData(){
        isLock = SPUtils.getBoolean(getApplication(), Constant.PM_LOCK);
        mlock.setToggle(isLock);
    }
    private void initEvent(){
        mlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLock = !isLock;
                SPUtils.putBoolean(getApplication(),Constant.PM_LOCK,isLock);
                if(isLock){
                    //开启则注册广播
                    receiver = new LockReceiver();
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(Intent.ACTION_SCREEN_OFF);
                    registerReceiver(receiver,filter);
                }else {
                    //否则就关闭广播
                    unregisterReceiver(receiver);
                }
                mlock.setToggle(isLock);
            }
        });
    }

}