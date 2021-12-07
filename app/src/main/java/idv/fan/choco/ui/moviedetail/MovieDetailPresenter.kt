package idv.fan.choco.ui.moviedetail

import com.socks.library.KLog
import idv.fan.choco.R
import idv.fan.choco.db.AppDatabase
import idv.fan.choco.model.MovieBean
import idv.fan.choco.model.MovieImgBean
import idv.fan.choco.model.getRatingFormat
import idv.fan.choco.ui.base.BasePresenter
import idv.fan.choco.utils.toMovieFormat

class MovieDetailPresenter(dramaId: Int) : BasePresenter<MovieDetailContract.View>(), MovieDetailContract.Presenter {

    private val TAG = MovieDetailPresenter::class.java.simpleName
    private val mDramaId = dramaId
    private var mMovieBean: MovieBean? = null
    private var mMovieImgBean: MovieImgBean? = null

    enum class ViewState { SUCCESS, ERROR }

    override fun subscribe() {
        KLog.i(TAG, "subscribe")
        getMovieData()
    }

    private fun getMovieData() {
        view?.getFragmentActivity()?.let { activity ->
            KLog.i(TAG, "getMovieData")
            try {
                mMovieBean = AppDatabase.getInstance(activity).movieDao().searchMovieDramaId(mDramaId)
                mMovieImgBean = AppDatabase.getInstance(activity).movieImgDao().getMovieImg(mDramaId)
                if (mMovieBean == null || mMovieImgBean == null) {
                    throw Exception("movie or movieImg is Null")
                } else {
                    setViewState(ViewState.SUCCESS)
                }
            } catch (e: Exception) {
                KLog.e(TAG, e.message)
                setViewState(ViewState.ERROR)
            }
        }
    }

    private fun setViewState(state: ViewState) {
        view?.getFragmentActivity()?.let { activity ->
            when (state) {
                ViewState.SUCCESS -> {
                    view?.setMovieImg(mMovieImgBean?.imgUri ?: "")
                    view?.setMovieName(mMovieBean?.name ?: "")
                    view?.setMovieCreateAt(mMovieBean?.created_at?.toMovieFormat(activity) ?: "")
                    view?.setMovieRating(mMovieBean?.getRatingFormat(activity) ?: "")
                    view?.setMovieTotalView(activity.getString(R.string.movie_view_count_format, mMovieBean?.total_views))
                }
                ViewState.ERROR -> {
                    view?.onError(activity.getString(R.string.msg_net_error))
                }
            }
        }
    }
}