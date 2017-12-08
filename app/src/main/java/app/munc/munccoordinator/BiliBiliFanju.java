package app.munc.munccoordinator;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.GsonBuilder;

import java.util.List;

import app.munc.munccoordinator.content.UrlBaseContent;
import app.munc.munccoordinator.content.UrlContent;
import app.munc.munccoordinator.fragment.FininAnim;
import app.munc.munccoordinator.fragment.SerialAnim;
import app.munc.munccoordinator.info.AnimInfo;
import app.munc.munccoordinator.inter.ResponseCommonService;
import app.munc.munccoordinator.util.AppCompatUtils;
import app.munc.munccoordinator.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BiliBiliFanju extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title_home)
    TextView tvTitleHome;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.ll_outside_tab)
    LinearLayout llOutsideTab;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @BindView(R.id.brand_frameLayout)
    FrameLayout brandFrameLayout;
    private int width;
    private String[] tabTitle = {"完结动画", "连载动画"};
    private FragmentTransaction fragmentTransaction;
    private List<AnimInfo.DataBean.ArchivesBean> archives;
    private FininAnim fininAnim = null;
    private SerialAnim serialAnim = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bili_bili_fanju);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        width = AppCompatUtils.getAppScreenWidth(BiliBiliFanju.this);
        AppCompatUtils.setAppStatus(BiliBiliFanju.this);
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
        retrofit2.Call<AnimInfo> call = service.regionGet("json", "20", "32", "subListCallback_1");
        call.enqueue(new Callback<AnimInfo>() {
            @Override
            public void onResponse(retrofit2.Call<AnimInfo> call, retrofit2.Response<AnimInfo> response) {
                String tname = response.body().getData().getArchives().get(0).getTname();
                Utils.showToast(BiliBiliFanju.this, tname);
                archives = response.body().getData().getArchives();
                Log.e("archives", archives.get(0).getPic());
                Glide.with(BiliBiliFanju.this)
                        .load(archives.get(0).getPic())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(ivBanner);
                initSet();
            }

            @Override
            public void onFailure(retrofit2.Call<AnimInfo> call, Throwable t) {

            }
        });
    }


    //适配
    private void initLayout() {
        AppCompatUtils.setScreenScale(rlTitle, width, 1, 8.18);
        AppCompatUtils.setScreenScale(llOutsideTab, width, 1, 8.3333333);
        AppCompatUtils.setScreenScale(ivBanner, width, 1, 2.5);
    }

    //监听
    private void initSet() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为根据文本长度变化而改变
        //设置下划线宽度
        Utils.setTabIndicator(mTabLayout, 65);
        // 初始化导航文字]
        for (int i = 0; i < tabTitle.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[i]));

        }
        mTabLayout.getTabAt(0).select();
        selectFragment(0);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d(getClass().getName(), position + "");
                selectFragment(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void selectFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments();
        switch (position) {
            case 0:
                if (fininAnim == null) {
                    fininAnim = new FininAnim();
                    this.fragmentTransaction.add(R.id.brand_frameLayout, fininAnim);
                }
                this.fragmentTransaction.show(fininAnim);
                break;
            case 1:
                if (serialAnim == null) {
                    serialAnim = new SerialAnim();
                    this.fragmentTransaction.add(R.id.brand_frameLayout, serialAnim);
                }
                this.fragmentTransaction.show(serialAnim);
                break;
            default:
                break;

        }
        this.fragmentTransaction.commitAllowingStateLoss();
    }

    private void hideFragments() {
        if (fininAnim != null) {
            fragmentTransaction.hide(fininAnim);
        }
        if (serialAnim != null) {
            fragmentTransaction.hide(serialAnim);
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
