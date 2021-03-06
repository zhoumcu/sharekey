package cn.zhiao.develop.freeofo.interfaces.model;


import android.widget.EditText;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.zhiao.develop.freeofo.bean.User;
import cn.zhiao.develop.freeofo.utils.CommonCallback;

/**
* Created by Administrator on 2017/03/24
*/

public class LoginModelImpl implements LoginModel{

    @Override
    public String verfiyUserName(EditText text) {
        if(text.getText().toString().isEmpty()){
            return null;
        }

        return text.getText().toString();
    }

    @Override
    public String verfiyPassWord(EditText text) {
        if(text.getText().toString().isEmpty()){
            return null;
        }
        return text.getText().toString();
    }

    @Override
    public void login(final String username, final String password, final CommonCallback<User> callback) {
        final User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                if (e == null) {
                    //SharedPrefrecesUtils.saveObject("user", bmobUser);
                    callback.onSucess(bmobUser);
                } else {
                    //toast(e.getMessage());
                    callback.onError(e.getMessage());
                }
            }
        });
    }
}