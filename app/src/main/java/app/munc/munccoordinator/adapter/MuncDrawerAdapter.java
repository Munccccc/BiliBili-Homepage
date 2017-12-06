package app.munc.munccoordinator.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.holder.DrawerBodyViewHolder;
import app.munc.munccoordinator.holder.DrawerHeaderViewHolder;
import app.munc.munccoordinator.info.RankingInfo;
import app.munc.munccoordinator.util.AppCompatUtils;

/**
 * Created by GD on 2017/12/5.
 */

public class MuncDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    private static final int SPAN_COUNT_ONE = 1;
    private List<RankingInfo.DataBean> dataAll = new ArrayList<>();
    private List dataSpecial = new ArrayList<>();
    private int mHeaderCount = 1;//头部View个数
    private final Context mContext;
    private int width;
    private MuncSpecialAdapter mAdapter;


    public MuncDrawerAdapter(Context context, List listT) {
        this.mContext = context;
        this.dataAll = listT;
    }

    //分页加载的添加数据 暂时不要
    public void addData(List<RankingInfo.DataBean> productList) {
        dataAll.addAll(productList);
        notifyDataSetChanged();
    }

    //刷新数据 暂时需要
    public void setDatas(List<RankingInfo.DataBean> productList) {
        dataAll.clear();
        dataAll.addAll(productList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        width = AppCompatUtils.getAppScreenWidth(mContext);
        if (viewType == ITEM_TYPE_HEADER) {
            //头部的布局适配
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_drawer, parent, false);
            final ImageView iv_user = headerView.findViewById(R.id.iv_user);
            final TextView tv_name = headerView.findViewById(R.id.tv_name);
            Glide.with(mContext.getApplicationContext())
                    .load(dataAll.get(1).getPic()).asBitmap()
                    .into(new BitmapImageViewTarget(iv_user) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            iv_user.setImageDrawable(circularBitmapDrawable);
                        }
                    });
            tv_name.setText(dataAll.get(1).getTitle());
            return new DrawerHeaderViewHolder(headerView);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            View layout = LayoutInflater.from(mContext).inflate(R.layout.item_rc_drawer_body, parent, false);

            return new DrawerBodyViewHolder(layout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DrawerHeaderViewHolder) {

        } else if (holder instanceof DrawerBodyViewHolder) {
            ((DrawerBodyViewHolder) holder).tv_bodyDescription.setText(dataAll.get(1).getDescription());
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
}
