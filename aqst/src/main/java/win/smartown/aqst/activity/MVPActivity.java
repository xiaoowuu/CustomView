package win.smartown.aqst.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import win.smartown.aqst.mvp.BasePresenter;

/**
 * 类描述：MVPActivity
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/6 14:41
 */
public abstract class MVPActivity<Presenter extends BasePresenter> extends BaseActivity {

    protected Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = onCreatePresenter();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }

    protected abstract Presenter onCreatePresenter();

}