package cn.zhiao.develop.freeofo.bean;

import cn.bmob.v3.BmobUser;

/**
 * author：Administrator on 2017/4/12 14:28
 * company: xxxx
 * email：1032324589@qq.com
 */
public class User extends BmobUser {
    public boolean isLocker() {
        return isLocker;
    }

    public void setLocker(boolean locker) {
        isLocker = locker;
    }

    public String getLockerName() {
        return lockerName;
    }

    public void setLockerName(String lockerName) {
        this.lockerName = lockerName;
    }

    public String getLockerId() {
        return lockerId;
    }

    public void setLockerId(String lockerId) {
        this.lockerId = lockerId;
    }

    private boolean isLocker = false;
    private String lockerName = "开锁王";
    private String lockerId = "开锁王";

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    private String photoUrl = "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg";
}
