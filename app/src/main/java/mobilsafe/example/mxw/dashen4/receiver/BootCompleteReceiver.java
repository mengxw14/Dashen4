package mobilsafe.example.mxw.dashen4.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import mobilsafe.example.mxw.dashen4.utils.Constant;
import mobilsafe.example.mxw.dashen4.utils.SPUtils;

import static mobilsafe.example.mxw.dashen4.utils.SPUtils.getString;

//监听手机重启的广播,手机重启后,广播被激活

public class BootCompleteReceiver extends BroadcastReceiver {
    public BootCompleteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //1.检查sim卡是否变化
        boolean need = SPUtils.getBoolean(context,Constant.ISOPEAN);
        if(need){
            String oldSim = getString(context, Constant.SIMNUMBER);
            TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELECOM_SERVICE);
            String newsim = tm.getSimSerialNumber();
            if(oldSim.equals(newsim)){
                //没有改变
                return;
            }else {
                //改变了
                //发送短信给安全号码
                String number = SPUtils.getString(context,Constant.SAFENUMBER);
                SmsManager.getDefault().sendTextMessage(number,null,"sim change",null,null);
            }
        }
        else {
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
