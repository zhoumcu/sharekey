package cn.zhiao.develop.freeofo;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhiao.baselib.base.BaseActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tl_custom)
    Toolbar toolbar;
    @BindView(R.id.unlock)
    TextView unlock;
    @BindView(R.id.user_photo)
    CircleImageView userPhoto;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.person_layout)
    RelativeLayout personLayout;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.dl_left)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void initView() {
        //setToolbar(toolbar);
        toolbar.setTitle("共享密码");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    public void gotoWallet(View view) {
        showToast("火速开发中....");
    }

    public void gotoMyRoute(View view) {
        showToast("火速开发中....");
    }

    public void gotoInvite(View view) {
        showToast("火速开发中....");
    }

    public void gotoFeedBack(View view) {
        // 以对话框的形式弹出
        PgyFeedback.getInstance().showDialog(getContext());
        // 以Activity的形式打开，这种情况下必须在AndroidManifest.xml配置FeedbackActivity
        // 打开沉浸式,默认为false
        // FeedbackActivity.setBarImmersive(true);
        PgyFeedback.getInstance().showActivity(getContext());
    }

    public void gotoUpate(View view) {
        update();
    }

    public void gotoSetting(View view) {

    }

    private void update() {
        PgyUpdateManager.register(this, "",
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("更新")
                                .setMessage(appBean.getReleaseNote())
                                .setNegativeButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startDownloadTask(MainActivity.this, appBean.getDownloadURL());
                                            }
                                        }).show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                        Toast.makeText(MainActivity.this, "没有新版本！", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
