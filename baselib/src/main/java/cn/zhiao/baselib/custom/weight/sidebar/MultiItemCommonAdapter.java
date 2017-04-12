package cn.zhiao.baselib.custom.weight.sidebar;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**多个type*/
public abstract class MultiItemCommonAdapter<T> extends CommonAdapter<T>
{

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context context, List<T> datas,
                                  MultiItemTypeSupport<T> multiItemTypeSupport)
    {
        super(context, -1, datas);
        mMultiItemTypeSupport = multiItemTypeSupport;
        if (mMultiItemTypeSupport == null) throw new IllegalArgumentException("the mMultiItemTypeSupport can not be null.");
    }

    @Override
    public int getItemViewType (int position) {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
        return super.getItemViewType(position);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (mMultiItemTypeSupport == null) return super.onCreateViewHolder(parent, viewType);

        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        RecyclerViewHolder holder = RecyclerViewHolder.get(mContext, null, parent, layoutId, -1);
        setListener(parent, holder, viewType);
        return holder;
    }

}
