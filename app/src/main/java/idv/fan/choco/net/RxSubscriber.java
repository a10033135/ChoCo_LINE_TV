package idv.fan.choco.net;

import com.socks.library.KLog;

import io.reactivex.subscribers.ResourceSubscriber;

public abstract class RxSubscriber<T> extends ResourceSubscriber<T> {
    private final String TAG = RxSubscriber.class.getSimpleName();

    @Override
    public void onNext(T t) {
        KLog.i(TAG, "onNext");
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        KLog.e(TAG, e);
        _onError(404, e.getMessage());
    }

    @Override
    public void onComplete() {
        KLog.i(TAG, "onCompleted");
    }

    public abstract void _onNext(T t);

    public abstract void _onError(int code, String msg);
}
