package app.munc.munccoordinator.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import app.munc.munccoordinator.manager.StatusBarColorManager;


/**
 * Created by Munc on 2017/10/31.
 */


//这个是AppCompatActivity所有界面初始化的方法

public class AppCompatUtils {

    private static String versionName;

    /**
     * 设置确定6.0以上安卓版本状态栏
     *
     * @param context
     */
    public static void setAppStatus(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarColorManager mStatusBarColorManager = new StatusBarColorManager(context);
            int color = Color.parseColor("#E97998");
            mStatusBarColorManager.setStatusBarColor(color, false, false);
            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 获取屏幕的宽度
     *
     * @param activity
     * @return
     */
    public static int getAppScreenWidth(Context activity) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }

    /**
     * 获取版本号
     *
     * @param activity
     * @return
     */
    public static String getAppVersion(Activity activity) {
        PackageManager packageManager = activity.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 控件比例适配  按除法计算
     *
     * @param view
     * @return
     */
    public static void setScreenScale(View view, int screenWidth, double width, double height) {
        ViewGroup.LayoutParams layoutParams_rlTitle = view.getLayoutParams();
        layoutParams_rlTitle.width = (int) (screenWidth / width);
        layoutParams_rlTitle.height = (int) (screenWidth / height);
        view.setLayoutParams(layoutParams_rlTitle);
    }


}
