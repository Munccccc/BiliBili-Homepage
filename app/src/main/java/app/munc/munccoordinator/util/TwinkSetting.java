package app.munc.munccoordinator.util;

import android.app.Activity;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import app.munc.munccoordinator.view.BiliBottomView;
import app.munc.munccoordinator.view.BilibiliRefreshView;

/**
 * Created by GD on 2018/2/7.
 */

public class TwinkSetting {
    /**
     * 设置Twinking头尾和基本设置
     *
     * @param context
     */
    public static void setTwinkHeaderAndFooter(Activity context, TwinklingRefreshLayout twinklingRefreshLayout) {
        BilibiliRefreshView headerView = new BilibiliRefreshView(context);
        twinklingRefreshLayout.setHeaderView(headerView);

        BiliBottomView footView = new BiliBottomView(context);
        twinklingRefreshLayout.setBottomView(footView);

        twinklingRefreshLayout.setEnableOverScroll(true);
        twinklingRefreshLayout.setHeaderHeight(90);
        twinklingRefreshLayout.setMaxHeadHeight(180);
        twinklingRefreshLayout.setBottomHeight(90);
        twinklingRefreshLayout.setMaxBottomHeight(180);
        twinklingRefreshLayout.setAutoLoadMore(true);
    }
}
