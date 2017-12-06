package app.munc.munccoordinator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import app.munc.munccoordinator.R;


/**
 * Created by Munc on 2017/12/2.
 */

public class MuncSpecialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final int width;
    private List<String> dataAll;

    public MuncSpecialAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        width = dm.widthPixels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        //身体的布局适配
        view = mLayoutInflater.inflate(R.layout.item_horizontal_special, parent, false);
        ImageView iv_goodsImage = view.findViewById(R.id.iv_goodsImage);

        //图片
        ViewGroup.LayoutParams layoutParams_iv_goodsImage = iv_goodsImage.getLayoutParams();
        layoutParams_iv_goodsImage.width = (int) (width / 2.88);
        layoutParams_iv_goodsImage.height = (int) (width / 2.88);
        iv_goodsImage.setLayoutParams(layoutParams_iv_goodsImage);
        return new FakePageVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FakePageVH) {

        }
    }


    @Override
    public int getItemCount() {
        return dataAll == null ? 0 : dataAll.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addData(List<String> catProductList) {
        dataAll = catProductList;
        notifyDataSetChanged();
    }


    private class FakePageVH extends RecyclerView.ViewHolder {
        private final ImageView iv_goodsImage;

        public FakePageVH(View view) {
            super(view);
            iv_goodsImage = (ImageView) view.findViewById(R.id.iv_goodsImage);

        }
    }
}
