package mobilsafe.example.mxw.dashen4.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mobilsafe.example.mxw.dashen4.R;

import mobilsafe.example.mxw.dashen4.bean.ProcessInfo;

public class ProcessAdapter extends BaseAdapter {
    private Context mContext;
    private List<ProcessInfo> mList;
    //private UninstallReceiver uninstallReceiver;

    public ProcessAdapter(Context context, List<ProcessInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        final ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            v = View.inflate(mContext, R.layout.lv_process,null);
            holder.icon = (ImageView)v.findViewById(R.id.am_icon);
            holder.isRom = (TextView)v.findViewById(R.id.am_isRom);
            holder.name = (TextView)v.findViewById(R.id.am_name);
            holder.packname = (TextView)v.findViewById(R.id.am_packname);
            holder.size = (TextView)v.findViewById(R.id.am_size);
            holder.check = (CheckBox)v.findViewById(R.id.pm_check);
            v.setTag(holder);
        }else {
            v = view;
            holder = (ViewHolder) v.getTag();
        }
        ProcessInfo info = mList.get(i);
        holder.icon.setImageDrawable(info.getIcon());
        holder.size.setText(Formatter.formatFileSize(mContext,info.getSize()));
        holder.packname.setText(info.getPackName());
        holder.name.setText(info.getName());
        holder.isRom.setText(RomOrSD(info.isUserProcess()));
        holder.check.setChecked(info.isChecked());


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
            return "系统进程";
        }else {
            return "用户进程";
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
        int i;
        CheckBox check;
    }
}
