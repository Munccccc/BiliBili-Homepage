package app.munc.munccoordinator.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.adapter.RcAnimAdapter;
import app.munc.munccoordinator.info.AnimInfo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinishFrag extends Fragment {


    private static final int SPAN_COUNT_ONE = 1;
    private final List<AnimInfo.DataBean.ArchivesBean> mArchives;
    @BindView(R.id.mZcRecyclerView)
    RecyclerView mZcRecyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.iv_go_top_home)
    ImageView ivGoTopHome;
    Unbinder unbinder;
    private View view;
    private int totalDy;
    private List listT = new ArrayList();
    private RcAnimAdapter mAdapter;

    public FinishFrag(List<AnimInfo.DataBean.ArchivesBean> archives) {
        // Required empty public constructor
        this.mArchives = archives;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_finish, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {setRecycler();}

    private void setRecycler() {
        mRefreshLayout.setNestedScrollingEnabled(false);
        mRefreshLayout.setEnableOverScroll(false);
        mRefreshLayout.setAutoLoadMore(true);
        //设置rc
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), SPAN_COUNT_ONE);
        mZcRecyclerView.setLayoutManager(manager);
        mAdapter = new RcAnimAdapter(getContext(), listT);
        mZcRecyclerView.setAdapter(mAdapter);
        //设置一个头 头部占一行
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mAdapter.isHeaderView(position)) ? manager.getSpanCount() : 1;
            }
        });
        //设置下拉上拉
        mZcRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addData(mArchives);
                        mRefreshLayout.finishLoadmore();
                    }
                }, 300);
            }
        });
        //设置数据
        mAdapter.setDatas(mArchives);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_go_top_home)
    public void onViewClicked() {
        ivGoTopHome.setVisibility(View.GONE);
        mZcRecyclerView.scrollBy(0, -totalDy);
    }
}
