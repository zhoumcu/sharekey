package cn.zhiao.develop.freeofo.interfaces.presenter;


import android.widget.EditText;

import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

import cn.zhiao.baselib.base.BaseResponse;
import cn.zhiao.develop.freeofo.interfaces.model.LoginModel;
import cn.zhiao.develop.freeofo.interfaces.model.LoginModelImpl;
import cn.zhiao.develop.freeofo.interfaces.view.LoginView;
import cn.zhiao.develop.freeofo.presenter.interfaces.LoginPresenter;

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
        loginModel.login(username, password);
    }
}