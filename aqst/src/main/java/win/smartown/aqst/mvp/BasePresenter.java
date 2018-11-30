package win.smartown.aqst.mvp;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 类描述：BasePresenter
 *
 * @author xiaoowuu@gmail.com
 * 创建时间：2018/11/6 14:41
 */
public class BasePresenter<View extends IView> {

    private CompositeDisposable compositeDisposable;
    protected View mView;

    public BasePresenter(View view) {
        this.mView = view;
        compositeDisposable = new CompositeDisposable();
    }

    public <T> void execute(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        compositeDisposable.add(disposable);
                    }
                })
                .subscribe(observer);
    }

    public void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
