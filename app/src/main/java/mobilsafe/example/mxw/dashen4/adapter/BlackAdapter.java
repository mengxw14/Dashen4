package mobilsafe.example.mxw.dashen4.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import mobilsafe.example.mxw.dashen4.R;
import mobilsafe.example.mxw.dashen4.bean.BlackPeople;
import mobilsafe.example.mxw.dashen4.db.dao.BlackNumberDao;

import static mobilsafe.example.mxw.dashen4.R.drawable.b;

/**
 * Created by MXW on 2020/6/5.
 */

public class BlackAdapter extends BaseAdapter {
    private Context c;
    private List<BlackPeople> mList;
    public BlackAdapter(Context c,List<BlackPeople> list) {
        this.c = c;
        mList = list;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        View v = null;
        if(view == null){
            holder = new ViewHolder();
            v = View.inflate(c,R.layout.lv_black,null);
            holder.phone = v.findViewById(R.id.lv_black_number);
            holder.mode = v.findViewById(R.id.lv_black_mode);
            holder.delete = v.findViewById(R.id.delete_black);
            v.setTag(holder);
        }else {
            v = view;
            holder = (ViewHolder) v.getTag();
        }
        holder.phone.setText(mList.get(i).getPhone());
        String mode = getMode(mList.get(i).getMode());
        holder.mode.setText(mode);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlackNumberDao dao = new BlackNumberDao(c);
                boolean delete = dao.delete(mList.get(i).getPhone());
                if (delete){
                    mList.remove(i);
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(c,"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    private String getMode(String mode) {
        String m = null;
        switch (mode){
            case "0":
                m = "拦截短信和电话";
                break;
            case "1":
                m = "拦截短信";
                break;
            case "2":
                m = "拦截电话";
                break;
            default:
                break;
        }
        return m;
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
        TextView phone;
        TextView mode;
        ImageView delete;
    }
}
