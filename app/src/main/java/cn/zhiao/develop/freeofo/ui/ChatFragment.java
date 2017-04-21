package cn.zhiao.develop.freeofo.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.activity.LCIMConversationListFragment;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.zhiao.baselib.base.BaseFragment;
import cn.zhiao.develop.freeofo.MainActivity;
import cn.zhiao.develop.freeofo.R;

/**
 * author：Administrator on 2017/4/13 09:08
 * company: xxxx
 * email：1032324589@qq.com
 */
public class ChatFragment extends BaseFragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager viewPager;

    @Override
    public int getLayoutRes() {
        return R.layout.frg_chat;
    }

    @Override
    public void initView() {
        initTabLayout();
    }

    @Override
    protected void initPresenter() {
    }

    private void initTabLayout() {
        String[] tabList = new String[]{"会话", "联系人"};
        final Fragment[] fragmentList = new Fragment[]{new LCIMConversationListFragment(),
                new ContactFragment()};

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabList.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList[i]));
        }

        TabFragmentAdapter adapter = new TabFragmentAdapter(getChildFragmentManager(),
                Arrays.asList(fragmentList), Arrays.asList(tabList));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (0 == position) {
//          EventBus.getDefault().post(new ConversationFragmentUpdateEvent());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class TabFragmentAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

    private void gotoSquareConversation() {
        List<LCChatKitUser> userList = MainActivity.partUsers;
        List<String> idList = new ArrayList<>();
        for (LCChatKitUser user : userList) {
            idList.add(user.getUserId());
        }
        LCChatKit.getInstance().getClient().createConversation(
                idList, getString(R.string.square), null, false, true, new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation avimConversation, AVIMException e) {
                        Intent intent = new Intent(getContext(), LCIMConversationActivity.class);
                        intent.putExtra(LCIMConstants.CONVERSATION_ID, avimConversation.getConversationId());
                        startActivity(intent);
                    }
                });
    }
}
