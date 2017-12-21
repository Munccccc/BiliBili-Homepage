package app.munc.munccoordinator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.munc.munccoordinator.fragment.homepage.Page1;
import app.munc.munccoordinator.fragment.homepage.Page2;
import app.munc.munccoordinator.fragment.homepage.Page3;
import app.munc.munccoordinator.fragment.homepage.Page4;
import app.munc.munccoordinator.fragment.homepage.Page5;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BiliMainAct extends AppCompatActivity {

    @BindView(R.id.fl_main_activity)
    FrameLayout flMainActivity;
    @BindView(R.id.ll_page1)
    LinearLayout llPage1;
    @BindView(R.id.ll_page2)
    LinearLayout llPage2;
    @BindView(R.id.ll_page3)
    LinearLayout llPage3;
    @BindView(R.id.ll_page4)
    LinearLayout llPage4;
    @BindView(R.id.ll_page5)
    LinearLayout llPage5;
    @BindView(R.id.activity_bili_main)
    LinearLayout activityBiliMain;
    @BindView(R.id.iv_page1)
    ImageView ivPage1;
    @BindView(R.id.tv_page1)
    TextView tvPage1;
    @BindView(R.id.iv_page2)
    ImageView ivPage2;
    @BindView(R.id.tv_page2)
    TextView tvPage2;
    @BindView(R.id.iv_page3)
    ImageView ivPage3;
    @BindView(R.id.tv_page3)
    TextView tvPage3;
    @BindView(R.id.iv_page4)
    ImageView ivPage4;
    @BindView(R.id.tv_page4)
    TextView tvPage4;
    @BindView(R.id.iv_page5)
    ImageView ivPage5;
    @BindView(R.id.tv_page5)
    TextView tvPage5;
    private FragmentTransaction ft;
    private Page1 page1 = null;
    private Page2 page2 = null;
    private Page3 page3 = null;
    private Page4 page4 = null;
    private Page5 page5 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bili_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        setSelect(1);
    }

    @OnClick({R.id.ll_page1, R.id.ll_page2, R.id.ll_page3, R.id.ll_page4, R.id.ll_page5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_page1:
                setSelect(1);
                break;
            case R.id.ll_page2:
                setSelect(2);
                break;
            case R.id.ll_page3:
                setSelect(3);
                break;
            case R.id.ll_page4:
                setSelect(4);
                break;
            case R.id.ll_page5:
                setSelect(5);
                break;
        }
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        hideFragments();
        resetTabs();
        switch (i) {
            case 1:
                if (page1 == null) {
                    page1 = new Page1();
                    ft.add(R.id.fl_main_activity, page1);
                }
                ivPage1.setImageResource(R.drawable.ic_home_selected);
                tvPage1.setTextColor(Color.parseColor("#E97998"));
                ft.show(page1);
                break;
            case 2:
                if (page2 == null) {
                    page2 = new Page2();
                    ft.add(R.id.fl_main_activity, page2);
                }
                ivPage2.setImageResource(R.drawable.ic_category_selected);
                tvPage2.setTextColor(Color.parseColor("#E97998"));
                ft.show(page2);
                break;
            case 3:
                if (page3 == null) {
                    page3 = new Page3();
                    ft.add(R.id.fl_main_activity, page3);
                }
                ivPage3.setImageResource(R.drawable.ic_dynamic_selected);
                tvPage3.setTextColor(Color.parseColor("#E97998"));
                ft.show(page3);
                break;
            case 4:
                if (page4 == null) {
                    page4 = new Page4();
                    ft.add(R.id.fl_main_activity, page4);
                }
                ivPage4.setImageResource(R.drawable.ic_communicate_selected);
                tvPage4.setTextColor(Color.parseColor("#E97998"));
                ft.show(page4);
                break;
            case 5:
                if (page5 == null) {
                    page5 = new Page5();
                    ft.add(R.id.fl_main_activity, page5);
                }
                ivPage5.setImageResource(R.drawable.emoji_keai0001);
                tvPage5.setTextColor(Color.parseColor("#E97998"));

                ft.show(page5);
                break;
        }
        ft.commitAllowingStateLoss();
    }

    private void resetTabs() {
        ivPage1.setImageResource(R.drawable.ic_home_unselected);
        ivPage2.setImageResource(R.drawable.ic_category_unselected);
        ivPage3.setImageResource(R.drawable.ic_dynamic_unselected);
        ivPage4.setImageResource(R.drawable.ic_communicate_unselected);
        ivPage5.setImageResource(R.drawable.emoji_nanguo0027);
        tvPage1.setTextColor(Color.parseColor("#979797"));
        tvPage2.setTextColor(Color.parseColor("#979797"));
        tvPage3.setTextColor(Color.parseColor("#979797"));
        tvPage4.setTextColor(Color.parseColor("#979797"));
        tvPage5.setTextColor(Color.parseColor("#979797"));
    }

    private void hideFragments() {
        if (page1 != null) {
            ft.hide(page1);
        }
        if (page2 != null) {
            ft.hide(page2);
        }
        if (page3 != null) {
            ft.hide(page3);
        }
        if (page4 != null) {
            ft.hide(page4);
        }
        if (page5 != null) {
            ft.hide(page5);
        }
    }
}
