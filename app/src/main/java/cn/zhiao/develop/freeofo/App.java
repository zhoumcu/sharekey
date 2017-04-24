package cn.zhiao.develop.freeofo;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.leancloud.chatkit.LCChatKit;
import cn.zhiao.baselib.app.BaseApplication;
import cn.zhiao.baselib.utils.L;
import cn.zhiao.develop.freeofo.ui.chatkit.CustomUserProvider;

/**
 * author：Administrator on 2017/4/12 10:42
 * company: xxxx
 * email：1032324589@qq.com
 */

public class App extends BaseApplication{
    private final String APP_ID = "YN8irJOPJnWukhHem1BSl7qY-gzGzoHsz";
    private final String APP_KEY = "9ToLV1p8ous2eOj9Paje8Umt";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initBmob();
//        initUmengPush();
//        PgyCrashManager.register(this);
        EventBus.builder().throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();
//        BP.init("25fbbe530d801ab95a2723acf03c50b6");
        AVOSCloud.initialize(this,"YN8irJOPJnWukhHem1BSl7qY-gzGzoHsz","9ToLV1p8ous2eOj9Paje8Umt");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        AVOSCloud.setDebugLogEnabled(true);
        LCChatKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);
        AVIMClient.setOfflineMessagePush(true);
        AVIMClient.setAutoOpen(false);
    }
    private void initUmengPush(){
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                L.e(deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                //
                L.e(s+s1);
            }
        });

    }
    private void initBmob() {
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("25fbbe530d801ab95a2723acf03c50b6")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024*1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);

        // 初始化BmobSDK
        Bmob.initialize(this, "25fbbe530d801ab95a2723acf03c50b6");
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();
        // 启动推送服务
        BmobPush.startWork(this);

//        BmobUpdateAgent.initAppVersion();
        BmobUpdateAgent.setUpdateOnlyWifi(false);
    }
}
