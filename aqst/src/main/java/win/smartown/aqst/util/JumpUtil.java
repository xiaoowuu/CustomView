package win.smartown.aqst.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;

/**
 * 类描述：页面跳转工具
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/6 14:41
 */
public class JumpUtil {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_EXTRA = "extra";

    public static void startActivity(Context context, Class<? extends Activity> activityClass, @StringRes int title) {
        startActivity(context, activityClass, context.getResources().getString(title), null);
    }

    public static void startActivity(Context context, Class<? extends Activity> activityClass, String title) {
        startActivity(context, activityClass, title, null);
    }

    public static void startActivity(Context context, Class<? extends Activity> activityClass, String title, Bundle bundle) {
        Intent intent = new Intent(context, activityClass);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_EXTRA, bundle);
        context.startActivity(intent);
    }
}