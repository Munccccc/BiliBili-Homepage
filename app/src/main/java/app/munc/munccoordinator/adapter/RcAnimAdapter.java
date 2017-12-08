package app.munc.munccoordinator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.info.AnimInfo;
import app.munc.munccoordinator.util.AppCompatUtils;
import app.munc.munccoordinator.view.RoundImageView;

/**
 * Created by GD on 2017/12/8.
 */

public class RcAnimAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    private List<AnimInfo.DataBean.ArchivesBean> dataAll = new ArrayList<>();
    private int mHeaderCount = 1;//头部View个数
    private final Context mContext;
    private int appScreenWidth;


    public RcAnimAdapter(Context context, List listT) {
        this.mContext = context;
        this.dataAll = listT;
    }

    //分页加载的添加数据 暂时不要
    public void addData(List<AnimInfo.DataBean.ArchivesBean> productList) {
        dataAll.addAll(productList);
        notifyDataSetChanged();
    }

    //刷新数据 暂时需要
    public void setDatas(List<AnimInfo.DataBean.ArchivesBean> productList) {
        dataAll.clear();
        dataAll.addAll(productList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        appScreenWidth = AppCompatUtils.getAppScreenWidth(mContext);
        if (viewType == ITEM_TYPE_HEADER) {
            //头部的布局适配
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_anim_header, parent, false);
            return new HeaderViewHolder(headerView);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_anim_body, parent, false);

            return new CommonFakePageVH(layout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            Glide.with(mContext.getApplicationContext())
                    .load(dataAll.get(1).getPic())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(((HeaderViewHolder) holder).iv_header);
        } else if (holder instanceof CommonFakePageVH) {
            AnimInfo.DataBean.ArchivesBean archivesBean = dataAll.get(position - 1);
            Glide.with(mContext.getApplicationContext())
                    .load(archivesBean.getPic())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(((CommonFakePageVH) holder).ri_headerView);
            ((CommonFakePageVH) holder).tv_flag.setText(archivesBean.getTitle());
            ((CommonFakePageVH) holder).tv_title.setText(archivesBean.getDesc());


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

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {


        private final ImageView iv_header;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            iv_header = (ImageView) itemView.findViewById(R.id.iv_header);
        }
    }

    private class CommonFakePageVH extends RecyclerView.ViewHolder {
        private final RoundImageView ri_headerView;
        private final TextView tv_title;
        private final TextView tv_flag;

        public CommonFakePageVH(View layout) {
            super(layout);
//            ri_headerView tv_title tv_flag
            ri_headerView = layout.findViewById(R.id.ri_headerView);
            tv_title = layout.findViewById(R.id.tv_title);
            tv_flag = layout.findViewById(R.id.tv_flag);

        }
    }
}
