package mobilsafe.example.mxw.dashen4.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.utils.Start;

public class SurperActivity extends AppCompatActivity {
    private Button mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surper);
        initView();
        initEvent();
    }

    private void initView() {
        mLocation = (Button) findViewById(R.id.location);
    }
    private void initEvent(){
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start.Activity(getApplication(),LocationActivity.class);
            }
        });
    }
}