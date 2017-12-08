package app.munc.munccoordinator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.content.UrlBaseContent;
import app.munc.munccoordinator.content.UrlContent;
import app.munc.munccoordinator.info.AnimInfo;
import app.munc.munccoordinator.inter.ResponseCommonService;
import app.munc.munccoordinator.util.AppCompatUtils;
import app.munc.munccoordinator.view.SliderLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FininAnim extends Fragment {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.ll_inSide)
    LinearLayout llInSide;
    @BindView(R.id.sliderLayout)
    SliderLayout mSliderLayout;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.frameLayout)
    FrameLayout mFrameLayout;
    Unbinder unbinder;
    private String[] tabTitle = {"", "", "", "", ""}; //这里写死5个模块取接口的前5个owner字段name
    private List<String> tabTop = new ArrayList<>();
    private List<String> tabDown = new ArrayList<>();
    private View view;
    private int width;
    private List<AnimInfo.DataBean.ArchivesBean> archives;
    private String[] tabTitleTop;
    private String[] tabTitleDown;
    private List<String> listT = new ArrayList<>();
    private MyFragmentPagerAdapter fragmentPagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>(); //页卡视图集合

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_finin_anim, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        width = AppCompatUtils.getAppScreenWidth(getActivity());
        init();
    }

    private void init() {
        initDatas();
        initLayout();
    }

    private void initDatas() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlContent.common + UrlBaseContent.Anim)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        ResponseCommonService service = retrofit.create(ResponseCommonService.class);
        Call<AnimInfo> call = service.regionGet("json", "20", "32", "subListCallback_1");
        call.enqueue(new Callback<AnimInfo>() {
            @Override
            public void onResponse(Call<AnimInfo> call, Response<AnimInfo> response) {
                archives = response.body().getData().getArchives();
                List<String> listTop = new ArrayList<String>();  //标题 上

                List<String> listDown = new ArrayList<String>();  //标题 下

                for (int i = 0; i < 5; i++) {
                    String mid = String.valueOf(archives.get(i).getOwner().getMid());
                    String name = archives.get(i).getOwner().getName();
                    listTop.add(mid);
                    listDown.add(name);
                }
                tabTitleTop = listTop.toArray(new String[]{});

                tabTitleDown = listDown.toArray(new String[]{});
                initSet();
            }


            @Override
            public void onFailure(Call<AnimInfo> call, Throwable t) {

            }
        });
    }

    private void initSet() {
        //上层标题
        for (int i = 0; i < tabTitleTop.length; i++) {
            tabTop.add(i, tabTitleTop[i]);
        }

        //下层标题
        for (int i = 0; i < tabTitleDown.length; i++) {
            tabDown.add(i, tabTitleDown[i]);
        }
        mSliderLayout.setSliderImage(getResources().getDrawable(R.drawable.small_sharp_angle)); //这里直接引用UI的图片
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为根据文本长度变化而改变
        // 初始化导航文字
        for (int i = 0; i < tabTitle.length; i++) {
            listT.add(i, tabTitle[i]);
        }

        for (int i = 0; i < tabTitle.length; i++) {
            FinishFrag brandSpecial = new FinishFrag(archives);
 /*           Bundle args = new Bundle();
            //实际开发中：这里传一些每个viewpager需要请求的特殊参数 这里暂时就不传了 直接全部用接口的数据
            args.putStringArray(CommonContent.BRAND_GROUP, new String[]{tabTitle_ID[i]});
            brandSpecial.setArguments(args);*/
            fragments.add(brandSpecial);
        }
        fragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragments, listT);
        mViewPager.setAdapter(fragmentPagerAdapter);//给ViewPager设置适配器  （viewpager和适配器关联）
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。 （标题和viewpager关联）
        mTabLayout.setTabsFromPagerAdapter(fragmentPagerAdapter);//给Tabs设置适配器  （标题和适配器关联）
        mViewPager.setCurrentItem(0);
        mViewPager.clearOnPageChangeListeners();
        mViewPager.addOnPageChangeListener(new SliderLayout.SliderOnPageChangeListener(mTabLayout, mSliderLayout));
        //设置自定义tabLayout
        setupTabIcons();
    }

    private void setupTabIcons() {
        for (int i = 0; i < tabTitle.length; i++) {
            mTabLayout.getTabAt(i).setCustomView(setTabCustomStyle(i));
        }
    }

    private View setTabCustomStyle(int position) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_item, null);
        TextView tabTime = (TextView) view.findViewById(R.id.tabTime);
        tabTime.setText(tabTop.get(position));
        TextView tabStatus = (TextView) view.findViewById(R.id.tabStatus);
        tabStatus.setText(tabDown.get(position));
        return view;
    }

    private void initLayout() {
        //mSliderLayout 高度60pt
        AppCompatUtils.setScreenScale(mSliderLayout, width, 1, 6.25);
        AppCompatUtils.setScreenScale(mTabLayout, width, 1, 6.81818182); //tab高度 55pt
        ViewGroup.MarginLayoutParams layoutParams_mViewPager = (ViewGroup.MarginLayoutParams) llInSide.getLayoutParams();
        layoutParams_mViewPager.setMargins(0, (int) (width / 6.81818182), 0, 0);
        llInSide.setLayoutParams(layoutParams_mViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
