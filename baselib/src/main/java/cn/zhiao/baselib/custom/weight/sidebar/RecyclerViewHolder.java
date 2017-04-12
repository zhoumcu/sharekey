package cn.zhiao.baselib.custom.weight.sidebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.zhiao.baselib.custom.DotImageView;

/**
 * 作者：wxx on 2016/4/22 09:54
 * <p>
 * 邮箱：13026620809@163.com
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;
    private int mLayoutId;

    public RecyclerViewHolder(Context context, View itemView, ViewGroup parent, int position) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mPosition = position;
        mViews = new SparseArray<View>();
        mConvertView.setTag(this);

    }


    public static RecyclerViewHolder get(Context context, View convertView,ViewGroup parent, int layoutId, int position) {
        if (null==convertView)
        {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                    false);
            RecyclerViewHolder holder = new RecyclerViewHolder(context, itemView, parent, position);
            holder.mLayoutId = layoutId;
            return holder;
        } else {
            RecyclerViewHolder holder = (RecyclerViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView()
    {
        return mConvertView;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerViewHolder setText(int viewId, String text)
    {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public RecyclerViewHolder setSimpleDraweeViewResource(int viewId, String  url)
    {
        SimpleDraweeView view = getView(viewId);
        view.setImageURI(Uri.parse(url));
        return this;
    }
    public RecyclerViewHolder setSimpleImage(int viewid,int id){
        SimpleDraweeView simpleDraweeView=this.getView(viewid);
        simpleDraweeView.setBackgroundResource(id);
        // simpleDraweeView.setImageURI(Uri.parse(id));
        return this;
    }
    public RecyclerViewHolder setImageResource(int viewId, int resId,int haveDot)
    {
        DotImageView view = getView(viewId);
        if (haveDot == 1){

            view.setDot(1);

        }
        view.setImageResource(resId);
        //view.setImageDrawable(mContext.getResources().getDrawable(resId));
        return this;
    }

    public RecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap)
    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public RecyclerViewHolder setImageDrawable(int viewId, Drawable drawable)
    {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public RecyclerViewHolder setBackgroundColor(int viewId, int color)
    {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public RecyclerViewHolder setBackgroundRes(int viewId, int backgroundRes)
    {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public RecyclerViewHolder setTextColor(int viewId, int textColor)
    {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public RecyclerViewHolder setTextColorRes(int viewId, int textColorRes)
    {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint("NewApi")
    public RecyclerViewHolder setAlpha(int viewId, float value)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            getView(viewId).setAlpha(value);
        } else
        {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public RecyclerViewHolder setVisible(int viewId, boolean visible)
    {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public RecyclerViewHolder linkify(int viewId)
    {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }


    public RecyclerViewHolder setTypeface(Typeface typeface, int... viewIds)
    {
        for (int viewId : viewIds)
        {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public RecyclerViewHolder setProgress(int viewId, int progress)
    {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public RecyclerViewHolder setProgress(int viewId, int progress, int max)
    {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public RecyclerViewHolder setMax(int viewId, int max)
    {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public RecyclerViewHolder setRating(int viewId, float rating)
    {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public RecyclerViewHolder setRating(int viewId, float rating, int max)
    {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public RecyclerViewHolder setTag(int viewId, Object tag)
    {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public RecyclerViewHolder setTag(int viewId, int key, Object tag)
    {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public RecyclerViewHolder setChecked(int viewId, boolean checked)
    {
        Checkable view =getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 关于事件的
     */
    public RecyclerViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener)
    {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener){
        CheckBox view = getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    public RecyclerViewHolder setOnTouchListener(int viewId,
                                         View.OnTouchListener listener)
    {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public RecyclerViewHolder setOnLongClickListener(int viewId,
                                             View.OnLongClickListener listener)
    {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public void updatePosition(int position)
    {
        mPosition = position;
    }

    public int getLayoutId()
    {
        return mLayoutId;
    }
}

