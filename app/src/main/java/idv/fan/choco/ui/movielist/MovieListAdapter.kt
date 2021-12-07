package idv.fan.choco.ui.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding.view.RxView
import com.socks.library.KLog
import idv.fan.choco.R
import idv.fan.choco.utils.loadUrl
import idv.fan.choco.model.MovieBean
import idv.fan.choco.net.Interactorlmpl
import idv.fan.choco.net.SwitchSchedulers
import idv.fan.choco.utils.toMovieFormat
import java.io.File

class MovieListAdapter(listener: MovieListListener) : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    private val TAG = MovieListAdapter::class.java.simpleName
    private var mMovieList: List<MovieBean> = listOf()
    private var mListener: MovieListListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_movie, parent, false)
        return MovieListViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        mMovieList.getOrNull(position)?.let { movieBean ->
            val context = holder.itemView.context
            holder.tvMovieName.text = movieBean.name
            holder.tvMovieRating.text = context.getString(R.string.movie_rating_format, movieBean.rating)
            holder.tvMovieCreatedAt.text = movieBean.created_at.toMovieFormat(context)
            holder.ivMovieThumb.loadUrl(R.drawable.image_placeholder)
            RxView.clicks(holder.itemView).subscribe {
                mListener.onItemClick(movieBean.drama_id)
            }
            Interactorlmpl()
                .getMovieImg(context, movieBean)
                .compose(SwitchSchedulers.applyFlowableSchedulers())
                .subscribe { Glide.with(context).load(File(it.imgUri)).placeholder(R.drawable.image_placeholder).into(holder.ivMovieThumb) }
        }
    }

    override fun getItemCount(): Int {
        KLog.i(TAG, "getItemCount: ${mMovieList.size}")
        return mMovieList.size
    }

    fun setMovieList(movieList: List<MovieBean>) {
        mMovieList = movieList
        notifyDataSetChanged()
    }

    class MovieListViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val ivMovieThumb = itemview.findViewById<ImageView>(R.id.iv_movie_thumb)
        val tvMovieName = itemview.findViewById<TextView>(R.id.tv_movie_name)
        val tvMovieRating = itemview.findViewById<TextView>(R.id.tv_movie_rating)
        val tvMovieCreatedAt = itemview.findViewById<TextView>(R.id.tv_movie_created_at)
    }

}

interface MovieListListener {
    fun onItemClick(dramaId: Int)
}