
package app.munc.munccoordinator.util;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import app.munc.munccoordinator.R;


/**
 * Acitity工具类
 * 
 */
public class ActivityUtil {

    /**
     * 开启另外一个activity
     * 
     * @param activity
     * @param cls 另外的activity类
     * @param bundle 传递的bundle对象
     * @param isFinish true表示要关闭activity false表示不要关闭activity
     */
    public static void startActivity(Activity activity, Class<?> cls, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }




    /**
     * 开启另外一个activity(默认动画)
     * 
     * @param activity
     * @param cls 另外的activity类
     * @param bundle 传递的bundle对象
     * @param isFinish true表示要关闭activity false表示不要关闭activity
     */
    public static void startActivityDefault(Activity activity, Class<?> cls, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
    }
    /**
     * 开启另外一个activity
     * 
     * @param activity
     * @param cls 另外的activity类
     * @param bundle 传递的bundle对象
     * @param isFinish true表示要关闭activity false表示不要关闭activity
     */
    public static void startActivityBack(Activity activity, Class<?> cls, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 开启另外一个activity
     * 
     * @param activity
     * @param cls 另外的activity类
     * @param bundle 传递的bundle对象
     * @param isFinish true表示要关闭activity false表示不要关闭activity
     */
    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode,
                                              Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
        if (isFinish) {
            activity.finish();
        }
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    
    /**
     * Fragment中开启另外一个activity
     * 
     * @param fragment
     * @param cls 另外的activity类
     * @param bundle 传递的bundle对象
     * @param isFinish true表示要关闭activity false表示不要关闭activity
     */
    public static void startActivityForResult(Fragment fragment, Class<?> cls, int requestCode,
                                              Bundle bundle, boolean isFinish) {
    	Intent intent = new Intent(fragment.getActivity(), cls);
    	if (bundle != null) {
    		intent.putExtras(bundle);
    	}
    	fragment.startActivityForResult(intent, requestCode);
    	if (isFinish) {
    		fragment.getActivity().finish();
    	}
    	fragment.getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 开启另外一个activity
     * 
     * @param activity
     * @param intent
     * @param bundle 传递的bundle对象
     * @param requestCode
     * @param isFinish true表示要关闭activity false表示不要关闭activity
     */
    public static void startActivityForResultIntent(Activity activity, Intent intent,
                                                    Bundle bundle, int requestCode, boolean isFinish) {
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
        if (isFinish) {
            activity.finish();
        }
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void finishActivity(Activity activity){
    	// 退出Activity时动画
    	activity.finish();
    	activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    
    /**
     * 进入创建直播界面
     * @param activity
     * @param cls
     * @param bundle
     * @param isFinish
     */
    public static void startCreateLiveActivity(Activity activity, Class cls, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
       // activity.overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
    }

}
