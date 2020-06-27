package mobilsafe.example.mxw.location;

import android.test.AndroidTestCase;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import static android.content.ContentValues.TAG;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by MXW on 2020/6/4.
 */

public class TestLocation extends AndroidTestCase{
    public void testlocation() throws Exception {
        InputStream open = getContext().getAssets().open("axisoffset.dat");
        ModifyOffset mo = ModifyOffset.getInstance(open);
        PointDouble pt = new PointDouble(100,65);
        PointDouble newPoint = mo.s2c(pt);
        double x = newPoint.x;
        double y = newPoint.y;
        System.out.println("x = " + x + "y = " + y);
        Log.d("androidtext", "x = "+ x + "y = " + y);
    }
}
