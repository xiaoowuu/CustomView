package win.smartown.aqst.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import win.smartown.aqst.R;
import win.smartown.aqst.mvp.BasePresenter;
import win.smartown.aqst.mvp.StateView;

/**
 * 类描述：用于展示多种状态的Activity
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/6 14:41
 */
public abstract class StateActivity<Presenter extends BasePresenter<? extends StateView>> extends MVPActivity<Presenter> implements StateView {

    protected View mFailView;
    protected View mEmptyView;
    protected View mLoadingView;
    protected View mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_state);
        initStateView();
    }

    /**
     * 初始化各状态视图
     */
    private void initStateView() {
        if (mRootView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) mRootView;
            mFailView = LayoutInflater.from(this).inflate(getFailLayout(), null);
            viewGroup.addView(mFailView);
            mEmptyView = LayoutInflater.from(this).inflate(getEmptyLayout(), null);
            viewGroup.addView(mEmptyView);
            mContentView = LayoutInflater.from(this).inflate(getContentLayout(), null);
            viewGroup.addView(mContentView);
            mLoadingView = LayoutInflater.from(this).inflate(getLoadingLayout(), null);
            viewGroup.addView(mLoadingView);
        }
    }

    /**
     * 获取内容布局
     *
     * @return 内容布局
     */
    @LayoutRes
    public abstract int getContentLayout();

    @LayoutRes
    public int getFailLayout() {
        return R.layout.layout_state_fail;
    }

    @LayoutRes
    public int getEmptyLayout() {
        return R.layout.layout_state_empty;
    }

    @LayoutRes
    public int getLoadingLayout() {
        return R.layout.layout_state_loading;
    }

    @Override
    public void startLoading(boolean hideContent) {
        mLoadingView.setVisibility(View.VISIBLE);
        mFailView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        if (hideContent) {
            mContentView.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadFail() {
        mLoadingView.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
        mFailView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadEmpty() {
        mLoadingView.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadSuccess() {
        mLoadingView.setVisibility(View.GONE);
        mContentView.setVisibility(View.VISIBLE);
    }
}