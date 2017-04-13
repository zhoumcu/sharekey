package cn.zhiao.baselib.utils.gridpassword;

/**
 * author：Administrator on 2017/4/13 13:10
 * company: xxxx
 * email：1032324589@qq.com
 */
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author Jungly
 * jungly.ik@gmail.com
 * 15/3/8 10:07
 */
public class Util {

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }

}