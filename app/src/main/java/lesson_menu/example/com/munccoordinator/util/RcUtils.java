package lesson_menu.example.com.munccoordinator.util;

import android.content.Context;
import android.view.View;

import java.util.List;

import lesson_menu.example.com.munccoordinator.adapter.MuncSpecialAdapter;
import lesson_menu.example.com.munccoordinator.holder.CommonMuncBody;

/**
 * Created by Munc on 2017/12/2.
 */

public class RcUtils {

    public static void setRcLogic(final Context mContext,
                                  final CommonMuncBody holder, final int position,
                                  List<String> dataAll, MuncSpecialAdapter mAdapter, List dataSpecial) {
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
