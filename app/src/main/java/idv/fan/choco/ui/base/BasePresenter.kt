package idv.fan.choco.ui.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    protected var view: V? = null

    @JvmField
    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: V) {
        this.view = view
    }

    /* 統一釋放資源 */
    override fun unsubscribe() {
        compositeDisposable.clear()
    }

}