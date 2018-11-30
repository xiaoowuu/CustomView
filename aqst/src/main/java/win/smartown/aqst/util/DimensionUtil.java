package win.smartown.aqst.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 类描述：尺寸工具
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/6 14:41
 */
public class DimensionUtil {

    private static DisplayMetrics getDisplayMetrics(Resources resources) {
        return resources.getDisplayMetrics();
    }

    public static int dip2px(Resources resources, int dipValue) {
        return (int) (dipValue * getDensity(resources));
    }

    public static float getDensity(Resources resources) {
        return getDisplayMetrics(resources).density;
    }

    public static int getScreenHeight(Resources resources) {
        return getDisplayMetrics(resources).heightPixels;
    }

    public static int getScreenWidth(Resources resources) {
        return getDisplayMetrics(resources).widthPixels;
    }

}
