package win.smartown.library.signview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 类描述：签名控件
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/30 10:00
 */
public class SignView extends View {

    /**
     * 背景色，既是View的背景色，也是生成图片的背景色
     */
    private int backgroundColor;
    /**
     * 画笔颜色
     */
    private int color;
    /**
     * 画笔线条宽度
     */
    private int width;
    /**
     * 拐角半径
     */
    private int cornerRadius;

    private Path path;
    private Paint paint;
    private SignListener signListener;

    public SignView(Context context) {
        super(context);
        init(null);
    }

    public SignView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SignView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 初始化
     *
     * @param attrs {@link AttributeSet}
     */
    private void init(AttributeSet attrs) {
        backgroundColor = Color.WHITE;
        color = Color.BLACK;
        width = dpToPx(4);
        cornerRadius = dpToPx(80);

        if (attrs != null) {
            TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.SignView);
            backgroundColor = typedArray.getColor(R.styleable.SignView_backgroundColor, backgroundColor);
            color = typedArray.getColor(R.styleable.SignView_color, color);
            width = typedArray.getDimensionPixelSize(R.styleable.SignView_width, width);
            cornerRadius = typedArray.getDimensionPixelSize(R.styleable.SignView_cornerRadius, width);
            typedArray.recycle();
        }

        setFocusable(true);
        setClickable(true);
        setBackgroundColor(backgroundColor);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setPathEffect(new CornerPathEffect(cornerRadius));
        path = new Path();
        path.moveTo(-width, -width);
        if (isInEditMode()) {
            path.moveTo(100, 100);
            path.lineTo(90, 300);
            path.lineTo(200, 100);
            path.lineTo(190, 300);
        }
    }

    /**
     * dp转px
     *
     * @param dp dp值
     * @return px值
     */
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density * dp);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                path.lineTo(event.getX(), event.getY());
                invalidate();
                if (signListener != null) {
                    signListener.onSignChanged(false);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                invalidate();
                if (signListener != null) {
                    signListener.onSignChanged(false);
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    /**
     * 清除
     */
    public void clear() {
        path.reset();
        path.moveTo(-width, -width);
        invalidate();
        if (signListener != null) {
            signListener.onSignChanged(true);
        }
    }

    /**
     * 生成bitmap
     *
     * @return {@link Bitmap}
     */
    public Bitmap getBitmap() {
        Bitmap result = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(backgroundColor);
        canvas.drawPath(path, paint);
        return result;
    }

    public void setSignListener(SignListener signListener) {
        this.signListener = signListener;
    }

    public interface SignListener {
        /**
         * 签名发生改变
         */
        void onSignChanged(boolean empty);
    }

}