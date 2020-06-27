package mobilsafe.example.mxw.dashen4.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.adapter.BlackAdapter;
import mobilsafe.example.mxw.dashen4.bean.BlackPeople;
import mobilsafe.example.mxw.dashen4.db.dao.BlackNumberDao;

public class BlackList extends AppCompatActivity {
    private ImageView mDelete;
    private ArrayList<BlackPeople> mList;
    private ListView mListView;
    private BlackNumberDao dao;
    private Button mButton;
    private AlertDialog dialog;
    private BlackAdapter adapter;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            adapter = new BlackAdapter(getApplication(),mList);
            mListView.setAdapter(adapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initeView();
        initeData();
        initeEvent();
    }


    private void initeView(){
        setContentView(R.layout.activity_black_list);
        mDelete = (ImageView) findViewById(R.id.delete_black);
        mListView = (ListView)findViewById(R.id.lv_black);
        mButton = (Button) findViewById(R.id.add_black);
    }
    private void initeData(){
        new Thread(){
            @Override
            public void run() {
                dao = new BlackNumberDao(getApplicationContext());
                Random random = new Random();
                mList = dao.findAll();
                handler.sendEmptyMessage(0);
            }
        }.start();
    }
    private void initeEvent() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BlackList.this);
                View v = View.inflate(getApplicationContext(),R.layout.add_black,null);
                builder.setView(v);
                dialog = builder.show();
                final EditText et_phone = (EditText)v.findViewById(R.id.add_black_number);
                Button btn_ok = (Button)v.findViewById(R.id.st_blackphone);
                Button btn_cancel = (Button)v.findViewById(R.id.st_cancel);
                final RadioGroup radiog = v.findViewById(R.id.rg);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phone = et_phone.getText().toString();
                        String mode = "0";
                        int id = radiog.getCheckedRadioButtonId();
                        switch (id){
                            case R.id.rb_all:
                                mode = "0";
                                break;
                            case R.id.rb_phone:
                                mode = "2";
                                break;
                            case R.id.rb_sms:
                                mode = "1";
                                break;
                            default:
                                break;
                        }
                        boolean add = dao.add(phone, mode);
                        if (add){
                            Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                            BlackPeople a = new BlackPeople(phone,mode);
                            mList.add(0,a);
                            //重新调用getCount和getView方法
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}