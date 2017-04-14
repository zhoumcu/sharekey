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

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Keys> keyses = new ArrayList<>();
    private Context context;
    private int viewType = 0;

    public ResultAdapter(Context context, List<Keys> keyses) {
        this.context = context;
        this.keyses = keyses;
    }

    public ResultAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<Keys> keyses) {
        this.keyses = keyses;
        notifyDataSetChanged();
    }

    public void setItemViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ViewHolder2(LayoutInflater.from(context).inflate(R.layout.item_minekeys, parent, false));
        } else {
            return new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.item_keys, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder1){
            ((ViewHolder1)holder).keyPsd.setText("密码：   " + keyses.get(position).getKeyName());
        }else if(holder instanceof ViewHolder2){
            ((ViewHolder2)holder).keyPsd.setText(keyses.get(position).getKeyName());
            ((ViewHolder2)holder).bike.setText(keyses.get(position).getKeyNumber());
        }

    }

    @Override
    public int getItemCount() {
        return keyses.size();
    }

    static public class ViewHolder1 extends RecyclerView.ViewHolder {
        @Bind(R.id.keyPsd)
        TextView keyPsd;

        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class ViewHolder2 extends RecyclerView.ViewHolder{
        @Bind(R.id.bike)
        TextView bike;
        @Bind(R.id.keyPsd)
        TextView keyPsd;

        public ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
