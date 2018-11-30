package smartown.win.demo;

import android.os.Bundle;
import android.view.View;

import win.smartown.aqst.activity.BaseActivity;
import win.smartown.aqst.util.JumpUtil;

/**
 * 类描述：入口
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/30 10:00
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signView(View view) {
        JumpUtil.startActivity(this, SignViewActivity.class, R.string.sign_view);
    }

    public void radarView(View view) {
        JumpUtil.startActivity(this, RadarViewActivity.class, R.string.radar_view);
    }

}
