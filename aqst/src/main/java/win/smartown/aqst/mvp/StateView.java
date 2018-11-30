package win.smartown.aqst.mvp;

/**
 * 类描述：StateView
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/6 14:41
 */
public interface StateView extends IView {
    void startLoading(boolean hideContent);

    void loadFail();

    void loadEmpty();

    void loadSuccess();
}
