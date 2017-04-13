package cn.zhiao.develop.freeofo.interfaces.presenter;


import android.widget.EditText;

import cn.zhiao.develop.freeofo.bean.User;
import cn.zhiao.develop.freeofo.interfaces.model.LoginModel;
import cn.zhiao.develop.freeofo.interfaces.model.LoginModelImpl;
import cn.zhiao.develop.freeofo.interfaces.view.LoginView;
import cn.zhiao.develop.freeofo.presenter.interfaces.LoginPresenter;
import cn.zhiao.develop.freeofo.utils.CommonCallback;

/**
* Created by Administrator on 2017/03/24
*/

public class LoginPresenterImpl implements LoginPresenter {

    @Override
    public void login(EditText eduser, EditText edpassword, final LoginView loginView) {
        //实例化产品模型
        final LoginModel loginModel = new LoginModelImpl();
        loginView.showProgress();
        String username = loginModel.verfiyUserName(eduser);
        String password = loginModel.verfiyPassWord(edpassword);
        if(username==null&&password==null) return;
        //从产品模型中获取产品分组数据
        loginModel.login(username, password, new CommonCallback<User>() {
            @Override
            public void onSucess(User data) {
                loginView.loginResult(data);
                loginView.hideProgress();
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }
}