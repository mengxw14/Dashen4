package mobilsafe.example.mxw.dashen4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mobilsafe.example.mxw.dashen4.utils.Constant;

import static android.R.attr.key;
import static android.R.id.primary;

/**
 * Created by MXW on 2020/6/5.
 */

public class BlackNumberDb extends SQLiteOpenHelper{
    /**
     *
     * @param context 上下文
     *  name 数据库名称
     *  factory 游标工程,null代表使用默认
     *  version 当前版本 从1开始
     */
    public BlackNumberDb(Context context) {
        super(context, Constant.BLACKTABLE + ".db", null, 1);
    }

    /**
     *  phone 拦截的黑名单号码
     *  mode 拦截模式 0 全部拦截 1 电话拦截 2 短信拦截
     *  _id 主键
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //执行sql语句 创建数据库表和表的字段
        sqLiteDatabase.execSQL("create table " + Constant.BLACKTABLE +"(_id integer primary key " +
                "autoincrement,phone varchar(20),mode varchar(2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
