package smartown.win.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import win.smartown.aqst.activity.BaseActivity;
import win.smartown.library.ecgview.EcgView;

/**
 * 类描述：心电图
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/12/6 13:57
 */
public class EcgViewActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecg);
        EcgView ecgView = findViewById(R.id.ecgview);
        ecgView.setValues(new float[]{
                800, -800, 700, -900, 900, -700, 600, -445, 454, -100, 9565, 98, 2323, -46,
                800, -800, 700, -900, 900, -700, 600, -445, 454, -100, 9565, 98, 2323, -46,
                800, -800, 700, -900, 900, -700, 600, -445, 454, -100, 9565, 98, 2323, -46,
                800, -800, 700, -900, 900, -700, 600, -445, 454, -100, 9565, 98, 2323, -46,
                800, -800, 700, -900, 900, -700, 600, -445, 454, -100, 9565, 98, 2323, -46,
                800, -800, 700, -900, 900, -700, 600, -445, 454, -100, 9565, 98, 2323, -46,
                800, -800, 700, -900, 900, -700, 600, -445, 454, -100, 9565, 98, 2323, -46,
                800, -800, 700, -900, 900, -700, 600, -445, 454, -100, 9565, 98, 2323, -46});
    }
}
