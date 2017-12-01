package lesson_menu.example.com.munccoordinator.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lesson_menu.example.com.munccoordinator.R;
import lesson_menu.example.com.munccoordinator.manager.StatusBarColorManager;

/**
 * Created by zhudong on 2017/7/19.
 */
public class Utils {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static View view = null;

    /**
     * 根据一个网络连接(String)获取bitmap图像
     *
     * @param imageUri
     * @return
     * @throws OutOfMemoryError IOException
     */
    public static Bitmap getbitmap(final String imageUri) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) imgUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    //分享本地图片的
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //分享网络图片的
    public static byte[] bmpToByteArray2(final Bitmap bmp, final boolean needRecycle) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                //F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
    }

    /**
     * 在主线程执行Runnable
     */
    public static void runOnUIThread(Runnable task) {
        mHandler.post(task);
    }

    /**
     * 在主线程执行Toust
     */
    private static Toast toast = null;

    public static void showToast(final Context context, final String notice) {
        runOnUIThread(new Runnable() {
            @SuppressLint("ShowToast")
            @Override
            public void run() {
                try {
                    if (toast == null) {
                        toast = new Toast(context);
                    }
                    toast.setDuration(Toast.LENGTH_SHORT);
                    view = View.inflate(context, R.layout.layouttoast, null);
                    TextView tv_toast = (TextView) view.findViewById(R.id.tv_toast);
                    tv_toast.setText(notice);
                    toast.setView(view);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static boolean isWeiboInstalled(@NonNull Context context) {
        PackageManager pm;
        if ((pm = context.getApplicationContext().getPackageManager()) == null) {
            return false;
        }
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo info : packages) {
            String name = info.packageName.toLowerCase(Locale.ENGLISH);
            if ("com.sina.weibo".equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 格式数据库的日期
     *
     * @param date
     * @return
     */
    public static String[] formatDate(String date) {
        String[] split = date.split("T");
        return split;
    }

    public static String[] formatDateMonth(String date) {
        String[] split = date.split("-");
        return split;
    }

    public static String[] formatDatePoint(String date) {
        String[] split = date.split(",");
        return split;
    }

    public static String[] formatDot(String date) {
        String[] split = date.split("\\.");
        return split;

    }

    public static int stringToInt(String math) {

        return Integer.parseInt(math);
    }

    public static void setStatusBar(Activity mActivity) {
        StatusBarColorManager mStatusBarColorManager = new StatusBarColorManager(mActivity);
        int color = Color.parseColor("#FFFFFF");
        mStatusBarColorManager.setStatusBarColor(color, false, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0 以上全透明状态栏
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏 加下面几句可以去除透明状态栏的灰色阴影,实现纯透明
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            mActivity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            mActivity.getWindow().setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 判断给的String 是否包含小数点 有，小数点后一位不为0.返回小数点的后一位前面的数 ,反之，返回小数点前面的数
     *
     * @param data
     * @return
     */
    public static String cutString(String data) {
        if (TextUtils.isEmpty(data)) {
            return "";
        }

        if (data.contains(".")) {
            String p1 = data.substring(0, data.lastIndexOf("."));
            String p2 = data.subSequence(data.lastIndexOf(".") + 1,
                    data.lastIndexOf(".") + 2).toString();
            if ("0".equals(p2)) {
                return p1;
            } else {
                return data.substring(0, data.lastIndexOf(".") + 2);
            }

        }

        return data;
    }

    /**
     * 判断给的String 是否包含小数点 有，小数点后面全部删除
     *
     * @param data
     * @return
     */
    public static String splitString(String data) {
        if (TextUtils.isEmpty(data)) {
            return "";
        }

        if (data.contains(".")) {
            String p1 = data.substring(0, data.lastIndexOf("."));
            return p1;
        }

        return data;
    }

    /**
     * 剪切字符串的最后一位 “，”
     * 1212,3131,313， ---1212,3131,313
     *
     * @param data
     * @return
     */
    public static String cutLastPoint(String data) {
        if (data.isEmpty()) {
            return "";
        }
        return data.substring(0, data.lastIndexOf(","));

    }

    /**
     * 剪切字符串的最后一位 “.”
     * 1212.3131.313， ---1212,3131
     *
     * @param data
     * @return
     */
    public static String cutLastPoint2(String data) {
        if (data.isEmpty()) {
            return "";
        }
        return data.substring(0, data.lastIndexOf("."));

    }

    /**
     * 剪切字符串的最后一位 “|”
     * "9f1ca3d5-1132-4934-8422-0d4ad408d766|10578013-662a-4992-8b53-308f9a948b32|"
     *
     * @param data
     * @return
     */
    public static String cutLastLine(String data) {
        if (data.isEmpty()) {
            return "";
        }
        return data.substring(0, data.lastIndexOf("|"));

    }

    /**
     * 保留小数点后两位
     *
     * @param data
     * @return
     */
    public static String savaPointTwo(String data) {
        if (data == null) {
            return null;
        }
        if (data.contains(".") && formatDot(data)[1].length() >= 2) {
            return data.substring(0, data.lastIndexOf(".") + 3);
        }
        if (data.contains(".") && formatDot(data)[1].length() == 1) {
            return data.substring(0, data.lastIndexOf(".") + 2) + "0";
        }

        return data;
    }

    public static final int WEEKDAYS = 7;
    public static String[] WEEK = new String[]{"星期日", "星期一", "星期二", "星期三",
            "星期四", "星期五", "星期六"};


    /**
     * 返回星期
     *
     * @param date
     * @return
     */
    public static String formatWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];

    }

    // 将字符串转为时间戳

    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return re_time;
    }

    // 例如：2016-05-09T13:18:14
    // 转换 1291778220
    // 将时间戳转为字符串
    public static long dataTurntoInt(String date) {
        // 去除-
        String dateTime = null;
        if (date.contains("-")) {
            dateTime = date.replaceAll("-", ":");
        }
        if (dateTime.contains("T")) {
            dateTime = dateTime.replaceAll("T", " ");
        }

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy:MM:dd HH:mm:ss")
                    .parse(dateTime));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return c.getTimeInMillis();
    }

    /**
     * 获取系统时间 2016-05-09 13-18-14
     *
     * @return
     */
    public static String getSystemTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh-mm-ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String getSystemTime1() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    /**
     * @param lo 毫秒数
     * @return String yyyy-MM-dd HH:mm:ss
     * @Description: long类型转换成天时分秒
     */
    public static String longToDate(long lo) {
        long day = 0;
        long hour = 0;
        long Minutes = 0;
        long Seconds = 0;
        day = (lo / (24 * 60 * 60 * 1000));
        hour = (lo - day * (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
        Minutes = (lo - day * (24 * 60 * 60 * 1000) - hour * (60 * 60 * 1000))
                / (60 * 1000);
        Seconds = (lo - day * (24 * 60 * 60 * 1000) - hour * (60 * 60 * 1000) - Minutes
                * (60 * 1000)) / (1000);
        return day + "天" + hour + "时" + Minutes + "分" + Seconds + "秒";
    }

    /**
     * 拼接年月日
     *
     * @param Year
     * @param Month
     * @param day
     * @return
     */

    public static String jointDate(String Year, String Month, String day) {
        String m = null;
        String d = null;
        if (Month.length() == 1) {
            m = "0" + Month;
        } else {
            m = Month;
        }
        if (day.length() == 1) {
            d = "0" + day;
        } else {
            d = day;
        }

        return Year + "-" + m + "-" + d;
    }

    /**
     * 描述：是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    public static Boolean isPhone(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.length() == 11;
    }

    /**
     * 精确计算两个Double数相减
     *
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double sub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }


    /**
     * 精确计算String转化为Double
     *
     * @param v1
     * @return
     */
    public static Double sub1(String v1, String math) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal r = b1.multiply(new BigDecimal(math));
        return r.doubleValue();
    }

    /**
     * 获取屏幕的高度
     *
     * @param activity
     * @return
     */
    public static int getWindowHeight(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width2 = outMetrics.widthPixels;
        int height2 = outMetrics.heightPixels;
        return height2;
    }

    /**
     * 获取屏幕的宽度
     *
     * @param activity
     * @return
     */
    public static int getWindowWidth(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width2 = outMetrics.widthPixels;
        return width2;
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        context.getWindow().setAttributes(lp);
    }

    /**
     * 设置tabLayout下划线宽度
     *
     * @param tabs
     */
    public static void setTabIndicator(final TabLayout tabs, final int width) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabs.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabs.getChildAt(0);

                    int dp10 = ColorUtil.dip2px(tabs.getContext(), width);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 设置tabLayout下划线宽度  固定
     *
     * @param tabs
     */
    public static void setTabIndicator(final TabLayout tabs, final int leftDip, final int rightDip) {
        tabs.post(new Runnable() {
                      @Override
                      public void run() {
                          Class<?> tabLayout = tabs.getClass();
                          Field tabStrip = null;
                          try {
                              tabStrip = tabLayout.getDeclaredField("mTabStrip");
                          } catch (NoSuchFieldException e) {
                              e.printStackTrace();
                          }

                          tabStrip.setAccessible(true);
                          LinearLayout llTab = null;
                          try {
                              llTab = (LinearLayout) tabStrip.get(tabs);
                          } catch (IllegalAccessException e) {
                              e.printStackTrace();
                          }

                          int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
                          int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

                          for (int i = 0; i < llTab.getChildCount(); i++) {
                              View child = llTab.getChildAt(i);
                              child.setPadding(0, 0, 0, 0);
                              LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                              params.leftMargin = left;
                              params.rightMargin = right;
                              child.setLayoutParams(params);
                              child.invalidate();
                          }
                      }
                  }
        );


    }
}
