package idv.fan.cathaybk.net

import android.content.Context
import idv.fan.choco.db.AppDatabase
import idv.fan.choco.db.MovieDao
import idv.fan.choco.model.MovieBean
import idv.fan.choco.net.ChocoInteraactor
import idv.fan.choco.net.ChocoInterface
import io.reactivex.Flowable

class Interactorlmpl : ChocoInteraactor {

    override fun getMovies(context: Context): Flowable<List<MovieBean>> {
        return AppDatabase.getInstance(context).movieDao().getMovies()
//        return ApiService.instance.getApiService(ChocoInterface::class.java).getMovies()
    }

    override fun insertMovies(context: Context, movieList: List<MovieBean>): Flowable<Boolean> {
        AppDatabase.getInstance(context).movieDao().insert(movieList)
        return Flowable.empty()
    }

}