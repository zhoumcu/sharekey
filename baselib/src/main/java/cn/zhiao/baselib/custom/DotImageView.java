package cn.zhiao.baselib.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by oujie on 2017/2/20.
 */

public class DotImageView extends ImageView {


    private int dot = 0;

    public DotImageView(Context context) {
        super(context);
    }

    public DotImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DotImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ( dot ==1){

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#f9abca"));

            canvas.drawCircle(32,43,8,paint);

        }


    }
    public  void setDot(int yes){

        dot = yes;

    }
}
