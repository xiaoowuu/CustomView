package smartown.win.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import win.smartown.aqst.activity.BaseActivity;
import win.smartown.library.signview.SignView;

/**
 * 类描述：电子签名控件演示
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/30 10:00
 */
public class SignViewActivity extends BaseActivity {

    private SignView signView;
    private ImageView resultImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_view);
        signView = findViewById(R.id.sign);
        resultImageView = findViewById(R.id.image);
        findViewById(R.id.clear).setOnClickListener(this::clear);
        findViewById(R.id.bitmap).setOnClickListener(this::getBitmap);
    }

    private void clear(View view) {
        signView.clear();
    }

    private void getBitmap(View view) {
        resultImageView.setImageBitmap(signView.getBitmap());
    }
}
