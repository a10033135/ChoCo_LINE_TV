package idv.fan.choco.ui.moviedetail

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.socks.library.KLog
import idv.fan.choco.R
import idv.fan.choco.utils.loadUrl
import idv.fan.choco.ui.base.BaseFragment
import idv.fan.choco.utils.DialogFactory
import kotlinx.android.synthetic.main.adapter_movie.iv_movie_thumb
import kotlinx.android.synthetic.main.adapter_movie.tv_movie_created_at
import kotlinx.android.synthetic.main.adapter_movie.tv_movie_name
import kotlinx.android.synthetic.main.adapter_movie.tv_movie_rating
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment :
    BaseFragment<MovieDetailContract.View, MovieDetailContract.Presenter>(),
    MovieDetailContract.View {

    private var mPopDialog: AlertDialog? = null

    override fun getTAG(): String {
        return MovieDetailFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.subscribe()
    }

    override fun setMovieName(name: String) {
        tv_movie_name?.text = name
    }

    override fun setMovieTotalView(total: String) {
        tv_movie_total_views?.text = total
    }

    override fun setMovieImg(uri: String) {
        iv_movie_thumb?.loadUrl(uri)
    }

    override fun setMovieCreateAt(createAt: String) {
        tv_movie_created_at?.text = createAt
    }

    override fun setMovieRating(rating: String) {
        tv_movie_rating?.text = rating
    }

    override fun onError(msg: String) {
        KLog.e(getTAG(), "onError")
        context?.let { context ->
            if (mPopDialog == null) {
                mPopDialog = DialogFactory.createPopDialog(context, msg) { dialog, which ->
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        dialog.dismiss()
                        mFragmentNavigationListener.popFragment()
                    }
                }
            }
            mPopDialog?.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPopDialog?.dismiss()
        mPopDialog = null
    }
}