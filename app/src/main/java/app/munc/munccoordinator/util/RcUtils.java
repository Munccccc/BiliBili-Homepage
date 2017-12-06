package app.munc.munccoordinator.util;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import java.util.Random;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.adapter.MuncSpecialAdapter;
import app.munc.munccoordinator.holder.CommonMuncBody;
import app.munc.munccoordinator.info.BiliBiliFanjuInfo;


/**
 * Created by Munc on 2017/12/2.
 */

public class RcUtils {

    public static void setRcLogic(final Context mContext,
                                  final CommonMuncBody holder, final int position,
                                  BiliBiliFanjuInfo.ResultBean resultBean, MuncSpecialAdapter mAdapter, List<BiliBiliFanjuInfo.ResultBean> dataSpecial) {
        holder.ll_main1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showToast(mContext, "" + position);
            }
        });
        holder.rc_special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showToast(mContext, "" + position);
            }
        });
        Glide.with(mContext.getApplicationContext())
                .load(dataSpecial.get(new Random().nextInt(dataSpecial.size())).getSquare_cover())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image).into(holder.iv_one);

        //出现横向布局  插播 这里数量为循环递增
        if (position % 7 == 0) {
            holder.ll_main1.setVisibility(View.GONE);
            holder.rc_special.setVisibility(View.VISIBLE);
            //这里通知子RC更新一下
            if (mAdapter != null) {
                holder.rc_special.setAdapter(mAdapter);
                mAdapter.addData(dataSpecial);

            }
        } else {
            holder.ll_main1.setVisibility(View.VISIBLE);
            holder.rc_special.setVisibility(View.GONE);


        }
    }
}
