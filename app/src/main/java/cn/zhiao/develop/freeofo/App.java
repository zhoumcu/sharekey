package cn.zhiao.develop.freeofo;

import com.pgyersdk.crash.PgyCrashManager;

import org.greenrobot.eventbus.EventBus;

import c.b.BP;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.zhiao.baselib.app.BaseApplication;

/**
 * author：Administrator on 2017/4/12 10:42
 * company: xxxx
 * email：1032324589@qq.com
 */

public class App extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        initBmob();
        PgyCrashManager.register(this);
        EventBus.builder().throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();
        BP.init("25fbbe530d801ab95a2723acf03c50b6");
//        BmobUpdateAgent.initAppVersion();
        BmobUpdateAgent.setUpdateOnlyWifi(false);
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
//        Bmob.initialize(this, "25fbbe530d801ab95a2723acf03c50b6");
//        // 使用推送服务时的初始化操作
//        BmobInstallation.getCurrentInstallation().save();
//        // 启动推送服务
//        BmobPush.startWork(this);
    }
}
