package idv.fan.choco.net

import android.content.Context
import idv.fan.choco.model.MovieBean
import io.reactivex.Flowable
import okhttp3.ResponseBody

interface ChocoInteraactor {

    fun getMovies(context: Context): Flowable<List<MovieBean>>

    fun insertMovies(context: Context, movieList: List<MovieBean>): Flowable<Boolean>
}