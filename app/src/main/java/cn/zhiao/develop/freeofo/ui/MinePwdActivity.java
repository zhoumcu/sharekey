package cn.zhiao.develop.freeofo.ui;

import android.support.v7.widget.LinearLayoutManager;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.Bind;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;
import cn.zhiao.develop.freeofo.R;
import cn.zhiao.develop.freeofo.adapter.ResultAdapter;
import cn.zhiao.develop.freeofo.bean.Keys;
import cn.zhiao.develop.freeofo.bean.User;
import cn.zhiao.develop.freeofo.interfaces.presenter.HomePresenterImpl;
import cn.zhiao.develop.freeofo.interfaces.view.HomeView;

/**
 * Created by ymn on 2017/4/13.
 */
public class MinePwdActivity extends BaseActivity implements HomeView {
    @Bind(R.id.recycler)
    EasyRecyclerView recycler;
    private HomePresenterImpl presenter;
    private ResultAdapter adapter;

    @Override
    public void initView() {
        recycler.setEmptyView(R.layout.error_view);
        recycler.setErrorView(R.layout.error_view);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ResultAdapter(getContext());
        recycler.setAdapter(adapter);
    }

    @Override
    public void initPresenter() {
        User user = (User) SharedPrefrecesUtils.readObject(getContext(), "user");
        presenter = new HomePresenterImpl(getContext(), this);
        presenter.QueryMyData(user.getObjectId());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_minepwd;
    }

    @Override
    public void getData(List<Keys> keyses) {
        if (keyses.size() == 0)
            recycler.showEmpty();
        adapter.addAll(keyses);
    }
}
