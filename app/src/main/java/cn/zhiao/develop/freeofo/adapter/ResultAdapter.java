package cn.zhiao.develop.freeofo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.zhiao.baselib.utils.L;
import cn.zhiao.develop.freeofo.R;
import cn.zhiao.develop.freeofo.bean.Keys;
import cn.zhiao.develop.freeofo.ui.PayForMeActivity;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder1) {
            ((ViewHolder1) holder).keyPsd.setText("密码：   " + keyses.get(position).getKeyName());
            float right = (float)keyses.get(position).getRightNum();
            float wrong = (float)keyses.get(position).getWrongNum();
            String rate = (int)((right/(right+wrong))*100)+"%";
            ((ViewHolder1) holder).tvRate.setText("正确率："+rate);
            ((ViewHolder1) holder).btnWrong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Keys keys = keyses.get(position);
                    int number = keys.getWrongNum() + 1;
                    keys.setWrongNum(number);
                    keys.update(keys.getObjectId(), new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                L.i("bmob", "更新成功");
                                Toast.makeText(context,"更新成功+1",Toast.LENGTH_SHORT).show();
                            } else {
                                L.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }
            });
            ((ViewHolder1) holder).btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Keys keys = keyses.get(position);
                    int number = keys.getRightNum() + 1;
                    keys.setRightNum(number);
                    keys.update(keys.getObjectId(), new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                L.i("bmob", "更新成功");
                                Toast.makeText(context,"更新成功+1",Toast.LENGTH_SHORT).show();
                            } else {
                                L.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }
            });
            ((ViewHolder1) holder).btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PayForMeActivity.class);
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof ViewHolder2) {
            ((ViewHolder2) holder).keyPsd.setText(keyses.get(position).getKeyName());
            ((ViewHolder2) holder).bike.setText(keyses.get(position).getKeyNumber());
            float right = (float)keyses.get(position).getRightNum();
            float wrong = (float)keyses.get(position).getWrongNum();
            String rate = (int)((right/(right+wrong))*100)+"%";
            ((ViewHolder2) holder).tvRate.setText(rate);
            ((ViewHolder2) holder).btnWrong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Keys keys = keyses.get(position);
                    int number = keys.getWrongNum() + 1;
                    keys.setWrongNum(number);
                    keys.update(keyses.get(position).getObjectId(), new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                L.i("bmob", "更新成功");
                                Toast.makeText(context,"更新成功+1",Toast.LENGTH_SHORT).show();
                            } else {
                                L.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }
            });
            ((ViewHolder2) holder).btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Keys keys = keyses.get(position);
                    int number = keys.getRightNum() + 1;
                    keys.setRightNum(number);
                    keys.update(keyses.get(position).getObjectId(), new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                L.i("bmob", "更新成功");
                                Toast.makeText(context,"更新成功+1",Toast.LENGTH_SHORT).show();
                            } else {
                                L.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return keyses.size();
    }

    static public class ViewHolder1 extends RecyclerView.ViewHolder {
        @Bind(R.id.keyPsd)
        TextView keyPsd;
        @Bind(R.id.tv_rate)
        TextView tvRate;
        @Bind(R.id.btn_wrong)
        TextView btnWrong;
        @Bind(R.id.btn_right)
        TextView btnRight;
        @Bind(R.id.btn_pay)
        TextView btnPay;

        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class ViewHolder2 extends RecyclerView.ViewHolder {
        @Bind(R.id.bike)
        TextView bike;
        @Bind(R.id.keyPsd)
        TextView keyPsd;
        @Bind(R.id.tv_rate)
        TextView tvRate;
        @Bind(R.id.btn_wrong)
        TextView btnWrong;
        @Bind(R.id.btn_right)
        TextView btnRight;


        public ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
