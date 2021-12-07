package idv.fan.choco.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.socks.library.KLog
import idv.fan.choco.ui.base.BaseFragment
import idv.fan.choco.R
import idv.fan.choco.model.MovieBean
import idv.fan.choco.ui.moviedetail.MovieDetailFragment
import idv.fan.choco.ui.moviedetail.MovieDetailPresenter
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : BaseFragment<MovieListContract.View, MovieListContract.Presenter>(),
    MovieListContract.View, MovieListListener {

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
        KLog.i(getTAG(), "onViewCreated")
        initView()
        presenter?.subscribe()
    }

    private fun initView() {
        bt_search?.setOnClickListener { presenter?.onSearchConfirmClick(et_search.text.toString()) }
        et_search?.setOnEditorActionListener { textview, actionId, keyEvent ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    presenter?.onSearchConfirmClick(textview.text.toString())
                    return@setOnEditorActionListener true
                }
                else -> return@setOnEditorActionListener false
            }
        }

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_movie.layoutManager = layoutManager
        if (mAdapter == null) {
            mAdapter = MovieListAdapter(this)
        }
        rv_movie.adapter = mAdapter
    }

    override fun setMovieList(alMovie: List<MovieBean>) {
        KLog.i(getTAG(), "setMovieList")
        mAdapter?.setMovieList(alMovie)
    }

    override fun setViewStateMsgVisibility(visibility: Int) {
        tv_state_msg?.visibility = visibility
    }

    override fun setRecyclerViewVisibility(visibility: Int) {
        rv_movie?.visibility = visibility
    }

    override fun setLoadingVisibility(visibility: Int) {
        pb_loading?.visibility = visibility
    }

    override fun setViewStateMsg(msg: String) {
        tv_state_msg?.text = msg
    }

    override fun onItemClick(dramaId: Int) {
        KLog.i(getTAG(), "onItemClick: $dramaId")
        val movieDetailFragment = MovieDetailFragment()
        movieDetailFragment.presenter = MovieDetailPresenter(dramaId)
        mFragmentNavigationListener.pushFragment(movieDetailFragment)
    }

}