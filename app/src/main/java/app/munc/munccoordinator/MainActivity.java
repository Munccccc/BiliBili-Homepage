package app.munc.munccoordinator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import app.munc.munccoordinator.adapter.MuncDrawerAdapter;
import app.munc.munccoordinator.content.CommonContent;
import app.munc.munccoordinator.content.UrlBaseContent;
import app.munc.munccoordinator.content.UrlContent;
import app.munc.munccoordinator.fragment.MyFragment;
import app.munc.munccoordinator.info.RankingInfo;
import app.munc.munccoordinator.inter.ResponseCommonService;
import app.munc.munccoordinator.util.AppCompatUtils;
import app.munc.munccoordinator.util.Utils;
import app.munc.munccoordinator.view.RoundImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int SPAN_COUNT_ONE = 1;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_sx)
    RoundImageView tvSx;
    @BindView(R.id.rc_drawer)
    RecyclerView rcDrawer;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.goNext)
    TextView goNext;

    private String[] tabTitle = {"手机游戏", "综合", "鬼畜", "影视杂谈", "绘画", "番剧", "人文地理", "科技"};

    private List<String> listT = new ArrayList<String>();//页卡标题集合
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>(); //页卡视图集合
    private MyFragmentPagerAdapter fragmentPagerAdapter;
    private int width;
    private MuncDrawerAdapter muncDrawerAdapter;
    private List mList = new ArrayList();
    private List<RankingInfo.DataBean> rankingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatUtils.setAppStatus(MainActivity.this);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        width = AppCompatUtils.getAppScreenWidth(MainActivity.this);
        initDatas();
    }

    private void initDatas() {
        //测试
        AppCompatUtils.setScreenScale(rlTitle, width, 1, 8.18);
        initRetrofit();
    }

    private void initRetrofit() {
        //do somethings........ RequestHttp  这里没有用接口数据
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlContent.common + UrlBaseContent.Indexday3)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        ResponseCommonService service = retrofit.create(ResponseCommonService.class);
        Call<RankingInfo> call = service.rankingGet("3");
        call.enqueue(new Callback<RankingInfo>() {
            @Override
            public void onResponse(Call<RankingInfo> call, Response<RankingInfo> response) {
                rankingList = response.body().getData();
                String description = rankingList.get(0).getDescription();
                Utils.showToast(MainActivity.this, description);
                init();
            }

            @Override
            public void onFailure(Call<RankingInfo> call, Throwable t) {

            }
        });
    }

    private void init() {
        final GridLayoutManager manager = new GridLayoutManager(MainActivity.this, SPAN_COUNT_ONE);
        muncDrawerAdapter = new MuncDrawerAdapter(MainActivity.this, mList);
        rcDrawer.setLayoutManager(manager);
        rcDrawer.setAdapter(muncDrawerAdapter);
        muncDrawerAdapter.addData(rankingList);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (muncDrawerAdapter.isHeaderView(position)) ? manager.getSpanCount() : 1;
            }
        });

//      mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //禁止drawerLayout滑动

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

    @OnClick({R.id.goNext, R.id.tv_sx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goNext:
                Intent intent = new Intent(MainActivity.this, BiliBiliFanju.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.tv_sx:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
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
