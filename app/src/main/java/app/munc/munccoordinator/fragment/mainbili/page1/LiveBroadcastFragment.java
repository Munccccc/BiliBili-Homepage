package app.munc.munccoordinator.fragment.mainbili.page1;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.GsonBuilder;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.activity.LiveBroadcastVideoAct;
import app.munc.munccoordinator.adapter.homepage.LiveBroadcastAdapter;
import app.munc.munccoordinator.content.CommonContent;
import app.munc.munccoordinator.content.UrlBaseContent;
import app.munc.munccoordinator.content.UrlContent;
import app.munc.munccoordinator.info.homepage.AppUserInfo;
import app.munc.munccoordinator.info.homepage.VideoColumnEntity;
import app.munc.munccoordinator.inter.ResponseCommonService;
import app.munc.munccoordinator.manager.GlideImageLoader;
import app.munc.munccoordinator.util.AppCompatUtils;
import app.munc.munccoordinator.view.BilibiliRefreshView;
import app.munc.munccoordinator.view.MyGridView;
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

/**
 * A simple {@link Fragment} subclass. 直播模块
 */
public class LiveBroadcastFragment extends Fragment {


    private static final int SPAN_COUNT_ONE = 1;
    private static final int LOAD_BANNER_SUCESS = 2;
    private static final int LOAD_CHANNEL_SUCESS = 3;
    private static final int LOAD_RECOMMENDED_ANCHORS_SUCESS = 4;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    Unbinder unbinder;
    @BindView(R.id.twinklingRefreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @BindView(R.id.iv_goTop)
    ImageView ivGoTop;
    private int totalDy;
    private int width;
    private List listT = new ArrayList();
    private boolean isExecuteRefresh = false;
    private LiveBroadcastAdapter mAdapter;
    private RelativeLayout rlBanner;
    private Banner mBanner;


    private List<String> banner_images = new ArrayList<>();
    private ImageView iv_channel2, iv_channel3, iv_channel4, iv_channel5, iv_recommend_column;
    private RoundedImageView iv_centerBanner;
    private TextView tv_channel2, tv_channel3, tv_channel4, tv_channel5, tv_anchor_count, tv_recommend_column;
    private MyGridView recommendGridView1, recommendGridView2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_BANNER_SUCESS:
                    setBanners();
                    break;
                case LOAD_CHANNEL_SUCESS:
                    if (partitions.size() != 4) {
                        return;
                    }
                    setChannel();
                    break;
                case LOAD_RECOMMENDED_ANCHORS_SUCESS:
                    setRecommendedAnchors();
                    if (isExecuteRefresh) {
                        twinklingRefreshLayout.finishLoadmore();
                        mAdapter.setDatas(lives);
                        isExecuteRefresh = false;
                    }
                    break;
            }
        }
    };
    private AppUserInfo.DataBean.RecommendDataBean recommend_data;
    private List<VideoColumnEntity> modulelist = new ArrayList<VideoColumnEntity>();
    private List<AppUserInfo.DataBean.RecommendDataBean.LivesBean> lives;
    private List<VideoColumnEntity> modulelist2 = new ArrayList<VideoColumnEntity>();

    //handler3 .推荐zb
    private void setRecommendedAnchors() {
        tv_anchor_count.setText("当前有" + recommend_data.getPartition().getCount() + "个主播,进去看看");
        //不做轮播了 默认取第一张
        Glide.with(getActivity().getApplicationContext())
                .load(recommend_data.getBanner_data().get(0).getCover().getSrc())
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.bg_following_default_image_tv9)
                .error(R.drawable.bg_following_default_image_tv9).into(iv_centerBanner);

        Glide.with(getActivity().getApplicationContext())
                .load(recommend_data.getPartition().getSub_icon().getSrc())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.bg_following_default_image_tv9)
                .error(R.drawable.bg_following_default_image_tv9).into(iv_recommend_column);
        tv_recommend_column.setText(recommend_data.getPartition().getName());

        for (int i = 0; i < recommend_data.getLives().size() / 2; i++) {
            VideoColumnEntity videoColumnEntity = new VideoColumnEntity();
            videoColumnEntity.setVideoCapture(recommend_data.getLives().get(i).getCover().getSrc());//图片
            videoColumnEntity.setVideoTitle(recommend_data.getLives().get(i).getTitle());//标题
            videoColumnEntity.setVideoUpPerson(recommend_data.getLives().get(i).getOwner().getName());//UP
            videoColumnEntity.setVideoCategory(recommend_data.getLives().get(i).getArea_v2_name());//类型
            videoColumnEntity.setVideoViewingNumber(recommend_data.getLives().get(i).getOnline() + "");//人数
            modulelist.add(videoColumnEntity);
        }
        for (int i = recommend_data.getLives().size() / 2; i < recommend_data.getLives().size(); i++) {
            VideoColumnEntity videoColumnEntity = new VideoColumnEntity();
            videoColumnEntity.setVideoCapture(recommend_data.getLives().get(i).getCover().getSrc());
            videoColumnEntity.setVideoTitle(recommend_data.getLives().get(i).getTitle());
            videoColumnEntity.setVideoUpPerson(recommend_data.getLives().get(i).getOwner().getName());
            videoColumnEntity.setVideoCategory(recommend_data.getLives().get(i).getArea_v2_name());//类型
            videoColumnEntity.setVideoViewingNumber(recommend_data.getLives().get(i).getOnline() + "");//人数
            modulelist2.add(videoColumnEntity);
        }

        recommendGridView1.setAdapter(new QuickAdapter<VideoColumnEntity>(getContext(), R.layout.item_video_style, modulelist) {

            @Override
            protected void convert(BaseAdapterHelper baseAdapterHelper, VideoColumnEntity inviteMethodEntity) {
                RoundImageView iv_videoScreenshot = baseAdapterHelper.getView(R.id.iv_videoScreenshot);
                Glide.with(getActivity().getApplicationContext())
                        .load(inviteMethodEntity.getVideoCapture())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.bg_following_default_image_tv9)
                        .error(R.drawable.bg_following_default_image_tv9).into(iv_videoScreenshot);
                baseAdapterHelper.setText(R.id.live_title, inviteMethodEntity.getVideoTitle());
                baseAdapterHelper.setText(R.id.tv_videoTitle, inviteMethodEntity.getVideoUpPerson());
                baseAdapterHelper.setText(R.id.tv_liveCategory, inviteMethodEntity.getVideoCategory());
                baseAdapterHelper.setText(R.id.tv_liveOnline, inviteMethodEntity.getVideoViewingNumber());


            }
        });

        recommendGridView2.setAdapter(new QuickAdapter<VideoColumnEntity>(getContext(), R.layout.item_video_style, modulelist2) {

            @Override
            protected void convert(BaseAdapterHelper baseAdapterHelper, VideoColumnEntity inviteMethodEntity) {
                RoundImageView iv_videoScreenshot = baseAdapterHelper.getView(R.id.iv_videoScreenshot);
                Glide.with(getActivity().getApplicationContext())
                        .load(inviteMethodEntity.getVideoCapture())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.bg_following_default_image_tv9)
                        .error(R.drawable.bg_following_default_image_tv9).into(iv_videoScreenshot);
                baseAdapterHelper.setText(R.id.live_title, inviteMethodEntity.getVideoTitle());
                baseAdapterHelper.setText(R.id.tv_videoTitle, inviteMethodEntity.getVideoUpPerson());
                baseAdapterHelper.setText(R.id.tv_liveCategory, inviteMethodEntity.getVideoCategory());
                baseAdapterHelper.setText(R.id.tv_liveOnline, inviteMethodEntity.getVideoViewingNumber());
            }
        });
        recommendGridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), LiveBroadcastVideoAct.class);
                intent.putExtra(CommonContent.BiliFlvUrl, recommend_data.getLives().get(i).getPlayurl());
                intent.putExtra(CommonContent.BiliFlvTitle, recommend_data.getLives().get(i).getOwner().getName());
                intent.putExtra(CommonContent.BiliFlvImg, recommend_data.getLives().get(i).getCover().getSrc());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        recommendGridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }

    private List<AppUserInfo.DataBean.PartitionsBean> partitions;
    private List<AppUserInfo.DataBean.BannerBean> banner_item;

    //handler2. 频道显示
    private void setChannel() {
        tv_channel2.setText(partitions.get(0).getPartition().getName());
        tv_channel3.setText(partitions.get(1).getPartition().getName());
        tv_channel4.setText(partitions.get(2).getPartition().getName());
        tv_channel5.setText(partitions.get(3).getPartition().getName());

        Glide.with(getActivity())
                .load(partitions.get(0).getPartition().getSub_icon().getSrc())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.bg_following_default_image_tv9)
                .error(R.drawable.bg_following_default_image_tv9).into(iv_channel2);
        Glide.with(getActivity())
                .load(partitions.get(1).getPartition().getSub_icon().getSrc())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.bg_following_default_image_tv9)
                .error(R.drawable.bg_following_default_image_tv9).into(iv_channel3);
        Glide.with(getActivity())
                .load(partitions.get(2).getPartition().getSub_icon().getSrc())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.bg_following_default_image_tv9)
                .error(R.drawable.bg_following_default_image_tv9).into(iv_channel4);
        Glide.with(getActivity())
                .load(partitions.get(3).getPartition().getSub_icon().getSrc())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.bg_following_default_image_tv9)
                .error(R.drawable.bg_following_default_image_tv9).into(iv_channel5);

    }


    //handler1. 轮播图
    private void setBanners() {
        for (AppUserInfo.DataBean.BannerBean bannerItemBean : banner_item) {
            String image = bannerItemBean.getImg();
            banner_images.add(image);
        }
        mBanner.setImages(banner_images);
        Log.e("banner_images", banner_images.size() + "");
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                dealWithBannerClick(position);//跳转
            }
        });
        mBanner.start();
    }

    public LiveBroadcastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live_broadcast, container, false);
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
        initRecycler();
        initRetrofit();
    }

    private void initLayout() {
        width = AppCompatUtils.getAppScreenWidth(getActivity());
    }

    private void initRecycler() {
        BilibiliRefreshView headerView = new BilibiliRefreshView(getActivity());
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(false);
        twinklingRefreshLayout.setAutoLoadMore(false);
        twinklingRefreshLayout.setEnableOverScroll(true);
        twinklingRefreshLayout.setHeaderHeight(90);
        twinklingRefreshLayout.setMaxHeadHeight(180);
        twinklingRefreshLayout.setEnableRefresh(false);//先禁用了


        //设置rc
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), SPAN_COUNT_ONE);
        recyclerview.setLayoutManager(manager);
        mAdapter = new LiveBroadcastAdapter(getActivity(), listT);
        //头部的布局适配
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_rc_livebroadcast_header, null); //有一个null header布局 如果不想要头的话
        rlBanner = inflate.findViewById(R.id.rl_livebroadcast_banner);
        mBanner = inflate.findViewById(R.id.livebroadcast_banner);

        iv_channel2 = inflate.findViewById(R.id.iv_channel2);
        iv_channel3 = inflate.findViewById(R.id.iv_channel3);
        iv_channel4 = inflate.findViewById(R.id.iv_channel4);
        iv_channel5 = inflate.findViewById(R.id.iv_channel5);

        tv_channel2 = inflate.findViewById(R.id.tv_channel2);
        tv_channel3 = inflate.findViewById(R.id.tv_channel3);
        tv_channel4 = inflate.findViewById(R.id.tv_channel4);
        tv_channel5 = inflate.findViewById(R.id.tv_channel5);

        iv_recommend_column = inflate.findViewById(R.id.iv_recommend_column);
        tv_recommend_column = inflate.findViewById(R.id.tv_recommend_column);
        tv_anchor_count = inflate.findViewById(R.id.tv_anchor_count);

        recommendGridView1 = inflate.findViewById(R.id.recommendGridView1);
        recommendGridView2 = inflate.findViewById(R.id.recommendGridView2);

        iv_centerBanner = inflate.findViewById(R.id.iv_centerBanner);


        mBanner.setIndicatorGravity(BannerConfig.RIGHT);

        AppCompatUtils.setScreenScale(rlBanner, width, 1, 2.5);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.setDelayTime(4000);
        mBanner.setIndicatorPadding(DensityUtil.dp2px(getActivity(), 25));

        mAdapter.setHeaderView(inflate); //这里把头部拿出来了
        recyclerview.setAdapter(mAdapter);

        //设置一个头 头部占一行
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mAdapter.isHeaderView(position)) ? manager.getSpanCount() : 1;
            }
        });

        //设置下拉上拉
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy = totalDy + dy;
                if (totalDy > 1000) {
                    ivGoTop.setVisibility(View.VISIBLE);
                } else {
                    ivGoTop.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resitDataLists();
                        isExecuteRefresh = true;
                        initRetrofit();
                    }
                }, 1200);
            }

        });
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlContent.commonLive + UrlBaseContent.AppLive)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        ResponseCommonService service = retrofit.create(ResponseCommonService.class);
        Call<AppUserInfo> call = service.getAppLive(CommonContent.actionKey, CommonContent.appkey, CommonContent.build,
                CommonContent.device, CommonContent.mobi_app, CommonContent.platform, CommonContent.scale, CommonContent.signId);
        call.enqueue(new Callback<AppUserInfo>() {
            @Override
            public void onResponse(Call<AppUserInfo> call, Response<AppUserInfo> response) {
                banner_item = response.body().getData().getBanner(); //banner
                partitions = response.body().getData().getPartitions();//频道
                recommend_data = response.body().getData().getRecommend_data();//推荐zb
                lives = recommend_data.getLives();//这里就刷新下推荐 全部刷新adapter传入多个集合即可
                mHandler.obtainMessage(LOAD_BANNER_SUCESS, response).sendToTarget();
                mHandler.obtainMessage(LOAD_CHANNEL_SUCESS, response).sendToTarget();
                mHandler.obtainMessage(LOAD_RECOMMENDED_ANCHORS_SUCESS, response).sendToTarget();
            }

            @Override
            public void onFailure(Call<AppUserInfo> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    private void resitDataLists() {
        if (lives != null) {
            lives.clear();
        }
        if (modulelist != null) {
            modulelist.clear();
        }
        if (modulelist2 != null) {
            modulelist2.clear();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_goTop)
    public void onViewClicked() {
        ivGoTop.setVisibility(View.GONE);
        recyclerview.scrollBy(0, -totalDy);
    }
}
