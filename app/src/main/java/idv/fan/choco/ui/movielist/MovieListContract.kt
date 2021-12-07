package idv.fan.choco.ui.movielist

import idv.fan.choco.ui.base.BaseContract
import idv.fan.choco.model.MovieBean

interface MovieListContract {

    interface View : BaseContract.View {
        fun setMovieList(alMovie: List<MovieBean>)
        fun setViewStateMsgVisibility(visibility: Int)
        fun setRecyclerViewVisibility(visibility: Int)
        fun setLoadingVisibility(visibility: Int)
        fun setViewStateMsg(msg: String)
    }

    interface Presenter : BaseContract.Presenter<View> {
        /**
         * 搜尋關鍵字
         * @param key 搜尋關鍵字
         * */
        fun onSearchConfirmClick(key: String)
    }
}