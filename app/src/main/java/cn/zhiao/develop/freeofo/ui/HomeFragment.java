package cn.zhiao.develop.freeofo.ui;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhiao.baselib.base.BaseFragment;
import cn.zhiao.baselib.utils.gridpassword.GridPasswordView;
import cn.zhiao.develop.freeofo.R;
import cn.zhiao.develop.freeofo.bean.Keys;
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

    @Override
    public int getLayoutRes() {
        return R.layout.content;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void initPresenter() {
        presenter = new HomePresenterImpl(getContext(), this);
    }

    @OnClick({R.id.btn_unlock, R.id.btn_save, R.id.btn_light})
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
                    Camera camera = Camera.open();
                    Camera.Parameters mParameters = camera.getParameters();
                    mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//打开Camera.Parameters.FLASH_MODE_OFF则为关闭
                    camera.setParameters(mParameters);
                    camera.release();
                    isFlashOpen = true;
                } else {
                    Camera camera = Camera.open();
                    Camera.Parameters mParameters = camera.getParameters();
                    mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//打开Camera.Parameters.FLASH_MODE_OFF则为关闭
                    camera.setParameters(mParameters);
                    camera.release();
                    isFlashOpen = false;
                }
                break;
        }
    }

    @Override
    public void getData(List<Keys> object) {
        ResultDialogFragment.showDialog((AppCompatActivity) getActivity(), object);
    }

}
