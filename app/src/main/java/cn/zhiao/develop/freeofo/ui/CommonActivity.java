package cn.zhiao.develop.freeofo.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.develop.freeofo.R;

/**
 * author：Administrator on 2017/4/21 14:04
 * company: xxxx
 * email：1032324589@qq.com
 */

public class CommonActivity extends BaseActivity {
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager viewPager;

    @Override
    public void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        initTabLayout();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.frg_chat;
    }

    private void initTabLayout() {
        String[] tabList = new String[]{"会话", "开锁王"};
        final Fragment[] fragmentList = new Fragment[] {new EaseConversationListFragment(),new EaseContactListFragment()};

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabList.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList[i]));
        }

        TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager(),
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.square_menu,menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.menu_square:
//                gotoSquareConversation();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
