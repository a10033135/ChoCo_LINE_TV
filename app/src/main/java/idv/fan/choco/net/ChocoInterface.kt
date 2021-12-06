package idv.fan.choco.net

import idv.fan.choco.model.MovieBean
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET

interface ChocoInterface {

    @GET("dramas-sample.json")
    fun getMovies(): Flowable<List<MovieBean>>
}