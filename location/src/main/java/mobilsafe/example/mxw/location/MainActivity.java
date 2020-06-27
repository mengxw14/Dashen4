package mobilsafe.example.mxw.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //定位第一步 获取定位管理者
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        //返回所有定位的名称
        List<String> allProviders = lm.getAllProviders();
        //定位方式 1.passive 2.gps
        /**
         for (String provider : allProviders) {

         }
         */
        Criteria critrtia = new Criteria();
        //定位的精度,精度越高,越准确,但耗费电QvQ
        critrtia.setAccuracy(Criteria.ACCURACY_FINE);
        //设置高功率
        critrtia.setPowerRequirement(Criteria.ACCURACY_HIGH);

        String bestProvider = lm.getBestProvider(critrtia, true);

        //通过最好的定位方式获取经纬度
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(bestProvider, 0, 0, new LocationListener() {

            //位置改变之后的回调方法
            @Override
            public void onLocationChanged(Location location) {
                //经度
                double longitude = location.getLongitude();
                //纬度
                double latitude = location.getLatitude();
                TextView tv = new TextView(getApplicationContext());
                tv.setText("经度=" + location + "纬度=" + latitude);
                tv.setTextColor(Color.RED);
                setContentView(tv);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
    }
}
