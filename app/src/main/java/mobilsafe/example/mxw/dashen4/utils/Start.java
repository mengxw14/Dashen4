package mobilsafe.example.mxw.dashen4.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import static mobilsafe.example.mxw.dashen4.R.drawable.b;
import static mobilsafe.example.mxw.dashen4.R.drawable.c;

/**
 * Created by MXW on 2020/5/29.
 */

public class Start {
    public static void Activity(Context c,Class<?> clz){
        Intent intent = new Intent(c,clz);
        c.startActivity(intent);
    }
}
