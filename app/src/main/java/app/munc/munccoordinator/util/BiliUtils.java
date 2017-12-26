package app.munc.munccoordinator.util;

/**
 * Created by GD on 2017/12/23.
 */

public class BiliUtils {

    /**
     * 这个用来显示Page1 - 推荐栏目中 视频的播放次数显示的
     */
    public static String setPlayCount(int playCount) {
        if (playCount < 10000) {
            return playCount + "";
        } else {
            double n = (double) playCount / 10000;
            return Utils.savaPointTwo(String.valueOf(n)) + "万";
        }
    }

}
