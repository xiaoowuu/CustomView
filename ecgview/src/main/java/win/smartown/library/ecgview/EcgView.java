package win.smartown.library.ecgview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 类描述：
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/12/6 13:39
 */
public class EcgView extends View {

    private int color;
    private int width;
    private float maxValue;
    private int dividerWidth;
    private int cornerRadius;
    private Path path;

    private float lastX;
    private int maxScrollX;
    private Paint paint;
    private float[] values = new float[0];

    public EcgView(Context context) {
        super(context);
        init(null);
    }

    public EcgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.EcgView);
            color = typedArray.getColor(R.styleable.EcgView_color, Color.BLACK);
            width = typedArray.getDimensionPixelSize(R.styleable.EcgView_width, 1);
            dividerWidth = typedArray.getDimensionPixelSize(R.styleable.EcgView_dividerWidth, 16);
            maxValue = typedArray.getFloat(R.styleable.EcgView_maxValue, 1000);
            cornerRadius = typedArray.getDimensionPixelSize(R.styleable.EcgView_cornerRadius, 16);
            typedArray.recycle();
        } else {
            color = Color.BLACK;
            width = 1;
            dividerWidth = 16;
            maxValue = 1000;
            cornerRadius = 16;
        }
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(width);
        paint.setPathEffect(new CornerPathEffect(cornerRadius));
        if (isInEditMode()) {
            setValues(new float[]{800, -800, 700, -900, 900, -700, 600, -445, 454, -100, 9565, 98, 2323, -46});
        }
    }

    public void setValues(float[] values) {
        this.values = values;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPath();
        canvas.drawPath(path, paint);
    }

    private void initPath() {
        maxScrollX = (values.length - 1) * dividerWidth - getWidth();
        path.reset();
        int startIndex = getScrollX() / dividerWidth - 1;
        if (startIndex < 0) {
            startIndex = 0;
        }
        int count = getWidth() / dividerWidth + 3;
        float centerY = getHeight() / 2;
        float maxHeight = centerY / 5 * 4;
        for (int i = startIndex; i < startIndex + count; i++) {
            if (values.length > i) {
                float value = values[i];
                if (value > maxValue) {
                    value = maxValue;
                } else if (value < -maxValue) {
                    value = -maxValue;
                }
                float x = dividerWidth * i;
                float y = centerY - value / maxValue * maxHeight;
                if (i == startIndex) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            } else {
                break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                int deltaX = (int) (x - lastX);
                lastX = x;
                if (deltaX > 0) {
                    //scrollX减小
                    if (getScrollX() - deltaX < 0) {
                        scrollTo(0, 0);
                    } else {
                        scrollBy(-deltaX, 0);
                    }
                } else {
                    //scrollX增大
                    if (getScrollX() - deltaX > maxScrollX) {
                        scrollTo(maxScrollX, 0);
                    } else {
                        scrollBy(-deltaX, 0);
                    }
                }
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
