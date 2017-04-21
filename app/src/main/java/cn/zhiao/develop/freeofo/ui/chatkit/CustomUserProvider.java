package cn.zhiao.develop.freeofo.ui.chatkit;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfilesCallBack;
import cn.zhiao.develop.freeofo.bean.User;

/**
 * author：Administrator on 2017/4/21 10:04
 * company: xxxx
 * email：1032324589@qq.com
 */
public class CustomUserProvider implements LCChatProfileProvider {

    private static CustomUserProvider customUserProvider;
    private User user;

    public synchronized static CustomUserProvider getInstance(Context context) {
        if (null == customUserProvider) {
            customUserProvider = new CustomUserProvider(context);
        }
        return customUserProvider;
    }

    private CustomUserProvider(Context context) {
//        user = (User) SharedPrefrecesUtils.readObject(context, "user");
//        if(user.isLocker()){
//            partUsers.add(new LCChatKitUser(user.getLockerId(), user.getLockerName(), user.getPhotoUrl()));
//        }
    }

    private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();

    // 此数据均为模拟数据，仅供参考
//    static {
//        partUsers.add(new LCChatKitUser("Tom", "开锁王1", "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"));
//        partUsers.add(new LCChatKitUser("Jerry", "开锁王2", "http://www.avatarsdb.com/avatars/jerry.jpg"));
//        partUsers.add(new LCChatKitUser("Harry", "开锁王3", "http://www.avatarsdb.com/avatars/young_harry.jpg"));
//        partUsers.add(new LCChatKitUser("William", "开锁王4", "http://www.avatarsdb.com/avatars/william_shakespeare.jpg"));
//        partUsers.add(new LCChatKitUser("Bob", "开锁王5", "http://www.avatarsdb.com/avatars/bath_bob.jpg"));
//    }

    @Override
    public void fetchProfiles(List<String> list, LCChatProfilesCallBack callBack) {
        List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
        for (String userId : list) {
            for (LCChatKitUser user : partUsers) {
                if (user.getUserId().equals(userId)) {
                    userList.add(user);
                    break;
                }
            }
        }
        callBack.done(userList, null);
    }

    public List<LCChatKitUser> getAllUsers() {
        return partUsers;
    }
}