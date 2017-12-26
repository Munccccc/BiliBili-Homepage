package app.munc.munccoordinator.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;

/**
 * Created by munc on 2017/12/24 哔哩哔哩的头部刷新控件
 */

public class BilibiliRefreshView extends FrameLayout implements IHeaderView {

    private ImageView refreshArrow;
    private TextView refreshTextView;

    public BilibiliRefreshView(Context context) {
        this(context, null);
    }

    public BilibiliRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BilibiliRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = View.inflate(getContext(), app.munc.munccoordinator.R.layout.view_biliheader, null);
        refreshArrow = rootView.findViewById(app.munc.munccoordinator.R.id.iv_topImage);
        refreshTextView = rootView.findViewById(app.munc.munccoordinator.R.id.tv_refreshStatus);
        addView(rootView);
    }

    public void setArrowResource(@DrawableRes int resId) {
        refreshArrow.setImageResource(resId);
    }

    public void setTextColor(@ColorInt int color) {
        refreshTextView.setTextColor(color);
    }

    public void setPullDownStr(String pullDownStr1) {
        pullDownStr = pullDownStr1;
    }

    public void setReleaseRefreshStr(String releaseRefreshStr1) {
        releaseRefreshStr = releaseRefreshStr1;
    }

    public void setRefreshingStr(String refreshingStr1) {
        refreshingStr = refreshingStr1;
    }

    private String pullDownStr = "再拉，再拉就刷给你看";
    private String releaseRefreshStr = "够了啦，松开人家嘛";
    private String refreshingStr = "刷呀刷呀，好累呀喵 ≥ω≤";

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1.2f) refreshTextView.setText(pullDownStr);
        if (fraction > 1.2f) refreshTextView.setText(releaseRefreshStr);

    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1.2f) {
            refreshTextView.setText(pullDownStr);

            if (refreshArrow.getVisibility() == INVISIBLE) {
                refreshArrow.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        refreshTextView.setText(refreshingStr);
        ((AnimationDrawable) refreshArrow.getDrawable()).start();
    }

    @Override
    public void onFinish(OnAnimEndListener listener) {
        listener.onAnimEnd();
        ((AnimationDrawable) refreshArrow.getDrawable()).stop();
    }

    @Override
    public void reset() {
        refreshArrow.setVisibility(VISIBLE);
        refreshTextView.setText(pullDownStr);
    }
}
