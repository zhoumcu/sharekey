package cn.zhiao.baselib.custom.weight.sidebar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者：wxx on 2016/4/22 10:02
 * <p>
 * 邮箱：13026620809@163.com
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder>
{
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
    }
    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        RecyclerViewHolder viewHolder = RecyclerViewHolder.get(mContext,null, parent, mLayoutId,-1);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position)
    {
        holder.updatePosition(position);
        convert(holder, mDatas.get(position));
    }
    /**子类重写的方法*/
    public abstract void convert(RecyclerViewHolder holder, T t);

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }
    protected int getPosition(RecyclerView.ViewHolder viewHolder)
    {
        return viewHolder.getAdapterPosition();
    }

    protected boolean isEnabled(int viewType)
    {
        return true;
    }

    protected void setListener(final ViewGroup parent, final RecyclerViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mOnItemClickListener != null)
                {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                if (mOnItemClickListener != null)
                {
                    int position = getPosition(viewHolder);
                    return mOnItemClickListener.onItemLongClick(parent, v, mDatas.get(position), position);
                }
                return false;
            }
        });
    }
}

