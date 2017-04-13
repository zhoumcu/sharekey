package cn.zhiao.develop.freeofo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhiao.develop.freeofo.R;
import cn.zhiao.develop.freeofo.bean.Keys;

/**
 * author：Administrator on 2017/4/13 10:38
 * company: xxxx
 * email：1032324589@qq.com
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private List<Keys> keyses = new ArrayList<>();
    private Context context;

    public ResultAdapter(Context context, List<Keys> keyses) {
        this.context = context;
        this.keyses = keyses;
    }

    public ResultAdapter(Context context) {
        this.context = context;
    }
    public void addAll(List<Keys> keyses){
        this.keyses = keyses;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_keys, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.keyPsd.setText("密码：   "+keyses.get(position).getKeyName());
    }

    @Override
    public int getItemCount() {
        return keyses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.keyPsd)
        TextView keyPsd;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
