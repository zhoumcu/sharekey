package cn.zhiao.develop.freeofo.bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by ymn on 2017/4/15.
 */
public class Notify implements Serializable{

    /**
     * alert : 是的发送到发送到
     */

    private String alert;

    public static Notify objectFromData(String str) {

        return new Gson().fromJson(str, Notify.class);
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }
}
