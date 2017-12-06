package app.munc.munccoordinator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import java.util.Random;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.info.BiliBiliFanjuInfo;


/**
 * Created by Munc on 2017/12/2.
 */

public class MuncSpecialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final int width;
    private List<BiliBiliFanjuInfo.ResultBean> dataAll;

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
            Glide.with(mContext.getApplicationContext())
                    .load(dataAll.get(new Random().nextInt(dataAll.size())).getCover())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.xiaomai)
                    .error(R.drawable.xiaomai).into(((FakePageVH) holder).iv_goodsImage);
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

    public void addData(List<BiliBiliFanjuInfo.ResultBean> catProductList) {
        dataAll = catProductList;
        notifyDataSetChanged();
    }


    private class FakePageVH extends RecyclerView.ViewHolder {
        public final ImageView iv_goodsImage;

        public FakePageVH(View view) {
            super(view);
            iv_goodsImage = (ImageView) view.findViewById(R.id.iv_goodsImage);

        }
    }
}
