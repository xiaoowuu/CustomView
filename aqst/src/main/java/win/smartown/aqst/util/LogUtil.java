package win.smartown.aqst.util;

import android.util.Log;


/**
 * 类描述：日志打印工具
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/6 14:41
 */
public class LogUtil {

    private static boolean isLogEnable = true;

    public static void setLogEnable(boolean enable) {
        isLogEnable = enable;
    }

    public static void logInfo(String tag, String info) {
        if (isLogEnable) {
            Log.i(tag, info);
        }
    }

    public static void logError(String tag, String info) {
        if (isLogEnable) {
            Log.e(tag, info);
        }
    }

}
