package mobilsafe.example.mxw.dashen4.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.utils.Constant;
import mobilsafe.example.mxw.dashen4.utils.SPUtils;
import mobilsafe.example.mxw.dashen4.utils.Start;

import static mobilsafe.example.mxw.dashen4.utils.SPUtils.getSp;
import static mobilsafe.example.mxw.dashen4.utils.SPUtils.putBoolean;

public class Setup2Activity extends AppCompatActivity {

    private ImageView mPre;
    private ImageView mNext;
    private RelativeLayout mLock;
    private boolean isLocked;
    private ImageView mImg;
    private String simSerialNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initView(){
        setContentView(R.layout.activity_setup2);
        mPre = (ImageView) findViewById(R.id.img_pre);
        mNext = (ImageView)findViewById(R.id.img_next);
        mLock = (RelativeLayout) findViewById(R.id.lock_button);
        mImg = (ImageView)findViewById(R.id.lock_sim);
    }

    private void initData(){
        isLocked = SPUtils.getSp(getApplicationContext()).getBoolean(Constant.LOCK,false);
        mImg.setImageResource(isLocked?R.drawable.lock:R.drawable.unlock);
        //请求权限
        ActivityCompat.requestPermissions(Setup2Activity.this,
                new String[]{Manifest.permission.READ_CONTACTS},2);
    }
    //请求权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                }

            }
        }
    }

    private void initEvent() {
        mPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start.Activity(getApplicationContext(), Setup1Activity.class);
                overridePendingTransition(R.anim.pre_in, R.anim.pre_out);
                finish();
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLocked) {
                    //已绑定,进入下一界面
                    SPUtils.putBoolean(getApplicationContext(),Constant.LOCK, isLocked);
                    locksim();
                    Start.Activity(getApplicationContext(), Setup3Activity.class);
                    overridePendingTransition(R.anim.next_in, R.anim.next_out);
                    finish();
                } else {
                    //未绑定
                    Toast.makeText(getApplicationContext(), "请绑定sim卡", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLocked = !isLocked;
                mImg.setImageResource(isLocked ? R.drawable.lock : R.drawable.unlock);
            }
        });
    }
        private void locksim() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝

                // sd卡权限
                String[] SdCardPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (ContextCompat.checkSelfPermission(this, SdCardPermission[0]) != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有授予该权限，就去提示用户请求
                    ActivityCompat.requestPermissions(this, SdCardPermission, 100);
                }
            }
            TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            simSerialNumber = tm.getSimSerialNumber();
            SPUtils.putString(getApplicationContext(),Constant.SIMNUMBER,simSerialNumber);
        }
}
