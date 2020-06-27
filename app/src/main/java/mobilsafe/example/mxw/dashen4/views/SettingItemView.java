package mobilsafe.example.mxw.dashen4.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobilsafe.example.mxw.dashen4.R;

/**
 * Created by MXW on 2020/5/30.
 */

public class SettingItemView extends RelativeLayout{
    private TextView mText;
    private ImageView mImg;
    private RelativeLayout mSettingToggle;
    public SettingItemView(Context context, AttributeSet attrs) {
        super(context,attrs);
        initView();
        initData(attrs);
    }

    //调用当前的方法设置不同的背景
    public void setToggle(boolean isOpean){
        if(isOpean){
            mImg.setImageResource(R.drawable.on);
        }else
            mImg.setImageResource(R.drawable.off);
    }

    private void initView(){
        View view = View.inflate(getContext(), R.layout.view_item,this);
        mText = findViewById(R.id.setting_text);
        mImg = findViewById(R.id.setting_img);
        mSettingToggle = findViewById(R.id.setting_toggle);
    }

    /**
     *
     * @param attrs 当前控件所有属性集合类
     */
    private void initData(AttributeSet attrs){
        String item = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","desc");
        String position = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","position");
        switch (position){
            case "first":
                mSettingToggle.setBackgroundResource(R.drawable.iv_first_selector);
                break;
            case "middle":
                mSettingToggle.setBackgroundResource(R.drawable.iv_middle_selector);
                break;
            case "last":
                mSettingToggle.setBackgroundResource(R.drawable.iv_last_selector);
                break;
            default:
                break;
        }
        mText.setText(item);
        mImg.setImageResource(R.drawable.off);
    }

}
