package lesson_menu.example.com.munccoordinator;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lesson_menu.example.com.munccoordinator.content.CommonContent;
import lesson_menu.example.com.munccoordinator.fragment.MyFragment;
import lesson_menu.example.com.munccoordinator.util.AppCompatUtils;
import lesson_menu.example.com.munccoordinator.util.Utils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    private String[] tabTitle = {"手机游戏", "综合", "鬼畜", "影视杂谈", "绘画", "番剧", "人文地理", "科技"};

    private List<String> listT = new ArrayList<String>();//页卡标题集合
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>(); //页卡视图集合
    private MyFragmentPagerAdapter fragmentPagerAdapter;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        width = AppCompatUtils.getAppScreenWidth(MainActivity.this);
        initDatas();
    }

    private void initDatas() {
        //do somethings........ RequestHttp
        init();
    }

    private void init() {
        AppCompatUtils.setScreenScale(rlTitle, width, 1, 8.18);
        AppCompatUtils.setAppStatus(MainActivity.this);

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为根据文本长度变化而改变
        //设置下划线宽度
        Utils.setTabIndicator(mTabLayout, 15);
        for (int i = 0; i < tabTitle.length; i++) {
            listT.add(i, tabTitle[i]);
        }

        for (int i = 0; i < tabTitle.length; i++) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt(CommonContent.INDEX, i);
            args.putStringArray(CommonContent.TITLE, new String[]{tabTitle[i]});
            myFragment.setArguments(args);
            fragments.add(myFragment);
        }
        fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, listT);
        mViewPager.setAdapter(fragmentPagerAdapter);//给ViewPager设置适配器  （viewpager和适配器关联）
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。 （标题和viewpager关联）
        mTabLayout.setTabsFromPagerAdapter(fragmentPagerAdapter);//给Tabs设置适配器  （标题和适配器关联）
        mViewPager.setCurrentItem(0);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private FragmentManager fm;
        private final List<Fragment> list;
        private final List<String> titles;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, List<String> listT) {
            super(fm);
            this.fm = fm;
            this.list = fragments;
            this.titles = listT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listT.get(position);
        }

        @Override
        public int getCount() {
            return listT.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }
}
