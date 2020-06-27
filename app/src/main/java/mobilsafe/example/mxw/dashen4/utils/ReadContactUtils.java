package mobilsafe.example.mxw.dashen4.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mobilsafe.example.mxw.dashen4.bean.contacts;

import static android.content.ContentValues.TAG;
import static mobilsafe.example.mxw.dashen4.R.drawable.c;

/**
 * Created by MXW on 2020/6/2.
 */

public class ReadContactUtils {
    public static List<contacts> readContacts(Context context){
        List<contacts> lists = new ArrayList<>();
        //读取系统数据库
        //1.先获得解析器
        ContentResolver resolver = context.getContentResolver();
        //2 获得查询的uri
        Uri dataUri = Uri.parse("content://com.android.contacts/data");
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Cursor cursor = resolver.query(uri, new String[]{"_id"}, null, null, null);
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            if(TextUtils.isEmpty(id)){
                contacts info = new contacts();
                Cursor query = resolver.query(dataUri, new String[]{"mimetype", "data1"}, "raw_contact_id=?"
                        , new String[]{id}, null, null);
                while(query.moveToNext()){
                    String mimetype = query.getString(0);
                    String data1 = query.getString(1);
                    if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
                        info.setNumber(data1);
                    }else if("vnd.android.cursor.item/name".equals(mimetype)){
                        info.setName(data1);
                    }
                    lists.add(info);
                }
            }
        }
        Log.d("list", lists.toString());
        return lists;
    }
}
