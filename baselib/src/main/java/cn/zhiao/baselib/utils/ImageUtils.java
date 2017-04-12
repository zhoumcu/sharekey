package cn.zhiao.baselib.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Date;

/**
 * 图片加载工具类
 */
public class ImageUtils {
    /**
     * 加载远程图片
     *
     * @param url       图片路径
     * @param imageView 要加载的控件
     */
    public static void loadImage(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView);
    }

    /**
     * 加载远程图片
     * @param loadingRes    加载中显示图片
     * @param url       图片路径
     * @param imageView 要加载的控件
     */
    public static void loadImage(int loadingRes, String url, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // 加载图片时会在内存中加载缓存
                .cacheOnDisk(true) // 加载图片时会在磁盘中加载缓存
                .showImageOnLoading(loadingRes)
                .showImageOnFail(loadingRes)
                .showImageForEmptyUri(loadingRes)
                .build();

        ImageLoader.getInstance().displayImage(url, imageView, options);
    }
    // 为图片target添加水印文字
    // Bitmap target：被添加水印的图片
    // String mark：水印文章
    public static Bitmap createWatermark(Bitmap target, String mark) {
        int w = target.getWidth();
        int h = target.getHeight();
        Paint pFont = new Paint();
        Rect rect = new Rect();
        //返回包围整个字符串的最小的一个Rect区域
        pFont.getTextBounds(mark, 0, mark.length(), rect);
        int height = rect.height();
        int width = rect.width();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint();
        // 水印的颜色
        p.setColor(Color.RED);
        // 水印的字体大小
        p.setTextSize(20);
        p.setAntiAlias(true);// 去锯齿
        canvas.drawBitmap(target, 0, 0, p);
        // 在左边的中间位置开始添加水印
        canvas.drawText(mark, w-width*2, 20, p);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        FileUtil.getInstance().saveMyBitmap(bmp,"");
        return bmp;
    }
    // 为图片target添加水印文字
    // Bitmap target：被添加水印的图片
    // String mark：水印文章
    public static String createAndSaveWatermark(Bitmap target, String mark) {
        return FileUtil.getInstance().saveBitmap(createWatermark(target,mark),"Desk"+System.currentTimeMillis());
    }
    public static boolean addWatermarkBitmap(Bitmap bitmap, String str, int w, int h) {
        int destWidth = w;   //此处的bitmap已经限定好宽高
        int destHeight = h;
        Log.v("tag","width = " + destWidth+" height = "+destHeight);

        Bitmap icon = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888); //定好宽高的全彩bitmap
        Canvas canvas = new Canvas(icon);//初始化画布绘制的图像到icon上

        Paint photoPaint = new Paint(); //建立画笔
        photoPaint.setDither(true); //获取跟清晰的图像采样
        photoPaint.setFilterBitmap(true);//过滤一些

        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());//创建一个指定的新矩形的坐标
        Rect dst = new Rect(0, 0, destWidth, destHeight);//创建一个指定的新矩形的坐标
        canvas.drawBitmap(bitmap, src, dst, photoPaint);//将photo 缩放或则扩大到 dst使用的填充区photoPaint

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);//设置画笔
        textPaint.setTextSize(destWidth/20);//字体大小
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);//采用默认的宽度
        textPaint.setAntiAlias(true);  //抗锯齿
        textPaint.setStrokeWidth(1);
        textPaint.setAlpha(15);
        textPaint.setStyle(Paint.Style.STROKE); //空心
        textPaint.setColor(Color.WHITE);//采用的颜色
        textPaint.setShadowLayer(1f, 0f, 3f, Color.LTGRAY);
//        textPaint.setShadowLayer(3f, 1, 1,getResources().getColor(android.R.color.white));//影音的设置
        canvas.drawText(str, destWidth/2, destHeight-45, textPaint);//绘制上去字，开始未知x,y采用那只笔绘制
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        bitmap.recycle();
        return FileUtil.getInstance().saveMyBitmap(icon,String.valueOf(new Date().getTime())); //保存至文件
    }
}
