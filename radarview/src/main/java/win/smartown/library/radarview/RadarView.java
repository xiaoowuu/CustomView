package win.smartown.library.radarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：雷达图
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/6 14:41
 */
public class RadarView extends View {

    private RadarAdapter adapter;
    private Paint paint;
    private TextPaint textPaint;

    private Path bonePath;
    private Path coverPath;
    private SparseArray<Path> netPaths;
    private List<PointF> nodePoints;

    private int textColor;
    private int textSize;
    private int boneColor;
    private int boneWidth;
    private int netColor;
    private int netWidth;
    private int nodeColor;
    private int nodeRadius;
    private int coverColor;
    private int labelMargin;

    public RadarView(Context context) {
        super(context);
        init(null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RadarView);
            textColor = typedArray.getColor(R.styleable.RadarView_textColor, Color.BLACK);
            textSize = typedArray.getDimensionPixelSize(R.styleable.RadarView_textSize, 20);
            boneColor = typedArray.getColor(R.styleable.RadarView_boneColor, Color.BLACK);
            boneWidth = typedArray.getDimensionPixelSize(R.styleable.RadarView_boneWidth, 2);
            netColor = typedArray.getColor(R.styleable.RadarView_netColor, Color.BLACK);
            netWidth = typedArray.getDimensionPixelSize(R.styleable.RadarView_netWidth, 2);
            nodeColor = typedArray.getColor(R.styleable.RadarView_nodeColor, Color.BLACK);
            nodeRadius = typedArray.getDimensionPixelSize(R.styleable.RadarView_nodeRadius, 4);
            coverColor = typedArray.getColor(R.styleable.RadarView_coverColor, Color.BLACK);
            labelMargin = typedArray.getDimensionPixelSize(R.styleable.RadarView_labelMargin, 16);
            typedArray.recycle();
        } else {
            textColor = Color.BLACK;
            textSize = 20;
            boneColor = Color.BLACK;
            boneWidth = 2;
            netColor = Color.BLACK;
            netWidth = 2;
            nodeColor = Color.BLACK;
            nodeRadius = 4;
            coverColor = Color.BLACK;
            labelMargin = 16;
        }
        bonePath = new Path();
        coverPath = new Path();
        netPaths = new SparseArray<>();
        nodePoints = new ArrayList<>();

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (isInEditMode()) {
            setAdapter(new RadarAdapter() {
                @Override
                public int getItemCount() {
                    return 12;
                }

                @Override
                public int getMaxValue() {
                    return 5;
                }

                @Override
                public int getValue(int position) {
                    if (position > 5) {
                        return 5;
                    }
                    return position;
                }

                @Override
                public String getName(int position) {
                    return "Label";
                }
            });
        }
    }

    public void setAdapter(RadarAdapter adapter) {
        this.adapter = adapter;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (adapter != null) {
            bonePath.reset();
            coverPath.reset();
            netPaths.clear();
            nodePoints.clear();
            doDraw(canvas);
        }
    }

    /**
     * 执行绘制
     *
     * @param canvas {@link Canvas}
     */
    private void doDraw(Canvas canvas) {
        //骨架条数
        int boneCount = adapter.getItemCount();
        //中心点坐标
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        //半径
        float radius = Math.min(getWidth(), getHeight()) / 3;
        //最大值
        int maxValue = adapter.getMaxValue();
        //根据最大值等分半径
        float perValueRadius = radius / maxValue;
        //根据维度数等分角度
        float perDegree = 360f / (boneCount);
        //允许文本显示的最大宽度
        int maxTextWidth = (int) (radius / 2 - 2 * labelMargin);

        for (int index = 0; index < boneCount; index++) {
            float degree = perDegree * index - 90;
            for (int value = 0; value <= maxValue; value++) {
                float valueRadius = value * perValueRadius;
                float x = (float) (centerX + valueRadius * Math.cos(degree * Math.PI / 180));
                float y = (float) (centerY + valueRadius * Math.sin(degree * Math.PI / 180));

                if (value == maxValue) {
                    //获取各维度定点坐标，作为文本绘制的基准点和骨架终点
                    bonePath.moveTo(centerX, centerY);
                    bonePath.lineTo(x, y);
                    drawLabel(canvas, index, maxTextWidth, x, y, degree);
                }

                if (value == adapter.getValue(index)) {
                    //第index个维度的值所对应坐标
                    if (index == 0) {
                        coverPath.moveTo(x, y);
                    } else {
                        coverPath.lineTo(x, y);
                    }
                    //记录值坐标后面绘制圆点
                    nodePoints.add(new PointF(x, y));
                }

                //网线路径
                Path netPath = netPaths.get(value);
                if (netPath == null) {
                    netPath = new Path();
                    netPaths.put(value, netPath);
                }
                if (index == 0) {
                    netPath.moveTo(x, y);
                } else if (index == boneCount - 1) {
                    netPath.lineTo(x, y);
                    netPath.close();
                } else {
                    netPath.lineTo(x, y);
                }
            }
        }
        //绘制骨架
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setColor(boneColor);
        paint.setStrokeWidth(boneWidth);
        canvas.drawPath(bonePath, paint);
        //绘制网线
        paint.setColor(netColor);
        paint.setStrokeWidth(netWidth);
        for (int i = 0; i < netPaths.size(); i++) {
            canvas.drawPath(netPaths.get(i), paint);
        }
        //绘制覆盖层，即每个维度值形成的覆盖层
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(coverColor);
        canvas.drawPath(coverPath, paint);
        //绘制各维度值坐标圆点
        paint.setColor(nodeColor);
        for (int i = 0; i < nodePoints.size(); i++) {
            PointF point = nodePoints.get(i);
            canvas.drawCircle(point.x, point.y, nodeRadius, paint);
        }
    }

    /**
     * 绘制文
     *
     * @param canvas       {@link Canvas}
     * @param index        维度index
     * @param maxTextWidth 允许的最大文本宽度
     * @param x            基准点x
     * @param y            基准点y
     * @param degree       角度
     */
    private void drawLabel(Canvas canvas, int index, float maxTextWidth, float x, float y, float degree) {
        String name = adapter.getName(index);
        int textWidth = (int) Math.min(textPaint.measureText(name), maxTextWidth);
        StaticLayout staticLayout = new StaticLayout(name, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1, 0, true);
        int textHeight = staticLayout.getHeight();
        canvas.save();
        if (degree >= -45 && degree <= 45) {
            //文本显示到右边
            canvas.translate(x + labelMargin, y - textHeight / 2);
        } else if (degree > 45 && degree < 135) {
            //文本显示到底部
            canvas.translate(x - textWidth / 2, y + labelMargin);
        } else if (degree >= 135 && degree <= 225) {
            //文本显示到左边
            canvas.translate(x - textWidth - labelMargin, y - textHeight / 2);
        } else {
            //根本显示到上面
            canvas.translate(x - textWidth / 2, y - textHeight - labelMargin);
        }
        staticLayout.draw(canvas);
        canvas.restore();
    }

}