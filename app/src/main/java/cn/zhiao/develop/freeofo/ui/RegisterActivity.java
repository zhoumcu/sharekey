package cn.zhiao.develop.freeofo.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.develop.freeofo.R;
import cn.zhiao.develop.freeofo.bean.User;

/**
 * author：Administrator on 2016/12/12 14:16
 * company: xxxx
 * email：1032324589@qq.com
 */

public class RegisterActivity extends BaseActivity {


    @Bind(R.id.ed_account)
    EditText edAccount;
    @Bind(R.id.imgCancel)
    ImageView imgCancel;
    @Bind(R.id.layoutPhone)
    RelativeLayout layoutPhone;
    @Bind(R.id.ed_pwd)
    EditText edPwd;
    @Bind(R.id.ed_repwd)
    EditText edRepwd;
    @Bind(R.id.rl_1)
    RelativeLayout rl1;
    @Bind(R.id.ed_phone)
    EditText edPhone;
    @Bind(R.id.btn_sendcode)
    TextView btnSendcode;
    @Bind(R.id.ed_code)
    EditText edCode;
    @Bind(R.id.rl_2)
    RelativeLayout rl2;
    @Bind(R.id.btn_register)
    Button btnRegister;
    private View rootView;

    @OnClick(R.id.btn_register)
    public void onClick() {
        //close();
        final String edPhone1 = edAccount.getText().toString();
        if (edAccount.length() < 11 || edAccount.length() > 11) {
            edAccount.setError("输入手机号码不正确");
            return;
        }
        if (edPwd.length() < 6 || edPwd.length() > 12) {
            edPwd.setError("密码应为6-12位");
            return;
        }
        if (!edPwd.getText().toString().equals(edRepwd.getText().toString())) {
            edRepwd.setError("两次密码输入不对应");
            return;
        }
        User user = new User();
        user.setMobilePhoneNumber(edPhone1);
        user.setPassword(edPwd.getText().toString());
        user.setUsername(edPhone1);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User s, BmobException e) {
                if (e == null) {
                    showToast("注册成功");
                    finish();
                } else {
                    showToast(e.getMessage());
                }
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_register;
    }

}
