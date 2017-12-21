package app.munc.munccoordinator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.munc.munccoordinator.util.ActivityUtil;
import app.munc.munccoordinator.util.AppCompatUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Splash extends AppCompatActivity {

    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.activity_splash)
    LinearLayout activitySplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        AppCompatUtils.setAppStatusWhite(Splash.this);
    }

    @OnClick({R.id.tv_1, R.id.tv_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                ActivityUtil.startActivity(Splash.this, BiliAct.class, null, false);
                break;
            case R.id.tv_2:
                ActivityUtil.startActivity(Splash.this, MainActivity.class, null, false);
                break;
        }
    }


}
