package cn.zhiao.develop.freeofo.interfaces.model;

import android.widget.EditText;

import cn.zhiao.develop.freeofo.bean.User;
import cn.zhiao.develop.freeofo.utils.CommonCallback;


/**
* Created by Administrator on 2017/03/24
*/

public interface LoginModel{
    public String verfiyUserName(EditText text);
    public String verfiyPassWord(EditText text);
    public void login(String username, String password,CommonCallback<User> callback);
}