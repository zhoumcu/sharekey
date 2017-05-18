package cn.zhiao.develop.freeofo.ui;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoApplication;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.db.DemoDBManager;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.baselib.utils.L;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;
import cn.zhiao.develop.freeofo.MainActivity;
import cn.zhiao.develop.freeofo.R;
import cn.zhiao.develop.freeofo.bean.User;
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
                //close();
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
    public void loginResult(User user) {
        //showToast("登录成功:");
        //gt(MainActivity.class);
        //finish();
        //SharedPrefrecesUtils.saveObject(getContext(),"user", user);
        //SharedPrefrecesUtils.saveBooleanToSharedPrefrences("is_login",true,getContext());
        loginleanCloud(user);
    }

    public void loginleanCloud(final User user) {
//        String clientId;
//        if(user.isLocker()){
//            clientId = user.getLockerId();
//        }else{
//            clientId = user.getUsername();
//        }
//        if (TextUtils.isEmpty(clientId.trim())) {
//            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
        login(phone.getText().toString(),tvPwd.getText().toString(),user);
    }
    private void login(String currentUsername, String currentPassword, final User user){
        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(currentUsername);

        final long start = System.currentTimeMillis();
        // call login method
        L.d("EMClient.getInstance().login");
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                L.d( "login: onSuccess");

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        DemoApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }

//                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
//                    pd.dismiss();
//                }
                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

                SharedPrefrecesUtils.saveObject(getContext(),"user", user);
                SharedPrefrecesUtils.saveBooleanToSharedPrefrences("is_login",true,getContext());
                Log.d("main", "登录聊天服务器成功！");
                gt(MainActivity.class);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                L.d( "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                L.d( "login: onError: " + code);
//                if (!progressShow) {
//                    return;
//                }
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        pd.dismiss();
//                        Toast.makeText(getApplicationContext(), getString(com.hyphenate.chatuidemo.R.string.Login_failed) + message,
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }
}
