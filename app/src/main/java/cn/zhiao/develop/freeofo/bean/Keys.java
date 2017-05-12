package cn.zhiao.develop.freeofo.bean;

import cn.bmob.v3.BmobObject;

/**
 * author：Administrator on 2017/4/13 10:18
 * company: xxxx
 * email：1032324589@qq.com
 */

public class Keys extends BmobObject{
    private String keyName;
    private String keyNumber;
    private String userId;
    private int rightNum = 1;

    public int getWrongNum() {
        return wrongNum;
    }

    public void setWrongNum(int wrongNum) {
        this.wrongNum = wrongNum;
    }

    public int getRightNum() {
        return rightNum;
    }

    public void setRightNum(int rightNum) {
        this.rightNum = rightNum;
    }

    private int wrongNum = 0;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(String keyNumber) {
        this.keyNumber = keyNumber;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
