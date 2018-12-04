package win.smartown.library.autodisplayviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 类描述：自动轮播ViewPager
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/12/4 10:22
 */
public class AutoDisplayViewPager extends ViewPager {

    private int interval;
    private Timer timer;

    public AutoDisplayViewPager(@NonNull Context context) {
        super(context);
        init(null);
    }

    public AutoDisplayViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.AutoDisplayViewPager);
            interval = typedArray.getInteger(R.styleable.AutoDisplayViewPager_interval, 3000);
            typedArray.recycle();
        } else {
            interval = 3000;
        }
        timer = new Timer(this, interval);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        timer.cancel();
        super.setAdapter(adapter);
        timer.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        timer.cancel();
        super.onDetachedFromWindow();
    }

    /**
     * 轮播定时器
     */
    public static class Timer extends CountDownTimer {

        private WeakReference<ViewPager> reference;

        public Timer(ViewPager viewPager, int interval) {
            super(Integer.MAX_VALUE, interval);
            this.reference = new WeakReference<>(viewPager);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            ViewPager viewPager = reference.get();
            if (viewPager == null) {
                cancel();
                return;
            }
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter == null) {
                cancel();
                return;
            }
            int pageCount = adapter.getCount();
            int index = (viewPager.getCurrentItem() + 1) % pageCount;
            viewPager.setCurrentItem(index, true);
        }

        @Override
        public void onFinish() {
        }
    }

    /**
     * 无限滚动ViewPager适配器
     *
     * @param <Item> 数据
     */
    public abstract static class BannerAdapter<Item> extends PagerAdapter {

        protected List<Item> data;

        public BannerAdapter(List<Item> data) {
            this.data = data;
        }

        @Override
        public final int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public final Object instantiateItem(@NonNull ViewGroup container, int position) {
            Item item = data.get(position % data.size());
            return instantiateItem(container, item, position);
        }

        @NonNull
        public abstract Object instantiateItem(ViewGroup container, Item item, int position);

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (object instanceof View) {
                container.removeView((View) object);
            }
        }
    }

}