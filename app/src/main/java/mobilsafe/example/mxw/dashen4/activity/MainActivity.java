package mobilsafe.example.mxw.dashen4.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.adapter.titleAdapter;
import mobilsafe.example.mxw.dashen4.bean.Title;
import mobilsafe.example.mxw.dashen4.utils.Constant;
import mobilsafe.example.mxw.dashen4.utils.SPUtils;
import mobilsafe.example.mxw.dashen4.utils.Start;

import static mobilsafe.example.mxw.dashen4.R.drawable.e;
import static mobilsafe.example.mxw.dashen4.R.id.launcher;

public class MainActivity extends AppCompatActivity {
    private ImageView mSetting;
    private GridView mGridView;
    private ImageView mLauncher;
    private BaseAdapter mAdapter;
    private ArrayList<Title> mTitleList;
    private Button stPswword;
    private Button canclePswword;
    private AlertDialog dialog;
    private EditText sePassword;
    private EditText suPassword;
    private EditText pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //初始化界面
        initView();
        //初始化动画
        initAnimation();
        //初始化数据
        initData();
        //初始化时间
        initEvent();
    }
    private void initView() {
        setContentView(R.layout.activity_main);
        mLauncher = (ImageView)findViewById(R.id.launcher);
        mSetting = (ImageView)findViewById(R.id.setting);
        mGridView = (GridView)findViewById(R.id.gv);

        //沉浸式
        setStyle();
    }
    private void setStyle(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    private void initAnimation(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLauncher,"rotationY",
                0,60,120,180,240,300,360);
        objectAnimator.start();
        /**
         * 动画0.5s循环一次
         * 无限循环
         */
        objectAnimator.setDuration(3000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
    }
    private void initData(){
        mTitleList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] title1 = getResources().getStringArray(R.array.text1);
                String[] title2 = getResources().getStringArray(R.array.text2);
                int[] Img = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,
                        e,R.drawable.f,R.drawable.g,R.drawable.h};
                for(int i = 0;i < Img.length;i++){
                    Title t = new Title(Img[i],title1[i],title2[i]);
                    Log.d("Listadd", t.toString());
                    mTitleList.add(t);
                }
            }
        }).start();

    }
    private void initEvent(){
        setAdapter();
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start.Activity(MainActivity.this,SettingActivity.class);
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        //手机防盗
                        String password = SPUtils.getString(getApplicationContext(), Constant.PASSWORD);
                        if(TextUtils.isEmpty(password)){
                            //没有设置过密码,应该弹出一个设置密码的对话框
                            setPassword();
                        }else {
                            //设置过密码
                            showEntryPassworDialog();
                        }
                        break;
                    case 1://黑名单列表
                        Start.Activity(getApplication(),BlackList.class);
                        break;
                    case 2://软件管理
                        Start.Activity(getApplication(),AppManagerActivity.class);
                        break;
                    case 3://进程管理
                        Start.Activity(getApplication(),ProgressManager.class);
                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:
                        //号码归属地查询等高级功能
                        Start.Activity(getApplication(),SurperActivity.class);
                        break;
                }
            }
        });

    }

    //第一个点击时的输入密码功能
    private void setPassword(){
        //设置密码对话框的方法
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = View.inflate(MainActivity.this,R.layout.set_password,null);
        stPswword =  view.findViewById(R.id.st_password);
        canclePswword = view.findViewById(R.id.se_cancel);
        builder.setView(view);
        dialog = builder.show();
        sePassword =  view.findViewById(R.id.en_password);
        suPassword =  view.findViewById(R.id.sure_password);

        //确定事件
        stPswword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fPassword = sePassword.getText().toString();
                String sPassword = suPassword.getText().toString();
                if(!fPassword.equals(sPassword) || TextUtils.isEmpty(fPassword)
                        || TextUtils.isEmpty(sPassword)){
                    Toast.makeText(getApplicationContext(),
                            "请按要求输入",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    //两次密码输入一致
                    SharedPreferences sp = SPUtils.getSp(getApplicationContext());
                    sp.edit().putString(Constant.PASSWORD,fPassword).commit();
                    Toast.makeText(getApplicationContext(),"密码设置完成",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            }
        });
        //取消操作 关闭当前对话框
        canclePswword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
        }});
    }
    private void showEntryPassworDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View v = View.inflate(MainActivity.this,R.layout.entry_password,null);
        builder.setView(v);
        Button ok = v.findViewById(R.id.check_password);
        Button ca = v.findViewById(R.id.cancel_password);
        final EditText pa =  v.findViewById(R.id.entry_password);
        dialog = builder.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pass = SPUtils.getSp(getApplicationContext()).
                        getString(Constant.PASSWORD,null);
                Log.d("password", pass);
                String exitPassword = pa.getText().toString();
                Log.d("password", exitPassword);
                if(pass.equals(exitPassword)){
                    //密码输入正确,进入对应界面
                    Toast.makeText(getApplicationContext(),"输入正确",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Start.Activity(MainActivity.this,Setup1Activity.class);
                }else {
                    //密码输入错误
                    Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    private void setAdapter(){
        mAdapter = new titleAdapter(getApplicationContext(),mTitleList);
        mGridView.setAdapter(mAdapter);
    }
}
