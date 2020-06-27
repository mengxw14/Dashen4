package mobilsafe.example.mxw.dashen4.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.bean.AppInfo;

public class AppManagerAdapter extends BaseAdapter {

    private Context mContext;
    private List<AppInfo> mList;
    //private UninstallReceiver uninstallReceiver;
    private int iii;

    public AppManagerAdapter(Context context, List<AppInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        iii = i;
        View v;
        final ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            v = View.inflate(mContext, R.layout.lv_app,null);
            holder.icon = (ImageView)v.findViewById(R.id.am_icon);
            holder.isRom = (TextView)v.findViewById(R.id.am_isRom);
            holder.name = (TextView)v.findViewById(R.id.am_name);
            holder.packname = (TextView)v.findViewById(R.id.am_packname);
            holder.size = (TextView)v.findViewById(R.id.am_size);
            holder.button = (Button)v.findViewById(R.id.am_delete);
            v.setTag(holder);
        }else {
            v = view;
            holder = (ViewHolder) v.getTag();
        }
        AppInfo info = mList.get(i);
        holder.icon.setImageDrawable(info.getIcon());
        holder.size.setText(String.valueOf(info.getSize()));
        holder.packname.setText(info.getPackname());
        holder.name.setText(info.getName());
        holder.isRom.setText(RomOrSD(info.isRom()));
        holder.i = info.getId();
        holder.button.setOnClickListener(new View.OnClickListener() {
            //卸载
            @Override
            public void onClick(View view) {
                uninstallApp(holder.packname.getText().toString());
            }
        });
        return v;
    }

    //卸载App的方法
    private void uninstallApp(String s) {
        //卸载
        Intent intent = new Intent();
        intent.setAction("android.intent.action.View");
        intent.setAction("android.intent.action.DELETE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:" + s));
        //监听是否有app被卸载
        mContext.startActivity(intent);
    }

    private String RomOrSD(boolean rom){
        if (rom){
            return "安装位置：ROM";
        }else {
            return "安装位置：SD卡";
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    class ViewHolder{
        TextView name;//软件名称
        TextView packname;//软件的包名
        TextView size;
        TextView isRom;//是否安装在手机内存里面
        ImageView icon;// 软件的图片
        Button button;
        int i;
    }
    /**class UninstallReceiver extends BroadcastReceiver {

        //一旦有应用被卸载 这个方法就会调用
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
            mList.remove(iii);
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }
        */



}
