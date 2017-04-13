package cn.zhiao.develop.freeofo.interfaces.view;

import java.util.List;

import cn.zhiao.baselib.base.IBaseView;
import cn.zhiao.develop.freeofo.bean.Keys;

/**
* Created by Administrator on 2017/04/13
*/

public interface HomeView extends IBaseView{

    void getData(List<Keys> object);
}