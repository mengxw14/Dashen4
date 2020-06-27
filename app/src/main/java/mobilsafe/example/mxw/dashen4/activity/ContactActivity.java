package mobilsafe.example.mxw.dashen4.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.adapter.PhoneAdapter;
import mobilsafe.example.mxw.dashen4.bean.contacts;
import mobilsafe.example.mxw.dashen4.utils.ReadContactUtils;

public class ContactActivity extends AppCompatActivity {
    private ListView mListView;
    private List<contacts> lists;
    private PhoneAdapter mAdapter;
    private Handler handler = new Handler(){

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();
        initData();
        initEvent();
    }
    private void initView(){
        mListView = (ListView)findViewById(R.id.lv_contact);
    }
    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                lists = ReadContactUtils.readContacts(getApplicationContext());
            }
        }).start();
        mAdapter = new PhoneAdapter(getApplicationContext(),lists);
    }

    private void initEvent(){
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String phone = lists.get(i).getNumber();
                Intent intent = new Intent();
                intent.putExtra("phone",phone);
                setResult(0,intent);
                finish();
            }
        });
        /**
        mListView.setOnClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String phone = lists.get(i).getNumber();
                Intent intent = new Intent();
                intent.putExtra("phone",phone);
                setResult(0,intent);
                finish();
            }
        });
         */
    }

}
