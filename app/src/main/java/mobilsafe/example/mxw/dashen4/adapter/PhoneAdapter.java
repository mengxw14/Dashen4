package mobilsafe.example.mxw.dashen4.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.bean.contacts;

import static android.content.ContentValues.TAG;

/**
 * Created by MXW on 2020/6/2.
 */

public class PhoneAdapter extends BaseAdapter {
    private List<contacts> mList;
    private Context context;
    public PhoneAdapter(Context c,List<contacts> list) {
        mList = list;
        context = c;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = null;
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            v = View.inflate(context, R.layout.item_contact_view,viewGroup);
            viewHolder.tv_name = (TextView)v.findViewById(R.id.tv_name);
            viewHolder.tv_phone = (TextView)v.findViewById(R.id.tv_phone);
            view.setTag(viewHolder);
        }else {
            v = view;
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.tv_name.setText(mList.get(i).getName());
        viewHolder.tv_phone.setText(mList.get(i).getNumber());
        return v;
    }
    @Override
    public int getCount() {
        Log.d("list", mList.toString());
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder{
        TextView tv_name;
        TextView tv_phone;
    }


}
