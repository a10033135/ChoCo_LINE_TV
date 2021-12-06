package idv.fan.choco.ui.movielist

import com.socks.library.KLog
import idv.fan.cathaybk.net.Interactorlmpl
import idv.fan.cathaybk.net.SwitchSchedulers
import idv.fan.cathaybk.ui.base.BasePresenter
import idv.fan.choco.db.AppDatabase
import idv.fan.choco.db.MovieDao
import idv.fan.choco.model.MovieBean
import idv.fan.choco.net.ChocoInteraactor
import idv.fan.choco.net.RxSubscriber

class MovieListPresenter : BasePresenter<MovieListContract.View>(), MovieListContract.Presenter {

    private val TAG = MovieListPresenter::class.java.simpleName
    private var mAlMovie: List<MovieBean> = listOf()
    private val mChocoInteractor: ChocoInteraactor by lazy { Interactorlmpl() }

    override fun subscribe() {
        if (mAlMovie.isEmpty()) {
            getMovieListByApi()
        } else {
            setViewStatus(ViewStatus.SUCCESS)
        }
    }

    enum class ViewStatus { LOADING, SUCCESS, ERROR }

    private fun setViewStatus(status: ViewStatus) {
        KLog.i(TAG, "setViewStatus: $status")
        when (status) {
            ViewStatus.LOADING -> {
            }
            ViewStatus.SUCCESS -> {
            }
            ViewStatus.ERROR -> {
            }
        }
    }

    override fun saveMovieList() {
        view?.getFragmentActivity()?.let {
            mChocoInteractor
                .insertMovies(it, mAlMovie)
                .compose(SwitchSchedulers.applyFlowableSchedulers())
                .onBackpressureBuffer()
                .subscribe {

                }

        }
    }

    private fun getMovieListByApi() {
        view?.getFragmentActivity()?.let { activity ->
            val movieSubscribe = createMovieSubscribe()
            mChocoInteractor.getMovies(activity)
                .compose(SwitchSchedulers.applyFlowableSchedulers())
                .subscribe(movieSubscribe)
        }

    }

    private fun createMovieSubscribe(): RxSubscriber<List<MovieBean>> {
        return object : RxSubscriber<List<MovieBean>>() {
            override fun _onNext(alMovie: List<MovieBean>) {
                KLog.i(TAG, alMovie.toString())
                mAlMovie = alMovie
                view?.setMovieList(mAlMovie)
            }

            override fun _onError(code: Int, msg: String?) {
                KLog.e(TAG, msg)
                setViewStatus(ViewStatus.ERROR)
            }
        }
    }

}