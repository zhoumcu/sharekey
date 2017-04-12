package cn.zhiao.baselib.utils;

import android.util.Log;

/**
 * 作者：wxx on 2016/3/23 19:33
 * <p/>
 * 邮箱：13026620809@163.com
 */
public class L {
    private L()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "wxx";

    // 下面四个是默认tag的函数
    public static void i(String msg)
    {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg)
    {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg)
    {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg)
    {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg)
    {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg)
    {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg)
    {
        if (isDebug)
            Log.i(tag, msg);
    }
    public static int e(String tag, String msg, Throwable tr) {
        if (isDebug) {
            return android.util.Log.e(tag, msg, tr);
        }
        return 0;
    }


    public static void v(String tag, String msg)
    {
        if (isDebug)
            Log.i(tag, msg);
    }
}

