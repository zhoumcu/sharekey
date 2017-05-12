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
    private String lockerName = "普通";
    private String lockerId = "普通";

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    private String photoUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493207862074&di=ffca420e19ef344cbdb560ae86fd21d8&imgtype=0&src=http%3A%2F%2Fwww.lgstatic.com%2Fthumbnail_300x300%2Fimage1%2FM00%2F0C%2F93%2FCgo8PFT1mgGAch5LAABMj6Js2g8029.jpg";
}
