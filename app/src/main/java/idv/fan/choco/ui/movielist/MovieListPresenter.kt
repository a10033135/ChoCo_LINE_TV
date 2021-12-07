package idv.fan.choco.ui.movielist

import android.view.View
import com.socks.library.KLog
import idv.fan.choco.R
import idv.fan.choco.net.Interactorlmpl
import idv.fan.choco.net.SwitchSchedulers
import idv.fan.choco.ui.base.BasePresenter
import idv.fan.choco.model.MovieBean
import idv.fan.choco.net.ChocoInteractor
import idv.fan.choco.net.RxSubscriber

class MovieListPresenter : BasePresenter<MovieListContract.View>(), MovieListContract.Presenter {

    private val TAG = MovieListPresenter::class.java.simpleName
    private var mMovieList: List<MovieBean> = listOf()
    private val mChocoInteractor: ChocoInteractor by lazy { Interactorlmpl() }

    override fun subscribe() {
        KLog.i(TAG, "subscribe")
        if (mMovieList.isEmpty()) {
            getMovieList()
        } else {
            setViewStatus(ViewStates.SUCCESS)
        }
    }

    enum class ViewStates { LOADING, SUCCESS, ERROR, EMPTY }

    private fun setViewStatus(states: ViewStates) {
        KLog.i(TAG, "setViewStatus: $states")
        view?.getFragmentActivity()?.let { activity ->
            view?.setLoadingVisibility(View.GONE)
            view?.setRecyclerViewVisibility(View.GONE)
            view?.setViewStateMsgVisibility(View.GONE)
            view?.setSearchViewVisibility(View.GONE)

            when (states) {
                ViewStates.LOADING -> {
                    view?.setLoadingVisibility(View.VISIBLE)
                }
                ViewStates.SUCCESS -> {
                    view?.setSearchViewVisibility(View.VISIBLE)
                    view?.setRecyclerViewVisibility(View.VISIBLE)
                    view?.setMovieList(mMovieList)
                }
                ViewStates.ERROR -> {
                    view?.setViewStateMsgVisibility(View.VISIBLE)
                    view?.setViewStateMsg(activity.getString(R.string.msg_net_error))
                }
                ViewStates.EMPTY -> {
                    view?.setViewStateMsg(activity.getString(R.string.msg_data_empty))
                }
            }
        }
    }

    /**
     *  取得影片列表，包含網路與本地端 */
    private fun getMovieList() {
        view?.getFragmentActivity()?.let { activity ->
            KLog.i(TAG, "getMovieList")
            setViewStatus(ViewStates.LOADING)
            val movieSubscribe = createMovieSubscribe()
            mChocoInteractor.getMovies(activity)
                .compose(SwitchSchedulers.applyFlowableSchedulers())
                .switchIfEmpty {
                    mMovieList = listOf()
                    setViewStatus(ViewStates.EMPTY)
                }
                .subscribe(movieSubscribe)
        }

    }

    private fun createMovieSubscribe(): RxSubscriber<List<MovieBean>> {
        return object : RxSubscriber<List<MovieBean>>() {
            override fun _onNext(alMovie: List<MovieBean>) {
                KLog.i(TAG, alMovie.toString())
                mMovieList = alMovie
                setViewStatus(ViewStates.SUCCESS)
            }

            override fun _onError(code: Int, msg: String?) {
                KLog.e(TAG, msg)
                setViewStatus(ViewStates.ERROR)
            }
        }
    }

    override fun onSearchConfirmClick(key: String) {
        KLog.i(TAG, "onSearchOnConfirmClick: $key")
        view?.getFragmentActivity()?.let { activity ->
            setViewStatus(ViewStates.LOADING)
            val movieSubscribe = createMovieSubscribe()
            mChocoInteractor.searchMovies(activity, key)
                .compose(SwitchSchedulers.applyFlowableSchedulers())
                .subscribe(movieSubscribe)
        }
    }

    override fun onRefreshClick() {
        mMovieList = listOf()
        subscribe()
    }
}