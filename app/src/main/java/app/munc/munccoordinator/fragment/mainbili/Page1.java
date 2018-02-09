package app.munc.munccoordinator.fragment.mainbili;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.adapter.MyFragmentPagerAdapter;
import app.munc.munccoordinator.content.CommonContent;
import app.munc.munccoordinator.content.UrlBaseContent;
import app.munc.munccoordinator.content.UrlContent;
import app.munc.munccoordinator.fragment.mainbili.page1.AfterSomeFragment;
import app.munc.munccoordinator.fragment.mainbili.page1.LiveBroadcastFragment;
import app.munc.munccoordinator.fragment.mainbili.page1.MoviesFragment;
import app.munc.munccoordinator.fragment.mainbili.page1.RecommendFragment;
import app.munc.munccoordinator.fragment.mainbili.page1.SpecialColumnFragment;
import app.munc.munccoordinator.info.homepage.TopbarInfo;
import app.munc.munccoordinator.inter.ResponseCommonService;
import app.munc.munccoordinator.util.AppCompatUtils;
import app.munc.munccoordinator.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Page1 extends Fragment {


    @BindView(R.id.iv_topBarBuy)
    ImageView ivTopBarBuy;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.iv_topBarSearch)
    ImageView ivTopBarSearch;
    Unbinder unbinder;
    @BindView(R.id.ll_topBar)
    LinearLayout llTopBar;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private int width;
    private List<TopbarInfo.DataBean> data;
    private String[] tabTitle = {"直播", "推荐", "追番", "影视", "专栏"};
    private List<String> listT = new ArrayList<String>();//页卡标题集合
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>(); //页卡视图集合
    private MyFragmentPagerAdapter fragmentPagerAdapter;

    public Page1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page1, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        initLayout();
        initTab();
        initRetrofit();
    }

    private void initTab() {
        //设置下划线宽度
        Utils.setTabIndicator(mTabLayout, 15);

        // 初始化导航文字]
        for (int i = 0; i < tabTitle.length; i++) {
            listT.add(i, tabTitle[i]);
        }

        LiveBroadcastFragment liveBroadcastFragment = new LiveBroadcastFragment();
        RecommendFragment recommendFragment = new RecommendFragment();
        AfterSomeFragment afterSomeFragment = new AfterSomeFragment();
        MoviesFragment moviesFragment = new MoviesFragment();
        SpecialColumnFragment specialColumnFragment = new SpecialColumnFragment();
        fragments.add(liveBroadcastFragment);
        fragments.add(recommendFragment);
        fragments.add(afterSomeFragment);
        fragments.add(moviesFragment);
        fragments.add(specialColumnFragment);

        fragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragments, listT);
        mViewPager.setAdapter(fragmentPagerAdapter);//给ViewPager设置适配器  （viewpager和适配器关联）
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。 （标题和viewpager关联）
        mTabLayout.setTabsFromPagerAdapter(fragmentPagerAdapter);//给Tabs设置适配器  （标题和适配器关联）
        mViewPager.setCurrentItem(1);
    }

    private void initLayout() {
        width = AppCompatUtils.getAppScreenWidth(getActivity());
        AppCompatUtils.setScreenScale(llTopBar, width, 1, 11);
        AppCompatUtils.setScreenScale(ivTopBarBuy, width, 10, 10);
        AppCompatUtils.setScreenScale(ivTopBarSearch, width, 10, 10);
    }

    private void initRetrofit() {
        //do somethings........ RequestHttp  这里没有用接口数据
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlContent.commonApp + UrlBaseContent.TopBar)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        ResponseCommonService service = retrofit.create(ResponseCommonService.class);
        Call<TopbarInfo> call = service.getTopBar(CommonContent.actionKey, CommonContent.appkey, CommonContent.build, CommonContent.device,
                CommonContent.mobi_app, CommonContent.platform, Utils.getUUid(), CommonContent.ts);
        call.enqueue(new Callback<TopbarInfo>() {
            @Override
            public void onResponse(Call<TopbarInfo> call, Response<TopbarInfo> response) {
                try {
                    data = response.body().getData();
                } catch (Exception e) {
                    return;
                }
                initView();
            }

            @Override
            public void onFailure(Call<TopbarInfo> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    //初始化控件
    private void initView() {
        Glide.with(getActivity())
                .load(data.get(0).getLogo())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.bg_following_default_image_tv9)
                .error(R.drawable.bg_following_default_image_tv9).into(ivTopBarBuy);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_topBarBuy, R.id.iv_topBarSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_topBarBuy:
                break;
            case R.id.iv_topBarSearch:
                break;
        }
    }
}
