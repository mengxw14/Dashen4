package mobilsafe.example.mxw.dashen4.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

import mobilsafe.example.mxw.dashen4.utils.Constant;
import mobilsafe.example.mxw.dashen4.utils.SPUtils;

public class LocationService extends Service {
    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        //设置精度
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置功率
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        //得到经纬度
        String bestProvider = lm.getBestProvider(criteria, true);
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
            @Override
            public void onLocationChanged(Location location) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                String text = "w = " + latitude + "j = " + longitude;
                String phone = SPUtils.getString(getApplication(), Constant.SAFENUMBER);
                SmsManager.getDefault().sendTextMessage(phone,null,text,null,null);
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
