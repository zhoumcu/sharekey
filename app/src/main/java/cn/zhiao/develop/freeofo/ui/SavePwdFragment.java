package cn.zhiao.develop.freeofo.ui;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.zhiao.baselib.base.BaseFragment;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;
import cn.zhiao.baselib.utils.gridpassword.GridPasswordView;
import cn.zhiao.develop.freeofo.R;
import cn.zhiao.develop.freeofo.bean.Keys;
import cn.zhiao.develop.freeofo.bean.User;

/**
 * author：Administrator on 2017/4/13 09:08
 * company: xxxx
 * email：1032324589@qq.com
 */
public class SavePwdFragment extends BaseFragment {
    public static final String MY_TAG = "SavePwdFragment";
    @Bind(R.id.numView)
    AppCompatEditText numView;
    @Bind(R.id.pswView)
    GridPasswordView pswView;
    private boolean isFlashOpen;
    private User user;
    private Camera m_Camera;

    public static SavePwdFragment newInstance() {
        Bundle args = new Bundle();
        SavePwdFragment fragment = new SavePwdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void jumpIn(AppCompatActivity at) {
        FragmentManager fragmentmanager = at.getSupportFragmentManager();
        SavePwdFragment fragment = SavePwdFragment.newInstance();
        fragmentmanager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.containers, fragment, SavePwdFragment.MY_TAG)
                .commitAllowingStateLoss();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.aty_save;
    }

    @Override
    public void initView() {
        user = (User) SharedPrefrecesUtils.readObject(getContext(),"user");
    }

    @Override
    protected void initPresenter() {

    }

    @OnClick({R.id.btn_save, R.id.btn_light})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if(TextUtils.isEmpty(numView.getText().toString())){
                    showToast("车牌号码不能为空或者输入不对！");
                    return;
                }
                if(TextUtils.isEmpty(pswView.getPassWord().toString())||pswView.getPassWord().toString().length()<4){
                    showToast("密码不能为空！");
                    return;
                }
                showProgress("正在保存......");
                Keys keys = new Keys();
                //注意：不能调用gameScore.setObjectId("")方法
                keys.setUserId(user.getObjectId());
                keys.setKeyName(pswView.getPassWord().toString());
                keys.setKeyNumber(numView.getText().toString());
                keys.save(new SaveListener<String>() {

                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            hideProgress();
                            numView.setText(null);
                            pswView.clearPassword();
                            showToast("保存密码成功！可以通过<菜单->我的密码>中查询");
                        } else {
                            hideProgress();
                            showToast("失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                break;
            case R.id.btn_light:
                if (!isFlashOpen) {
                    try{
                        m_Camera = Camera.open();
                        Camera.Parameters mParameters;
                        mParameters = m_Camera.getParameters();
                        mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        m_Camera.setParameters(mParameters);
                    } catch(Exception ex){}
                    isFlashOpen = true;
                } else {
                    try{
                        Camera.Parameters mParameters;
                        mParameters = m_Camera.getParameters();
                        mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        m_Camera.setParameters(mParameters);
                        m_Camera.release();
                    } catch(Exception ex){}
                    isFlashOpen = false;
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
