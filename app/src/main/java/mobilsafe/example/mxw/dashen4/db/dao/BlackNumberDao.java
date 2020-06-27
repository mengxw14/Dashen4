package mobilsafe.example.mxw.dashen4.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import mobilsafe.example.mxw.dashen4.bean.BlackPeople;
import mobilsafe.example.mxw.dashen4.db.BlackNumberDb;
import mobilsafe.example.mxw.dashen4.utils.Constant;

import static android.R.attr.data;
import static android.R.attr.mode;
import static mobilsafe.example.mxw.dashen4.R.drawable.d;

/**
 * Created by MXW on 2020/6/5.
 * 黑名单数据库的CRUD
 */

public class BlackNumberDao {
    private BlackNumberDb db;

    public BlackNumberDao(Context context){
        db = new BlackNumberDb(context);
    }

    /**
     *
     * @param phone 黑名单号码
     * @param mode 拦截模式
     */
    public boolean add(String phone,String mode){
        //1.获得可写的数据库
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone",phone);
        values.put("mode",mode);
        long insert = database.insert(Constant.BLACKTABLE, null, values);
        db.close();
        if (insert != -1){
            return true;
        }else {
            return false;
        }
    }
    
    public boolean delete(String phone){
        SQLiteDatabase database = db.getWritableDatabase();
        int delete = database.delete(Constant.BLACKTABLE,"phone=?",new String[]{phone});
        database.close();
        return delete==0?false:true;
    }

    public boolean update(String phone,String mode){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mode",mode);
        int update = database.update(Constant.BLACKTABLE, values, "phone=?", new String[]{phone});
        database.close();
        return update==0?false:true;
    }

    /**
     * 查找的操作
     * @param phone
     * @return
     */
    public String find(String phone){
        String mode = null;
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(Constant.BLACKTABLE, new String[]{"mode"}, "phone = ?",
                new String[]{phone}, null, null, null);
        while (cursor.moveToNext()){
            mode = cursor.getString(0);
        }
        return mode;
    }
    
    public ArrayList<BlackPeople> findAll(){
        ArrayList<BlackPeople> list = new ArrayList<>();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(Constant.BLACKTABLE, new String[]{"phone", "mode"},
                null, null, null, null, null);
        while ((cursor.moveToNext())){
            String phone = cursor.getString(0);
            String mode = cursor.getString(1);
            BlackPeople A = new BlackPeople(phone,mode);
            list.add(A);
        }
        cursor.close();
        return  list;
    }
}
