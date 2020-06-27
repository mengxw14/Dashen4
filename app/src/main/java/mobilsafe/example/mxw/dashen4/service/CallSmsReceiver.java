package mobilsafe.example.mxw.dashen4.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

import mobilsafe.example.mxw.dashen4.db.dao.BlackNumberDao;

import static mobilsafe.example.mxw.dashen4.R.drawable.b;

public class CallSmsReceiver extends Service {
    private InnerSmsReceiver receiver;
    private BlackNumberDao dao;
    private TelephonyManager tm;
    private MyListener listener;
    public CallSmsReceiver() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        dao = new BlackNumberDao(this);
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new MyListener();
        //监听电话打进来的状态
        tm.listen(listener,PhoneStateListener.LISTEN_CALL_STATE);
        /**
         * 在代码中动态注册广播
         */
        receiver = new InnerSmsReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver ,filter);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        tm.listen(listener,PhoneStateListener.LISTEN_NONE);
        tm = null;
        listener = null;
        super.onDestroy();
    }

    class InnerSmsReceiver extends BroadcastReceiver{

        //一旦有短信来 这个方法就会被调用
        @Override
        public void onReceive(Context context, Intent intent) {
            /**
             * 获得短信内容步骤如下
             */
            //1.通过pdus的规范获得短信的对象数组
            Object[] objs = (Object[]) intent.getExtras().get("pdus");
            //2.遍历pdus集合
            for(Object obj :objs){
                //3.把pdus转化为一个短信对象
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                //获得发送短信的号码
                String phone = sms.getOriginatingAddress();
                //根据号码查询是否拦截
                String mode = dao.find(phone);
                if("0".equals(mode) || "2".equals(mode)){
                    abortBroadcast();
                }
            }
        }
    }

    class MyListener extends PhoneStateListener{
        //电话的三种状态
        @Override
        public void onCallStateChanged(int state, final String incomingNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE://空闲状态

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://接通状态

                    break;
                case TelephonyManager.CALL_STATE_RINGING://响铃状态
                    //响铃的时候获得电话号码,查询是否需要挂断
                    String mode = dao.find(incomingNumber);
                    if("0".equals(incomingNumber)||"1".equals(incomingNumber)){
                        //拦截号码
                        endCall();
                        Uri uri = Uri.parse("content://call_log/calls");
                        getContentResolver().registerContentObserver(uri, true, new ContentObserver(new Handler()) {
                            //当我观察的数据库发生了改变
                            @Override
                            public void onChange(boolean selfChange) {
                                deleteCallLog(incomingNumber);
                                super.onChange(selfChange);
                            }
                        });
                    }
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    //删除通话记录
    private void deleteCallLog(String phone) {
        //操作别人的数据库之前 先获得内容解析器
        ContentResolver resolver = getContentResolver();
        //获得操作路径 在源码中查找
        Uri uri = Uri.parse("content://call_log/calls");
        resolver.delete(uri,"number=?",new String[]{phone});
    }

    //挂断电话
    private void endCall(){
        try {
            //获得字节码
            Class<?> clazz = CallSmsReceiver.class.getClassLoader().loadClass("android.os.Service");
            //获得方法
            Method method = clazz.getDeclaredMethod("getService", String.class);
            //调用方法
            IBinder b = (IBinder) method.invoke(null, TELEPHONY_SERVICE);
            ITelephony iTelephony = ITelephony.Stub.asInterface(b);
            iTelephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
