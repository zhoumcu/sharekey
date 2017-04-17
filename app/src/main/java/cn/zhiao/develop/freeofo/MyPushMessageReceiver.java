package cn.zhiao.develop.freeofo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.push.PushConstants;
import cn.zhiao.baselib.utils.SharedPrefrecesUtils;
import cn.zhiao.develop.freeofo.bean.Constants;
import cn.zhiao.develop.freeofo.bean.MessageEvent;
import cn.zhiao.develop.freeofo.bean.Notify;

/**
 * Created by ymn on 2017/4/15.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
            SharedPrefrecesUtils.saveStrToSharedPrefrences(Constants.MSG,Notify.objectFromData(intent.getStringExtra("msg")).getAlert(),context);
            MessageEvent event = new MessageEvent();
            event.setAlert(Notify.objectFromData(intent.getStringExtra("msg")).getAlert());
            EventBus.getDefault().post(event);
        }
    }
}