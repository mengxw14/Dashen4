package mobilsafe.example.mxw.dashen4.activity;

import android.app.ActivityManager;
import android.net.TrafficStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mobilsafe.example.mxw.dashen4.R;

public class TrafficActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView(){
        setContentView(R.layout.activity_traffic);
    }
}