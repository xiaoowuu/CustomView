package win.smartown.library.radarview;

/**
 * 类描述：雷达数据适配器
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/6 14:41
 */
public interface RadarAdapter {

    /**
     * 获取维度数量（数据种类）
     *
     * @return 维度数量
     */
    int getItemCount();

    /**
     * 获取数据最大值
     *
     * @return 数据最大值
     */
    int getMaxValue();

    /**
     * 获取某种数据的值
     *
     * @param position 数据位置
     * @return 某种数据的值
     */
    int getValue(int position);

    /**
     * 获取某种数据的名字
     *
     * @param position 数据位置
     * @return 数据的名字
     */
    String getName(int position);
}