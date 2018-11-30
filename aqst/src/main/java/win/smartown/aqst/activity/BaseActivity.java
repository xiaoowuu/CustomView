package win.smartown.aqst.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import win.smartown.aqst.R;
import win.smartown.aqst.util.JumpUtil;

/**
 * 类描述：BaseActivity
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/6 14:41
 */
public class BaseActivity extends AppCompatActivity {

    protected View mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        setupToolbar();
    }

    protected void setupToolbar() {
        Toolbar toolbar = super.findViewById(R.id.base_toolbar);
        String title = getIntent().getStringExtra(JumpUtil.EXTRA_TITLE);
        try {
            toolbar.setTitle(title);
        } catch (Exception e) {
            Log.e("BaseActivity", "please use JumpUtil.java to start an activity!");
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        ViewStub viewStub = super.findViewById(R.id.base_root);
        viewStub.setLayoutResource(layoutResID);
        mRootView = viewStub.inflate();
    }

    @Override
    public <T extends View> T findViewById(@IdRes int id) {
        return (T) mRootView.findViewById(id);
    }

}
