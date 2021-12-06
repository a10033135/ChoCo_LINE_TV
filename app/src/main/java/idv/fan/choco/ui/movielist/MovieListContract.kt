package idv.fan.choco.ui.movielist

import idv.fan.cathaybk.ui.base.BaseContract
import idv.fan.choco.model.MovieBean

interface MovieListContract {

    interface View : BaseContract.View {
        fun setMovieList(alMovie: List<MovieBean>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun saveMovieList()
    }
}