package cn.zhiao.develop.freeofo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.zhiao.baselib.app.BaseApplication;
import cn.zhiao.baselib.utils.L;

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
        regiesterEaseMob();
//        initUmengPush();
//        PgyCrashManager.register(this);
        EventBus.builder().throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();
//        BP.init("25fbbe530d801ab95a2723acf03c50b6");
    }
    private void regiesterEaseMob(){
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
            L.e("enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().init(this, options);
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
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
