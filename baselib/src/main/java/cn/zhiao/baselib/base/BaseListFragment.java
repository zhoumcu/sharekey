package cn.zhiao.baselib.base;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

public abstract class BaseListFragment<M> extends BaseFragment implements IBaseView {

    public DataAdapter getDataAdapter(){
        return new DataAdapter(getContext());
    }

    public int getViewType1(int type){
        return 0;
    }

    public abstract BaseViewHolder<M> getViewHolder(ViewGroup parent, int viewType);

    public class DataAdapter extends RecyclerArrayAdapter<M> {

        public DataAdapter(Context context) {
            super(context);
        }

        public DataAdapter(Context context,List<M> data) {
            super(context,data);
        }

        @Override
        public int getViewType(int position) {
            return getViewType1(position);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }
    }

}
