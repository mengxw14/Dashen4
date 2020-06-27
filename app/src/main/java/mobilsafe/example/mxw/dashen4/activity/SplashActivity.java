package mobilsafe.example.mxw.dashen4.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.utils.Constant;
import mobilsafe.example.mxw.dashen4.utils.SPUtils;
import mobilsafe.example.mxw.dashen4.utils.Start;
import mobilsafe.example.mxw.dashen4.utils.SystemInfoUtils;

import static android.R.attr.animation;

public class SplashActivity extends AppCompatActivity {
    private RelativeLayout mSplash;
    private TextView version;
    private TextView mVersionName;
    private AnimationSet mAnimationSet;
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
        setContentView(R.layout.activity_splash);
        mSplash = (RelativeLayout)findViewById(R.id.splash);
        version = (TextView)findViewById(R.id.version);
        mVersionName = (TextView)findViewById(R.id.text_1);
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
    private void initAnimation() {
        /**0 开始旋转角度
         * 360 结束角度
         * Animation.RELATIVE_TO_SELF 锚点,相对于自己的某个位置固定
         * 0.5f 在X轴方向说明锚点相对于自己宽带的宽度的一半去选点
         */
        //旋转动画
        RotateAnimation ra = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        //设置动画时间
        ra.setDuration(2000);
        //补间动画?
        ra.setFillAfter(true);

        /**0.0f 在X/Y轴方向从0放大到1
         *
         */
        //比例动画
        ScaleAnimation sa = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        //设置动画时间
        sa.setDuration(2000);
        //补间动画?
        sa.setFillAfter(true);

        /**
         * 0.0f 完全透明 1.0f 全不透
         */
        //透明度渐变动画
        AlphaAnimation aa = new AlphaAnimation(0.0f,1.0f);
        aa.setDuration(2000);
        //补间动画?
        aa.setFillAfter(true);

        /**
         * false 不共用动画插入器
         */
        //动画容器,用来装载所有的动画,然后统一运行
        mAnimationSet = new AnimationSet(false);
        mAnimationSet.addAnimation(ra);
        mAnimationSet.addAnimation(sa);
        mAnimationSet.addAnimation(aa);
        mSplash.startAnimation(mAnimationSet);
    }
    private void initData() {
        try {
            version.setText("版本号:" + SystemInfoUtils.getVersionCode(this));
            mVersionName.setText("孟同学调试:" + SystemInfoUtils.getVersionName(this));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        copyDb("address.db");
    }
    private void copyDb(final String name){
        new Thread(){
            public void run(){
                File file = new File(getFilesDir(),name);
                if(file.exists() && file.length() > 0){
                    //已经加载过数据库 无需再加载
                }else {
                    //1.创建一个输入流
                    InputStream in = null;
                    try {
                        in = getAssets().open(name);
                        FileOutputStream fos = new FileOutputStream(file);
                        int len = 0;
                        byte[] buffer = new byte[1024];
                        while((len = in.read(buffer)) != -1){
                            fos.write(buffer,0,len);
                        }
                    }catch (Exception e){

                    }
                }
            }
        }.start();
    }
    private void initEvent() {
        //动画执行的监听
        mAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            //动画启动
            @Override
            public void onAnimationStart(Animation animation) {

            }

            //动画做完后的回调
            @Override
            public void onAnimationEnd(Animation animation) {
                //进入主界面
                Start.Activity(getApplicationContext(),MainActivity.class);
                finish();
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void checkVersion(){
        //在子线程中检查是否需要更新
        new Thread(){
            public void run(){
                //url
                //服务器返回一个json数据
                //保存解析的值
                //对比版本号
            }
        }.start();
    }
}
