package idv.fan.choco.ui.moviedetail

import idv.fan.choco.ui.base.BaseContract
import java.net.URI

interface MovieDetailContract : BaseContract {

    interface View : BaseContract.View {
        fun setMovieName(name: String)
        fun setMovieTotalView(total: String)
        fun setMovieImg(uri: String)
        fun setMovieCreateAt(createAt: String)
        fun setMovieRating(rating: String)

        /**
         * 資料讀取失敗
         * @param msg 顯示文字
         * */
        fun onError(msg: String)
    }

    interface Presenter : BaseContract.Presenter<View> {

    }

}