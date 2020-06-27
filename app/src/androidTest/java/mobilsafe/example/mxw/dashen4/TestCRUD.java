package mobilsafe.example.mxw.dashen4;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import mobilsafe.example.mxw.dashen4.bean.BlackPeople;
import mobilsafe.example.mxw.dashen4.db.dao.BlackNumberDao;

import static android.content.ContentValues.TAG;

/**
 * Created by MXW on 2020/6/5.
 */

public class TestCRUD extends AndroidTestCase{

    public void testAdd(){
        BlackNumberDao dao = new BlackNumberDao(getContext());
        boolean add = dao.add("18627752172", "0");
        assertEquals(true,add);
    }
    public void testAddLot(){
        BlackNumberDao dao = new BlackNumberDao(getContext());
        for (int i = 0;i < 100;i ++) {
            Random r = new Random();
            int c = r.nextInt(3);
            dao.add("18627752172" + i, String.valueOf(c));
        }
    }

    public void testDelete(){
        BlackNumberDao dao = new BlackNumberDao(getContext());
        boolean delete = dao.delete("18627752172");
        assertEquals(true,delete);
    }

    public void testUpdate(){
        BlackNumberDao dao = new BlackNumberDao(getContext());
        boolean update = dao.update("18627752172", "1");
        assertEquals(true,update);
    }

    public void testFind(){
        BlackNumberDao dao = new BlackNumberDao(getContext());
        String s = dao.find("18627752172");
        assertEquals("1",s);
    }

    public void testFindall(){
        BlackNumberDao dao = new BlackNumberDao(getContext());
        ArrayList<BlackPeople> list = dao.findAll();
        int n = list.size();
        for(int i = 0;i<n;i++){
            String phone = list.get(i).getPhone();
            String mode = list.get(i).getMode();
            Log.d("black", phone + "    :" + mode);
        }
    }
    public void testDeleteAll(){
        BlackNumberDao dao = new BlackNumberDao(getContext());
        ArrayList<BlackPeople> list = dao.findAll();
        int n = list.size();
        for(int i = 0;i<n;i++){
            String phone = list.get(i).getPhone();
            Log.d("已删除", phone);
            dao.delete(phone);
        }
    }
}
