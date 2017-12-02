package lesson_menu.example.com.munccoordinator.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lesson_menu.example.com.munccoordinator.R;
import lesson_menu.example.com.munccoordinator.holder.CommonMuncBody;
import lesson_menu.example.com.munccoordinator.holder.HeaderViewHolder;
import lesson_menu.example.com.munccoordinator.util.AppCompatUtils;
import lesson_menu.example.com.munccoordinator.util.RcUtils;

/**
 * Created by GD on 2017/12/2.
 */

public class MuncAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    private static final int SPAN_COUNT_ONE = 1;
    private List<String> dataAll = new ArrayList<>();
    private List dataSpecial = new ArrayList<>();
    private int mHeaderCount = 1;//头部View个数
    private final Context mContext;
    private int width;
    private MuncSpecialAdapter mAdapter;


    public MuncAdapter(Context context, List listT) {
        this.mContext = context;
        this.dataAll = listT;
    }

    //分页加载的添加数据 暂时不要
    public void addData(List<String> productList) {
        dataAll.addAll(productList);
        notifyDataSetChanged();
    }

    //刷新数据 暂时需要
    public void setDatas(List<String> productList) {
        dataAll.clear();
        dataAll.addAll(productList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        width = AppCompatUtils.getAppScreenWidth(mContext);
        if (viewType == ITEM_TYPE_HEADER) {
            //头部的布局适配
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_header, parent, false);


            return new HeaderViewHolder(headerView);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rc_munc_body, parent, false);
            RecyclerView rc_special = layout.findViewById(R.id.rc_special);
            //RC
            final GridLayoutManager manager = new GridLayoutManager(mContext, SPAN_COUNT_ONE, GridLayoutManager.HORIZONTAL, false);
            rc_special.setLayoutManager(manager);
            mAdapter = new MuncSpecialAdapter(mContext);
            for (int a = 0; a < 5; a++) {
                dataSpecial.add(a);
            }
            return new CommonMuncBody(layout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
        } else if (holder instanceof CommonMuncBody) {
            RcUtils.setRcLogic(mContext, (CommonMuncBody) holder, position, dataAll, mAdapter, dataSpecial);
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
