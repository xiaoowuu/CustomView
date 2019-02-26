package win.smartown.writing;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * 类描述：
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2019/1/30 9:41
 */
public class WritingCardView extends FrameLayout {

    private AnimatorSet animator;

    public WritingCardView(@NonNull Context context) {
        super(context);
        init();
    }

    public WritingCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WritingCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_view_writing, null);
        addView(view);
        Animator animator1 = createAnimator(view.findViewById(R.id.loading_1), 0);
        Animator animator2 = createAnimator(view.findViewById(R.id.loading_2), 100);
        Animator animator3 = createAnimator(view.findViewById(R.id.loading_3), 200);
        Animator animator4 = createAnimator(view.findViewById(R.id.loading_4), 300);
        Animator animator5 = createAnimator(view.findViewById(R.id.loading_5), 400);
        animator = new AnimatorSet();
        animator.playTogether(animator1, animator2, animator3, animator4, animator5);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animation.start();
            }
        });
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.cancel();
    }

    private Animator createAnimator(View view, long delay) {
        Animator animator = ObjectAnimator.ofFloat(view, "alpha", 0, 1, 0);
        animator.setDuration(500);
        animator.setStartDelay(delay);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        return animator;
    }

}
