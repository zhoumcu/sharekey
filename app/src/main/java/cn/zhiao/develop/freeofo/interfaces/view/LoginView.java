package cn.zhiao.develop.freeofo.interfaces.view;


import cn.zhiao.baselib.base.IBaseView;
import cn.zhiao.develop.freeofo.bean.User;

/**
* Created by Administrator on 2017/03/24
*/

public interface LoginView extends IBaseView {

    void loginResult(User user);
}