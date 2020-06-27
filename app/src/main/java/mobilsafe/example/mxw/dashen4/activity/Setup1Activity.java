package mobilsafe.example.mxw.dashen4.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.utils.Start;

public class Setup1Activity extends AppCompatActivity {
    private ImageView mPre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initView(){
        setContentView(R.layout.activity_setup1);
        mPre = (ImageView) findViewById(R.id.img_next);
    }

    private void initData(){

    }

    private void initEvent(){
        mPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start.Activity(getApplicationContext(),Setup2Activity.class);
                //覆盖默认的activity跳转动画
                overridePendingTransition(R.anim.next_in,R.anim.next_out);
                finish();
            }
        });
    }

}
