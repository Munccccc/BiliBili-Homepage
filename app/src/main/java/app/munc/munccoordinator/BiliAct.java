package app.munc.munccoordinator;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import app.munc.munccoordinator.util.ActivityUtil;
import app.munc.munccoordinator.util.AppCompatUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BiliAct extends AppCompatActivity {

    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView(R.id.activity_bili)
    RelativeLayout activityBili;
    private Animation mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bili);
        getSupportActionBar().hide();
        AppCompatUtils.setAppStatusWhite(BiliAct.this);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initAnim();
    }

    private void initAnim() {
        mAnimation = AnimationUtils.loadAnimation(BiliAct.this, R.anim.anim_bili_scale);
        ivSplash.setAnimation(mAnimation);
        mAnimation.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityUtil.startActivity(BiliAct.this, BiliMainAct.class, null, true);
            }
        }, 3000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnimation != null) {
            mAnimation.cancel();
        }
    }
}
