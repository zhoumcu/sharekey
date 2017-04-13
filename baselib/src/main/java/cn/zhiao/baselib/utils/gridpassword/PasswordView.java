package cn.zhiao.baselib.utils.gridpassword;

/**
 * author：Administrator on 2017/4/13 13:10
 * company: xxxx
 * email：1032324589@qq.com
 */
interface PasswordView {

    //void setError(String error);

    String getPassWord();

    void clearPassword();

    void setPassword(String password);

    void setPasswordVisibility(boolean visible);

    void togglePasswordVisibility();

    void setOnPasswordChangedListener(GridPasswordView.OnPasswordChangedListener listener);

    void setPasswordType(PasswordType passwordType);
}