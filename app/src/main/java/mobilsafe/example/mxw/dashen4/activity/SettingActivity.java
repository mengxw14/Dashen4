package mobilsafe.example.mxw.dashen4.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.service.CallSmsReceiver;
import mobilsafe.example.mxw.dashen4.utils.Constant;
import mobilsafe.example.mxw.dashen4.utils.SPUtils;
import mobilsafe.example.mxw.dashen4.utils.ServiceStartUtils;
import mobilsafe.example.mxw.dashen4.views.SettingItemView;

public class SettingActivity extends AppCompatActivity {

    private SettingItemView mUpdate;
    private SettingItemView mBlack;
    private SettingItemView mDoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initDate();
        initEvent();
    }
    private void initView(){
        setContentView(R.layout.activity_setting);
        mUpdate = (SettingItemView) findViewById(R.id.setting_update);
        mBlack = (SettingItemView)findViewById(R.id.setting_black);
        mDoor = (SettingItemView)findViewById(R.id.setting_door);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initDate(){
        boolean update = SPUtils.getBoolean(getApplicationContext(),Constant.UPDATE);
        mUpdate.setToggle(update);
        boolean black = ServiceStartUtils.IsServiceRunning(getApplicationContext(), "mobilsafe.example.mxw.dashen4.service.CallSmsReceiver");
        SPUtils.putBoolean(getApplication(),Constant.BLACK,black);
        mBlack.setToggle(black);
        boolean door = SPUtils.getBoolean(getApplication(),Constant.DOOR);
        mBlack.setToggle(door);
    }

    private void initEvent(){
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean update = SPUtils.getBoolean(getApplicationContext(),Constant.UPDATE);
                update =! update;
                mUpdate.setToggle(update);
                SPUtils.putBoolean(getApplicationContext(),Constant.UPDATE,update);
            }
        });
        mBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean black = SPUtils.getBoolean(getApplicationContext(),Constant.BLACK);
                black =! black;
                mBlack.setToggle(black);
                SPUtils.putBoolean(getApplicationContext(),Constant.BLACK,black);
            }
        });
        mDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean door = SPUtils.getBoolean(getApplicationContext(),Constant.DOOR);
                door =! door;
                mDoor.setToggle(door);
                SPUtils.putBoolean(getApplicationContext(),Constant.DOOR,door);
            }
        });
    }

    @Override
    protected void onDestroy() {
        boolean black = SPUtils.getBoolean(getApplication(),Constant.BLACK);
        Intent intent = new Intent(SettingActivity.this, CallSmsReceiver.class);
        if(black){
            Log.d("service", "启动服务");
            startService(intent);
        }else {
            Log.d("service", "关闭服务");
            stopService(intent);
        }
        SPUtils.putBoolean(getApplication(),Constant.PM_LOCK,false);
        super.onDestroy();
    }
}
