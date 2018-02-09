package app.munc.munccoordinator.adapter.homepage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.info.homepage.AppUserInfo;
import app.munc.munccoordinator.util.AppCompatUtils;

/**
 * Created by GD on 2017/12/26.
 */
//这个适配器适用于直播栏目下的列表格式
public class LiveBroadcastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;


    private int mHeaderCount = 1;//头部View个数
    private final Context mContext;
    private int width;
    private View headerView1;
    private List<AppUserInfo.DataBean.RecommendDataBean.LivesBean> dataAll = new ArrayList<>();


    public LiveBroadcastAdapter(Context context, List listT) {
        this.mContext = context;
        this.dataAll = listT;
    }

    //分页加载的添加数据 暂时不要
    public void addData(List<AppUserInfo.DataBean.RecommendDataBean.LivesBean>  productList) {
        dataAll.addAll(productList);
        notifyDataSetChanged();
    }

    //刷新数据 暂时需要
    public void setDatas(List<AppUserInfo.DataBean.RecommendDataBean.LivesBean> productList) {
        dataAll.clear();
        dataAll.addAll(productList);
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        width = AppCompatUtils.getAppScreenWidth(mContext);
        if (viewType == ITEM_TYPE_HEADER) {
            return new RecommendAdapter.HeaderViewHolder(headerView1);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_livebroadcast_body, parent, false);

            return new RcRecommendBody(layout);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof RecommendAdapter.HeaderViewHolder) {
        } else if (holder instanceof RcRecommendBody) {

        }
    }

    @Override
    public int getItemCount() {
        return dataAll == null ? 0 : dataAll.size() + mHeaderCount;
    }


    @Override
    public int getItemViewType(int position) {
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    public void setHeaderView(View headerView) {
        headerView1 = headerView;
    }

    private class RcRecommendBody extends RecyclerView.ViewHolder {


        public RcRecommendBody(View view) {
            super(view);

        }
    }
}
