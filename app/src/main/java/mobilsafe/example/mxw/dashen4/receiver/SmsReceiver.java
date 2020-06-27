package mobilsafe.example.mxw.dashen4.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.service.LocationService;

import static mobilsafe.example.mxw.dashen4.R.drawable.a;
import static mobilsafe.example.mxw.dashen4.R.drawable.b;

public class SmsReceiver extends BroadcastReceiver {
    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");//短信的国标
        for (Object object: pdus) {
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
            //获取短信文本
            String body = sms.getDisplayMessageBody();
            if("#*location*#".equals(body)){
                //获取当前的经纬度
                Intent intentService = new Intent(context, LocationService.class);
                context.startService(intentService);
                //屏蔽短信 不让小偷看到
                abortBroadcast();
            }else if("#*alarm*#".equals(body)){
                //需要发警报
                MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
                player.setVolume(1.0f,1.0f);
                player.setLooping(true);
                player.start();
                abortBroadcast();

            }else if ("#*wipedate*#".equals(body)){
                //抹除数据
            }else if ("#*lockscreen*#".equals(body)){
                //远程锁频
            }
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
