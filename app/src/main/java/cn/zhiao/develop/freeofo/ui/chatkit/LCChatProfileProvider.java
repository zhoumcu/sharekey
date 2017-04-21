package cn.zhiao.develop.freeofo.ui.chatkit;

import java.util.List;

import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * author：Administrator on 2017/4/21 10:03
 * company: xxxx
 * email：1032324589@qq.com
 */

public interface LCChatProfileProvider {
    // 根据传入的 clientId list，查找、返回用户的 Profile 信息(id、昵称、头像)
    public void fetchProfiles(List<String> userIdList, LCChatProfilesCallBack profilesCallBack);
}
