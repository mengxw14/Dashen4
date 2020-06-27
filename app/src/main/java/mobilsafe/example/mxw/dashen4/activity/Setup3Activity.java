package mobilsafe.example.mxw.dashen4.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.utils.Constant;
import mobilsafe.example.mxw.dashen4.utils.SPUtils;
import mobilsafe.example.mxw.dashen4.utils.Start;

import static mobilsafe.example.mxw.dashen4.R.drawable.a;
import static mobilsafe.example.mxw.dashen4.R.drawable.g;

public class Setup3Activity extends AppCompatActivity {

    private ImageView mPre;
    private ImageView mNext;
    private Button mBinding;
    private EditText mInputnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initView(){
        setContentView(R.layout.activity_setup3);
        mPre = (ImageView) findViewById(R.id.img_pre);
        mNext = (ImageView)findViewById(R.id.img_next);
        mBinding = (Button)findViewById(R.id.binding);
        mInputnumber = (EditText)findViewById(R.id.set_safenumber);
    }

    private void initData(){
        String safenumber = SPUtils.getSp(getApplicationContext()).getString(Constant.SAFENUMBER,null);
        mInputnumber.setText(safenumber);
    }

    private void initEvent(){
        mPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start.Activity(getApplicationContext(),Setup2Activity.class);
                overridePendingTransition(R.anim.pre_in, R.anim.pre_out);
                finish();
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.putString(getApplicationContext(),Constant.SAFENUMBER,mInputnumber.getText().toString());
                overridePendingTransition(R.anim.next_in, R.anim.next_out);
                Start.Activity(getApplicationContext(),Setup4Activity.class);
                finish();
            }
        });
        mBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //开启一个新的界面,从电话簿中选择联系人电话
                Intent intent = new Intent(Setup3Activity.this,ContactActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null){
            String phone = data.getStringExtra("phone");
            mInputnumber.setText(phone);
        }
    }
}
