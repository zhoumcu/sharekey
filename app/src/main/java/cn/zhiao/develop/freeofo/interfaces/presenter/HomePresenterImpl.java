package cn.zhiao.develop.freeofo.interfaces.presenter;

import android.content.Context;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.zhiao.develop.freeofo.bean.Keys;
import cn.zhiao.develop.freeofo.interfaces.model.HomeModel;
import cn.zhiao.develop.freeofo.interfaces.model.HomeModelImpl;
import cn.zhiao.develop.freeofo.interfaces.view.HomeView;
import cn.zhiao.develop.freeofo.presenter.interfaces.HomePresenter;

/**
* Created by Administrator on 2017/04/13
*/

public class HomePresenterImpl implements HomePresenter {
    private HomeModel homeModel;
    private HomeView homeView;
    private Context context;

    public HomePresenterImpl(Context context, HomeView homeView) {
        this.context = context;
        this.homeView = homeView;
        homeModel = new HomeModelImpl();
    }

    @Override
    public void QueryData(String data) {
        homeView.showProgress("小哥火速查询中，请耐心等待...");
        //查找Person表里面id为6b6c11c537的数据
        BmobQuery<Keys> bmobQuery = new BmobQuery<Keys>();
        //查询playerName叫“比目”的数据
        bmobQuery.addWhereEqualTo("keyNumber", data);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        bmobQuery.setLimit(50);
        //执行查询方法
        bmobQuery.findObjects(new FindListener<Keys>() {
            @Override
            public void done(List<Keys> object, BmobException e) {
                if(e==null){
                    homeView.hideProgress();
                    homeView.getData(object);
                }else{
                    homeView.showToast("查询失败：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void QueryMyData(String data) {
        homeView.showProgress("小哥火速查询中，请耐心等待...");
        //查找Person表里面id为6b6c11c537的数据
        BmobQuery<Keys> bmobQuery = new BmobQuery<Keys>();
        //查询playerName叫“比目”的数据
        bmobQuery.addWhereEqualTo("objectId", data);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        bmobQuery.setLimit(50);
        //执行查询方法
        bmobQuery.findObjects(new FindListener<Keys>() {
            @Override
            public void done(List<Keys> object, BmobException e) {
                if(e==null){
                    homeView.hideProgress();
                    homeView.getData(object);
                }else{
                    homeView.showToast("查询失败：" + e.getMessage());
                }
            }
        });
    }
}