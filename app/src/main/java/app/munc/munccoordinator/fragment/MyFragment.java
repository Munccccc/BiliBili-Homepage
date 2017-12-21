package app.munc.munccoordinator.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.GsonBuilder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.adapter.MuncAdapter;
import app.munc.munccoordinator.content.CommonContent;
import app.munc.munccoordinator.content.UrlBaseContent;
import app.munc.munccoordinator.content.UrlContent;
import app.munc.munccoordinator.info.BiliBiliFanjuInfo;
import app.munc.munccoordinator.inter.ResponseCommonService;
import app.munc.munccoordinator.util.AppCompatUtils;
import app.munc.munccoordinator.util.Utils;
import app.munc.munccoordinator.view.RoundImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//第一个界面的数据
public class MyFragment extends Fragment {

    private static final int LOAD_DATA = 200;
    private static final int SPAN_COUNT_ONE = 2;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRcyclerview;
    @BindView(R.id.iv_go_top_home)
    RoundImageView ivGoTopHome;
    @BindView(R.id.twinklingRefreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    private int width;
    private int position;
    private List<BiliBiliFanjuInfo.ResultBean> addfanjuresult; //新增集合
    private boolean isExecuteRefresh = false;
    private boolean isExecuteLoadMore = false;

    //内部的子tab
    private String[] insideTab;
    private List<String> listT = new ArrayList<String>();//页卡标题集合

    private List addlist = new ArrayList<>();//增加的集合  模拟
    private List<BiliBiliFanjuInfo.ResultBean> fanjuresult;
    private String[] tabTopImgUrl = {"http://i0.hdslb.com/bfs/archive/b2fdeaebbc93faff9c35a63601d650dbf7a9a516.jpg",
            "http://i0.hdslb.com/bfs/archive/ddb9377d9d896ff6b8ee7e71f5227fe734afcae4.jpg",
            "http://i0.hdslb.com/bfs/archive/ad9616fe62d919d5535f127fb8319063224f07c0.jpg",
            "http://i0.hdslb.com/bfs/archive/55f7819c8d08f12d5c494e714f487dffc09a9e8a.jpg",
            "http://i0.hdslb.com/bfs/archive/1986059a4254ded8053989c2be2faba885f5981f.jpg",
            "http://i0.hdslb.com/bfs/archive/600b1e1ea3718895e1cf792af0da256e3b8dd1cb.jpg",
            "http://i1.hdslb.com/bfs/archive/249278bacdaec81399d9cba5d283c139e75d4bab.jpg",
            "http://i0.hdslb.com/bfs/archive/208bfcc3df2e72bfcfa616d38ae2137988eebc96.jpg"};
    private String mTitle;
    private int totalDy;
    private MuncAdapter muncAdapter;
    private List mList = new ArrayList();
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_DATA:
                    if (isExecuteRefresh) {
                        muncAdapter.setDatas(fanjuresult);
                        mRefreshLayout.finishRefreshing();
                        isExecuteRefresh = false;
                    } else if (isExecuteLoadMore) {
                        muncAdapter.addData(addfanjuresult);
                        mRefreshLayout.finishLoadmore();
                        isExecuteLoadMore = false;
                        if (addfanjuresult.size() == 0) {
                            Utils.showToast(getActivity(), getString(R.string.loadnomore));
                            return;
                        }
                    }
                    if (fanjuresult.size() == 0) {
                        totalDy = 0;
                    }
                    break;


            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String[] titleAndArgm = bundle.getStringArray(CommonContent.TITLE);
        position = bundle.getInt(CommonContent.INDEX);  //这里其实请求服务器直接设置数据就好  position做简单的模拟服务器数据
        mTitle = titleAndArgm[0];//全部
        width = AppCompatUtils.getAppScreenWidth(getActivity());
        init();
    }

    private void init() {
        //do somethings........ RequestHttp
        initRetrofit();

    }

