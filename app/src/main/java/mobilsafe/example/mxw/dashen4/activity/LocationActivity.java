package mobilsafe.example.mxw.dashen4.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mobilsafe.example.mxw.dashen4.R;

public class LocationActivity extends AppCompatActivity {
private EditText mEdit;
private Button mButton;
private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initeData();
        initeView();
        initEvent();
    }

    private void initeView() {
        mEdit = findViewById(R.id.lc_number);
        mButton = findViewById(R.id.lc_find);
    }
    private void initeData(){
        db = SQLiteDatabase.openDatabase("/data/data/mobilsafe.example.mxw.dashen4/files/address.db",
                null,SQLiteDatabase.OPEN_READONLY);
    }
    private void initEvent(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mEdit.getText().toString();
                if(phone.length() < 7){
                    Toast.makeText(getApplicationContext(),"请输入完整的号码",Toast.LENGTH_SHORT).show();
                    return;
                }
                String location= "未找到";
                Cursor cursor = db.rawQuery("select location from data2 where id = " +
                        "(select outkey from data1 where id=?)", new String[]{phone.substring(0, 7)});
                if(cursor.moveToFirst()){
                    location = cursor.getString(0);
                }
                Log.d("location name", location);
                Toast.makeText(getApplicationContext(),location,Toast.LENGTH_SHORT).show();
            }
        });
    }
}