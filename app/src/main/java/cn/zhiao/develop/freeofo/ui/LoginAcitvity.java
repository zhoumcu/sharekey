package cn.zhiao.develop.freeofo.ui;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;
import cn.zhiao.develop.freeofo.MainActivity;
import cn.zhiao.develop.freeofo.R;
import cn.zhiao.develop.freeofo.interfaces.presenter.LoginPresenterImpl;
import cn.zhiao.develop.freeofo.interfaces.view.LoginView;

/**
 * author：Administrator on 2017/3/1 09:07
 * company: xxxx
 * email：1032324589@qq.com
 */

public class LoginAcitvity extends BaseActivity implements LoginView {

    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.tv_pwd)
    EditText tvPwd;
    private LoginPresenterImpl loginPresenter;

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {
        loginPresenter = new LoginPresenterImpl();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_login;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                // 处理返回逻辑
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({R.id.tv_login, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                close();
                final String phone1 = phone.getText().toString();
                final String mPassword = tvPwd.getText().toString();
                if (phone1.length() <= 0) {
                    phone.setError("手机号格式错误");
                    return;
                }
                if (mPassword.length() < 4 || mPassword.length() > 12) {
                    tvPwd.setError("密码应为5-12位");
                    return;
                }
                loginPresenter.login(phone,tvPwd,this);
                break;
            case R.id.tv_register:
                gt(RegisterActivity.class);
                break;
        }
    }

    @Override
    public void loginResult(int i, String s) {
        showToast("登录成功:");
        gt(MainActivity.class);
        finish();
        SharedPrefrecesUtils.saveBooleanToSharedPrefrences("is_login",true,getContext());
    }
}