    private void initRetrofit() {
        //这里一共53条
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlContent.commonFj + UrlBaseContent.Fj)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        ResponseCommonService service = retrofit.create(ResponseCommonService.class);
        Call<BiliBiliFanjuInfo> call = service.getfanju();
        call.enqueue(new Callback<BiliBiliFanjuInfo>() {
            @Override
            public void onResponse(Call<BiliBiliFanjuInfo> call, Response<BiliBiliFanjuInfo> response) {
                fanjuresult = response.body().getResult();
                Log.e("bilibili", response.body().toString());
                initSet();
            }

            @Override
            public void onFailure(Call<BiliBiliFanjuInfo> call, Throwable t) {
                Log.e("bilibili", t.getMessage());
            }
        });
    }

    private void initSet() {
        AppCompatUtils.setScreenScale(ivDetail, width, 1, 2);
        ProgressLayout headerView = new ProgressLayout(getActivity());
        headerView.setColorSchemeColors(Color.parseColor("#FF4500"));
        mRefreshLayout.setTargetView(mRcyclerview);  //拿到recyclerview
        mRefreshLayout.setHeaderView(headerView);
        mRefreshLayout.setFloatRefresh(true);
        mRefreshLayout.setEnableOverScroll(true);
        mRefreshLayout.setHeaderHeight(140);
        mRefreshLayout.setMaxHeadHeight(240);
        mRefreshLayout.setAutoLoadMore(true);

        //---------------------appBarLayout设置监听---------------------
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mRefreshLayout.setEnableRefresh(true);
                    mRefreshLayout.setEnableOverScroll(false);
                } else {
                    mRefreshLayout.setEnableRefresh(false);
                    mRefreshLayout.setEnableOverScroll(false);
                }
            }
        });
        //---------------------mRefreshLayout设置监听---------------------
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //这里略过 没服务器>...<
            /*            pageIndex = 1;
                        isExecuteRefresh = true;
                        resitDataLists();
                        initOKhttp(tabTitle[tabIndex], mTitle);*/
                        isExecuteRefresh = true;
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(UrlContent.commonFj + UrlBaseContent.Fj)
                                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                                .build();
                        ResponseCommonService service = retrofit.create(ResponseCommonService.class);
                        Call<BiliBiliFanjuInfo> call = service.getfanju();
                        call.enqueue(new Callback<BiliBiliFanjuInfo>() {
                            @Override
                            public void onResponse(Call<BiliBiliFanjuInfo> call, Response<BiliBiliFanjuInfo> response) {
                                fanjuresult = response.body().getResult();
                                Log.e("bilibili", response.body().toString());
                                mhandler.obtainMessage(LOAD_DATA).sendToTarget();
                            }

                            @Override
                            public void onFailure(Call<BiliBiliFanjuInfo> call, Throwable t) {
                                Log.e("bilibili", t.getMessage());
                            }
                        });

                    }
                }, 1200);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //这里略过 没服务器>...<
                     /*   pageIndex++;
                        isExecuteLoadMore = true;
                        initOKhttp(tabTitle[tabIndex], mTitle);*/
                        isExecuteLoadMore = true;
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(UrlContent.commonFj + UrlBaseContent.Fj)
                                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                                .build();
                        ResponseCommonService service = retrofit.create(ResponseCommonService.class);
                        Call<BiliBiliFanjuInfo> call = service.getfanju();
                        call.enqueue(new Callback<BiliBiliFanjuInfo>() {
                            @Override
                            public void onResponse(Call<BiliBiliFanjuInfo> call, Response<BiliBiliFanjuInfo> response) {
                                addfanjuresult = response.body().getResult();
                                Log.e("bilibili", response.body().toString());
                                mhandler.obtainMessage(LOAD_DATA).sendToTarget();
                            }

                            @Override
                            public void onFailure(Call<BiliBiliFanjuInfo> call, Throwable t) {
                                Log.e("bilibili", t.getMessage());
                            }
                        });

                    }
                }, 300);
            }
        });
        //---------------------mRcyclerview设置监听---------------------
        mRcyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy = totalDy + dy;
                if (totalDy > 1000) {
                    ivGoTopHome.setVisibility(View.VISIBLE);
                } else {
                    ivGoTopHome.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        //设置rc
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), SPAN_COUNT_ONE);
        mRcyclerview.setLayoutManager(manager);
        muncAdapter = new MuncAdapter(getActivity(), fanjuresult);
        mRcyclerview.setAdapter(muncAdapter);
        muncAdapter.addData(fanjuresult);
        //设置一个头 头部占一行
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 7 == 0) {
                    return 2;
                }
                //头部是0 传进去为真 返回SPAN_COUNT_ONE的宽度的宽度 当头部大于等于1 为假 返回1/SPAN_COUNT_ONE的宽度
                return (muncAdapter.isHeaderView(position)) ? manager.getSpanCount() : 1;
            }
        });
        Glide.with(getActivity().getApplicationContext())
                .load(tabTopImgUrl[position])
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.img_column_no_comments)
                .error(R.drawable.img_column_no_comments).into(ivDetail);
        //设置tab模拟数据
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为根据文本长度变化而改变
        //设置下划线宽度
        Utils.setTabIndicator(mTabLayout, 15, 15);
        switch (position) {
            case 0:
                insideTab = new String[]{"碧蓝航线", "野蛮人大作战", "我一点都不可口"};
                break;
            case 1:
                insideTab = new String[]{"娱乐", "时尚", "广告"};
                break;
            case 2:
                insideTab = new String[]{"诸葛村夫", "王司徒", "德国Boy"};
                break;
            case 3:
                insideTab = new String[]{"雷神3", "复仇者联盟", "这个杀手不太冷"};
                break;
            case 4:
                insideTab = new String[]{"《蒙娜·丽莎》", "《最后的晚餐》", "《向日葵》"};
                break;
            case 5:
                insideTab = new String[]{"【合集】来自风平浪静的明天", "【BD1080P/剧场版/英配】精灵宝可梦 就决定是你了【自制字幕】", "【720P/BDrip】天然萌少女明日香/今天的明日香【生肉】"};
                break;
            case 6:
                insideTab = new String[]{"人工智能", "缓解压力的小方法", "手办"};
                break;
            case 7:
                insideTab = new String[]{"JavaEE", "Android", "IOS"};
                break;
            default:
                break;
        }
        // 初始化导航文字]
        for (int i = 0; i < insideTab.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(insideTab[i]));
            listT.add(i, insideTab[i]);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_detail, R.id.iv_go_top_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_detail:
                break;
            case R.id.iv_go_top_home:
                break;
        }
    }

}
