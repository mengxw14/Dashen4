package mobilsafe.example.mxw.dashen4.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class LockReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
        for(int i = 0; i < infos.size(); i ++){
            am.killBackgroundProcesses(infos.get(i).processName);
        }
        Toast.makeText(context,"锁频关闭",Toast.LENGTH_SHORT).show();
        Log.d("broad", "广播进行");
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
