package cn.zhiao.develop.freeofo;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.feedback.FeedbackAgent;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.event.LCIMIMTypeMessageEvent;
import cn.zhiao.baselib.app.BaseApplication;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;
import cn.zhiao.develop.freeofo.bean.Constants;
import cn.zhiao.develop.freeofo.bean.User;
import cn.zhiao.develop.freeofo.ui.CommonActivity;
import cn.zhiao.develop.freeofo.ui.HomeFragment;
import cn.zhiao.develop.freeofo.ui.LoginAcitvity;
import cn.zhiao.develop.freeofo.ui.MinePwdActivity;
import cn.zhiao.develop.freeofo.ui.PayChoocesActivity;
import cn.zhiao.develop.freeofo.ui.chatkit.CustomUserProvider;
import de.greenrobot.event.EventBus;
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
    @Bind(R.id.version_notify)
    TextView versionNotify;
    @Bind(R.id.dl_left)
    DrawerLayout dlLeft;
    private ActionBarDrawerToggle mDrawerToggle;
    private User userL;
    private FeedbackAgent agent;
    public static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();
    public static List<LCChatKitUser> partAllUsers = new ArrayList<LCChatKitUser>();
    private boolean isRecvMsg = false;

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        BmobUpdateAgent.update(this);
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                // TODO Auto-generated method stub
                //根据updateStatus来判断更新是否成功
                logE(updateStatus + updateInfo.toString());
                if (updateStatus == 0) {
                    versionNotify.setVisibility(View.VISIBLE);
                } else {
                    versionNotify.setVisibility(View.GONE);
                }
            }
        });

        agent = new FeedbackAgent(getContext());
        agent.sync();
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
        addFragment(R.id.containers, new HomeFragment());
        userPhone.setText(userL.getUsername());
        //initBanner();
        //initInterstitialAD();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ExcelUtils.readExcel();
//            }
//        }).start();
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
        userL = (User) SharedPrefrecesUtils.readObject(getContext(), "user");
        initLockerList();
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
//        showToast("下个版本加入该功能....");
        gt(PayChoocesActivity.class);
    }

    public void gotoMyRoute(View view) {
        gt(MinePwdActivity.class);
    }

    public void gotoInvite(View view) {
        showToast("火速开发中....");
    }

    public void gotoFeedBack(View view) {
//        // 以对话框的形式弹出
//        PgyFeedback.getInstance().showDialog(getContext());
//        // 以Activity的形式打开，这种情况下必须在AndroidManifest.xml配置FeedbackActivity
//        // 打开沉浸式,默认为false
//        // FeedbackActivity.setBarImmersive(true);
//        PgyFeedback.getInstance().showActivity(getContext());
        agent.startDefaultThreadActivity();
    }

    public void gotoWork(View view) {
        agent.startDefaultThreadActivity();
    }

    public void gotoUpate(View view) {
        update();
    }

    public void gotoSetting(View view) {
        SharedPrefrecesUtils.saveObject(getContext(),"user", null);
        SharedPrefrecesUtils.saveBooleanToSharedPrefrences("is_login",false,getContext());
        finish();
        gt(LoginAcitvity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_share:
                isRecvMsg = false;
                gt(CommonActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        System.out.println("执行了onPrepareOptionsMenu");
        if (isRecvMsg) {
            menu.findItem(R.id.menu_share).setIcon(
                    R.mipmap.chat);
        } else {
            menu.findItem(R.id.menu_share).setIcon(
                    R.mipmap.chated);
        }
        // getSupportMenuInflater().inflate(R.menu.book_detail, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    private void initLockerList() {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("isLocker", true);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    //showToast("查询用户成功:"+object.size());
                    partUsers.clear();
//                    partAllUsers.clear();
                    for (User user:object) {
//                        partAllUsers.add(new LCChatKitUser(user.getLockerId(), user.getLockerName(), user.getPhotoUrl()));
                        if(!user.getUsername().equals(userL.getUsername())&&user.isLocker())
                            partUsers.add(new LCChatKitUser(user.getLockerId(), user.getLockerName(), user.getPhotoUrl()));
                    }
                    CustomUserProvider.getInstance().setAllUsers(partUsers);
                }else{
                    showToast("更新用户信息失败:" + e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    /**
     * 处理推送过来的消息
     * 同理，避免无效消息，此处加了 conversation id 判断
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LCIMIMTypeMessageEvent messageEvent) {
        if ( null != messageEvent){
            isRecvMsg = true;
            invalidateOptionsMenu();
        }
    }
}
