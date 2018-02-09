package app.munc.munccoordinator.fragment.mainbili.page1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.adapter.homepage.AfterSomeAdapter;
import app.munc.munccoordinator.util.TwinkSetting;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass. 追番模块
 */
public class AfterSomeFragment extends Fragment implements IHeaderView {


    private static final int SPAN_COUNT_ONE = 1;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private View view;
    private List listT = new ArrayList();
    private AfterSomeAdapter mAdapter;//追番适配器
    private boolean isExecuteRefresh = false;


    public AfterSomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_after_some, container, false);
        }
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
        initSet();
        isExecuteRefresh = true;
        initRetrofit();
    }

    private void initRetrofit() {

    }

    private void initLayout() {

    }

    private void initSet() {
        //设置下拉刷新头和twink初始化
        TwinkSetting.setTwinkHeaderAndFooter(getActivity(), mRefreshLayout);
        mRefreshLayout.setEnableRefresh(false);//先禁用了
        //设置rc
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), SPAN_COUNT_ONE);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AfterSomeAdapter(getActivity(), listT, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        setHeaderView();
        //设置一个头 头部占一行
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mAdapter.isHeaderView(position)) ? manager.getSpanCount() : 1;
            }
        });
    }

    private void setHeaderView() {
        //头部的布局适配
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_rc_aftersome_header, null); //有一个null header布局 如果不想要头的话
        mAdapter.setHeaderView(inflate); //这里把头部拿出来了
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {

    }

    @Override
    public void reset() {

    }
}
