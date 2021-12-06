package idv.fan.choco.ui.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.socks.library.KLog
import idv.fan.choco.R
import idv.fan.choco.model.MovieBean

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    private val TAG = MovieListAdapter::class.java.simpleName
    private var mMovieList: List<MovieBean> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_movie, parent, false)
        return MovieListViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        mMovieList.getOrNull(position)?.let { movieBean ->
            val context = holder.itemView.context
            holder.tvMovieName.text = movieBean.name
            holder.tvMovieRating.text = movieBean.rating.toString()
            holder.tvMovieCreatedAt.text = movieBean.created_at
            Glide.with(context)
                .load(movieBean.thumb)
                .into(holder.ivMovieThumb)
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