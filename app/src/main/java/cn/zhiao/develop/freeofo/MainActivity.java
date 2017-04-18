package cn.zhiao.develop.freeofo;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pgyersdk.feedback.PgyFeedback;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;

import butterknife.Bind;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.zhiao.baselib.app.BaseApplication;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;
import cn.zhiao.develop.freeofo.bean.Constants;
import cn.zhiao.develop.freeofo.bean.User;
import cn.zhiao.develop.freeofo.ui.HomeFragment;
import cn.zhiao.develop.freeofo.ui.MinePwdActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tl_custom)
    Toolbar tlCustom;
    @Bind(R.id.user_photo)
    CircleImageView userPhoto;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_phone)
    TextView userPhone;
    @Bind(R.id.person_layout)
    RelativeLayout personLayout;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.dl_left)
    DrawerLayout dlLeft;
    @Bind(R.id.containers)
    FrameLayout containers;
    private ActionBarDrawerToggle mDrawerToggle;
    private User user;

    @Override
    public void initView() {
        BmobUpdateAgent.update(this);
        //setToolbar(toolbar);
        tlCustom.setTitle("共享密码");//设置Toolbar标题
        tlCustom.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(tlCustom);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, dlLeft, tlCustom, R.string.open, R.string.close) {
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
        dlLeft.setDrawerListener(mDrawerToggle);
        tvVersion.setText("V" + BaseApplication.getVersion());
        addFragment(R.id.containers,new HomeFragment());
        userPhone.setText(user.getUsername());
        initBanner();
        initInterstitialAD();
    }

    private void initInterstitialAD() {
        /**
        * 创建插屏广告
        * "appid"指在 http://e.qq.com/dev/ 能看到的app唯一字符串
        * "广告位 id" 指在 http://e.qq.com/dev/ 生成的数字串，
        * 并非 appid 或者 appkey
        */
        final InterstitialAD iad = new InterstitialAD(this, Constants.APPID, Constants.InterteristalPosID);
            iad.setADListener(new AbstractInterstitialADListener() {

                @Override
                public void onADReceive() {
                     /*
                    * 展示插屏广告，仅在回调接口的adreceive事件发生后调用才有效。
                    */
                    iad.show();
                }

                @Override
                public void onNoAD(int arg0) {
                    Log.i("AD_DEMO", "LoadInterstitialAd Fail:" + arg0);
                }

        });
        //请求插屏广告，每次重新请求都可以调用此方法。
        iad.loadAD();
    }

    private void initBanner() {
        // 创建Banner广告AdView对象
        // appId : 在 http://e.qq.com/dev/ 能看到的app唯一字符串
        // posId : 在 http://e.qq.com/dev/ 生成的数字串，并非 appid 或者 appkey
        BannerView banner = new BannerView(this, ADSize.BANNER, Constants.APPID, Constants.BannerPosID);
        //设置广告轮播时间，为0或30~120之间的数字，单位为s,0标识不自动轮播
        banner.setRefresh(30);
        banner.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(int arg0) {
                Log.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        /* 发起广告请求，收到广告数据后会展示数据   */
        banner.loadAD();

    }

    @Override
    public void initPresenter() {
        user = (User) SharedPrefrecesUtils.readObject(getContext(),"user");
//        final rx.plugins.RxJavaErrorHandler rxJavaErrorHandler = new rx.plugins.RxJavaErrorHandler() {
//            @Override
//            public void handleError( final Throwable x ) {
//                System.out.println( "rxJavaErrorHandler.handleError: " + x.getClass().getSimpleName() );
//            }
//        };
//        rx.plugins.RxJavaPlugins.getInstance().registerErrorHandler( rxJavaErrorHandler );
//
//        final rx.functions.Action1<Long> action = new rx.functions.Action1<Long>() {
//            @Override
//            public void call( final Long L ) {
//                System.out.println( "tick" );
//                try { Thread.sleep( 2500 ); } catch ( InterruptedException x ) {}
//            }
//        };
//
//        final rx.Subscription subscription = rx.Observable.interval( 100L, TimeUnit.MILLISECONDS )
//                .subscribeOn( rx.schedulers.Schedulers.io() )
//                .observeOn( rx.schedulers.Schedulers.newThread() )
//                .subscribe( action );
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    public void gotoWallet(View view) {
        showToast("下个版本加入该功能....");
        //gt(PayActivity.class);
    }

    public void gotoMyRoute(View view) {
        gt(MinePwdActivity.class);
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
//        PgyUpdateManager.register(this, "",
//                new UpdateManagerListener() {
//                    @Override
//                    public void onUpdateAvailable(final String result) {
//                        // 将新版本信息封装到AppBean中
//                        final AppBean appBean = getAppBeanFromString(result);
//                        new AlertDialog.Builder(MainActivity.this)
//                                .setTitle("更新")
//                                .setMessage(appBean.getReleaseNote())
//                                .setNegativeButton("确定",
//                                        new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                startDownloadTask(MainActivity.this, appBean.getDownloadURL());
//                                            }
//                                        }).show();
//                    }
//
//                    @Override
//                    public void onNoUpdateAvailable() {
//                        Toast.makeText(MainActivity.this, "没有新版本！", Toast.LENGTH_SHORT).show();
//                    }
//                });
        BmobUpdateAgent.forceUpdate(getContext());
    }

}
