package mobilsafe.example.mxw.dashen4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.bean.Title;

/**
 * Created by MXW on 2020/5/29.
 */

public class titleAdapter extends BaseAdapter {
    private Context mContext;
    private List<Title> mList;

    public titleAdapter(Context mContext, ArrayList<Title> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        Title t = mList.get(i);
        if(view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_view,viewGroup,false);
            holder = new ViewHolder();
            holder.img = view.findViewById(R.id.grid_img);
            holder.mTitle1 = view.findViewById(R.id.title_1);
            holder.mTitle2 = view.findViewById(R.id.title_2);
            view.setTag(holder);
        }else
        holder = (ViewHolder) view.getTag();
        holder.img.setImageResource(t.getImgSrc());
        holder.mTitle1.setText(t.getText1());
        holder.mTitle2.setText(t.getText2());
        return view;
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
        ImageView img;
        TextView mTitle1;
        TextView mTitle2;
    }

}
