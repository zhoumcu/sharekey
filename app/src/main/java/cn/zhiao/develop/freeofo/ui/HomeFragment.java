package cn.zhiao.develop.freeofo.ui;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhiao.baselib.base.BaseFragment;
import cn.zhiao.baselib.utils.CommonUtil;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;
import cn.zhiao.baselib.utils.gridpassword.GridPasswordView;
import cn.zhiao.develop.freeofo.R;
import cn.zhiao.develop.freeofo.bean.Constants;
import cn.zhiao.develop.freeofo.bean.Keys;
import cn.zhiao.develop.freeofo.bean.MessageEvent;
import cn.zhiao.develop.freeofo.interfaces.presenter.HomePresenterImpl;
import cn.zhiao.develop.freeofo.interfaces.view.HomeView;

/**
 * author：Administrator on 2017/4/13 09:08
 * company: xxxx
 * email：1032324589@qq.com
 */
public class HomeFragment extends BaseFragment implements HomeView {

    @Bind(R.id.notify)
    TextView notify;
    @Bind(R.id.btn_unlock)
    Button btnUnlock;
    @Bind(R.id.btn_save)
    Button btnSave;
    @Bind(R.id.btn_light)
    ImageView btnLight;
    @Bind(R.id.numView)
    GridPasswordView numView;
    private HomePresenterImpl presenter;
    private boolean isFlashOpen;
    private Camera m_Camera;
    private String url;

    @Override
    public int getLayoutRes() {
        return R.layout.content;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void initView() {
        url = CommonUtil.getCompleteUrl(getResources().getString(R.string.notify));
        notify.setText(SharedPrefrecesUtils.getStrFromSharedPrefrences(Constants.MSG,getContext())==""?
                getResources().getString(R.string.notify):SharedPrefrecesUtils.getStrFromSharedPrefrences(Constants.MSG,getContext()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        /* Do something */
        notify.setText(event.getAlart());
        url =  CommonUtil.getCompleteUrl(event.getAlart());
        logE("客户端收到推送内容"+event.getAlart());
    };

    @Override
    protected void initPresenter() {
        presenter = new HomePresenterImpl(getContext(), this);
    }

    @OnClick({R.id.btn_unlock, R.id.btn_save, R.id.btn_light,R.id.notify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_unlock:
                if(TextUtils.isEmpty(numView.getPassWord().toString())){
                    showToast("内容不能为空！");
                    return;
                }
                presenter.QueryData(numView.getPassWord().toString());
                break;
            case R.id.btn_save:
                SavePwdFragment.jumpIn((AppCompatActivity) getActivity());
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
            case R.id.notify:
                showToast("click");
                if(!TextUtils.isEmpty(url)){
                    Uri uri = Uri.parse(url);
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                }
                break;
        }
    }

    @Override
    public void getData(List<Keys> object) {
        ResultDialogFragment.showDialog((AppCompatActivity) getActivity(), object);
    }

}
