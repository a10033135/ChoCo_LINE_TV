package idv.fan.choco.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.socks.library.KLog
import idv.fan.cathaybk.ui.base.BaseFragment
import idv.fan.choco.R
import idv.fan.choco.model.MovieBean
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : BaseFragment<MovieListContract.View, MovieListContract.Presenter>(),
    MovieListContract.View {

    private var mAdapter: MovieListAdapter? = null

    override fun getTAG(): String {
        return MovieListFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        presenter?.subscribe()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_movie.layoutManager = layoutManager
        if (mAdapter == null) {
            mAdapter = MovieListAdapter()
        }
        rv_movie.adapter = mAdapter

        toolbar?.setOnClickListener { presenter?.saveMovieList() }
    }

    override fun setMovieList(alMovie: List<MovieBean>) {
        KLog.i(getTAG(), "setMovieList")
        mAdapter?.setMovieList(alMovie)
    }

}