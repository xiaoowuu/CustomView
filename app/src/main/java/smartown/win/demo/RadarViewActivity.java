package smartown.win.demo;

import android.os.Bundle;

import win.smartown.aqst.activity.BaseActivity;
import win.smartown.library.radarview.RadarAdapter;
import win.smartown.library.radarview.RadarView;

/**
 * 类描述：雷达图控件演示
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/30 10:00
 */
public class RadarViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        RadarView radarView = findViewById(R.id.radar);
        radarView.setAdapter(new RadarAdapter() {
            @Override
            public int getItemCount() {
                return 12;
            }

            @Override
            public int getMaxValue() {
                return 5;
            }

            @Override
            public int getValue(int position) {
                if (position > getMaxValue()) {
                    return getMaxValue();
                }
                return position;
            }

            @Override
            public String getName(int position) {
                return "Label" + position;
            }
        });
    }

}