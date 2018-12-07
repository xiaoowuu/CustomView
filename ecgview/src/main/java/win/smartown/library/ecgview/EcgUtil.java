package win.smartown.library.ecgview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;

/**
 * 类描述：心电图绘制辅助工具
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/12/6 13:39
 */
public class EcgUtil {

    private int lineWidth;
    private int height;
    private int dividerWidth;
    private float maxValue;

    private Paint paint;

    public static EcgUtil newInstance() {
        return new EcgUtil();
    }

    private EcgUtil() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 设置波线颜色
     *
     * @param lineColor 波线颜色
     * @return this
     */
    public EcgUtil setLineColor(int lineColor) {
        paint.setColor(lineColor);
        return this;
    }

    /**
     * 设置波线宽度
     *
     * @param lineWidth 波线宽度
     * @return this
     */
    public EcgUtil setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        paint.setStrokeWidth(lineWidth);
        return this;
    }

    /**
     * 设置拐角半径
     *
     * @param cornerRadius 拐角半径
     * @return this
     */
    public EcgUtil setCornerRadius(int cornerRadius) {
        paint.setPathEffect(new CornerPathEffect(cornerRadius));
        return this;
    }

    /**
     * 设置图片高度
     *
     * @param height 图片高度
     * @return this
     */
    public EcgUtil setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * 设置波峰值
     *
     * @param maxValue 波峰值
     * @return this
     */
    public EcgUtil setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    /**
     * 设置两个数据间间隔宽度
     *
     * @param dividerWidth 两个数据间间隔宽度
     * @return this
     */
    public EcgUtil setDividerWidth(int dividerWidth) {
        this.dividerWidth = dividerWidth;
        return this;
    }

    /**
     * 生成心电图图片
     *
     * @param values 心电图数据
     * @return 心电图图片
     */
    @Nullable
    public Bitmap getResult(float... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(dividerWidth * (values.length - 1) + lineWidth, height, Bitmap.Config.ARGB_8888);
        Path path = new Path();
        float centerY = height / 2;
        float maxHeight = centerY / 5 * 4;
        for (int i = 0; i < values.length; i++) {
            float value = values[i];
            if (value > maxValue) {
                value = maxValue;
            } else if (value < -maxValue) {
                value = -maxValue;
            }
            float x = dividerWidth * i;
            float y = centerY - value / maxValue * maxHeight;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        Canvas canvas = new Canvas(bitmap);
        canvas.drawPath(path, paint);
        return bitmap;
    }

}
