package mobilsafe.example.mxw.dashen4.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.utils.Constant;
import mobilsafe.example.mxw.dashen4.utils.SPUtils;
import mobilsafe.example.mxw.dashen4.utils.Start;

public class Setup4Activity extends AppCompatActivity {

    private ImageView mPre;
    private ImageView mNext;
    private CheckBox mBox;
    private TextView mText;
    private boolean mIsopean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initView(){
        setContentView(R.layout.activity_setup4);
        mPre = (ImageView) findViewById(R.id.img_pre);
        mNext = (ImageView)findViewById(R.id.img_next);
        mBox = (CheckBox)findViewById(R.id.open);
        mText = (TextView)findViewById(R.id.text_isopen);

    }

    private void initData(){
        mIsopean = SPUtils.getBoolean(getApplication(), Constant.ISOPEAN);
        if(mIsopean){
            mBox.setChecked(true);
            mText.setText("已开启手机防盗");
        }else {
            mBox.setChecked(false);
            mText.setText("未开启手机防盗");
        }
    }

    private void initEvent(){
        mPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start.Activity(getApplicationContext(),Setup3Activity.class);
                overridePendingTransition(R.anim.pre_in, R.anim.pre_out);
                finish();
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBox.isChecked()) {
                    SPUtils.putBoolean(getApplication(),Constant.ISOPEAN,mIsopean);
                    Start.Activity(getApplicationContext(), MainActivity.class);
                    overridePendingTransition(R.anim.pre_in, R.anim.pre_out);
                    finish();
                }else {
                    //没选中 要求选中
                    Toast.makeText(getApplication(),"请开启防盗",Toast.LENGTH_SHORT);
                }
            }
        });
        mBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBox.isChecked()){
                    mBox.setChecked(true);
                    mText.setText("已开启手机防盗");
                }else {
                    mBox.setChecked(false);
                    mText.setText("未开启手机防盗");
                }
                mIsopean = !mIsopean;
            }
        });
    }
}
