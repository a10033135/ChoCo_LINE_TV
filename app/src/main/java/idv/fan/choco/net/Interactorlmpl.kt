package idv.fan.choco.net

import android.content.Context
import com.bumptech.glide.Glide
import com.socks.library.KLog
import idv.fan.choco.db.AppDatabase
import idv.fan.choco.model.MovieBean
import idv.fan.choco.model.MovieImgBean
import idv.fan.choco.pref.SharedPreferencesGroup
import io.reactivex.*

class Interactorlmpl : ChocoInteractor {

    private val TAG = Interactorlmpl::class.java.simpleName

    // 先用 room 搜尋資料，如果沒有則網路下載影片列表
    override fun getMovies(context: Context): Flowable<List<MovieBean>> {
        return Maybe.concat(
            CacheModelFactory.instance.getMovieListModelDisk(
                context,
                SharedPreferencesGroup.DataCache.Key_Movie_Expire,
                false
            ).firstElement(),
            getMoviesNetwork(context).firstElement()
        ).firstElement().toFlowable()
    }

    // 網路下載資料
    override fun getMoviesNetwork(context: Context): Flowable<List<MovieBean>> {
        KLog.i(TAG, "getMovieByApi")
        return ApiService.instance.getApiService(ChocoInterface::class.java).getMoviesNetwork()
            .doOnNext {
                AppDatabase.getInstance(context).movieDao().insert(it)
                CacheModelFactory.instance.saveModelDisk(
                    context, SharedPreferencesGroup.DataCache.Key_Movie_Expire
                )
            }.compose(CacheModelFactory.logSource(CacheModelFactory.SOURCE_NET, List<MovieImgBean>::javaClass.name))
    }

    // 從 room 搜尋關鍵字
    override fun searchMovies(context: Context, key: String): Flowable<List<MovieBean>> {
        val keySearch = AppDatabase.getInstance(context).movieDao().searchMovieName("%$key%")
        return Flowable.just(keySearch)
    }

    // 先用 room 搜尋資料，如果沒有則網路下載影片列表
    override fun getMovieImg(context: Context, movieBean: MovieBean): Flowable<MovieImgBean> {
        return Maybe.concat(
            CacheModelFactory.instance
                .getMovieImgModelDisk(context, movieBean, SharedPreferencesGroup.DataCache.Key_Movie_Img_Expire, false)
                .firstElement(),
            getMovieImgNetwork(context, movieBean).firstElement()
        ).firstElement().toFlowable()
    }

    // 網路下載資料
    override fun getMovieImgNetwork(context: Context, movieBean: MovieBean): Flowable<MovieImgBean> {
        val flowable: Flowable<MovieImgBean> = Flowable.create({ e: FlowableEmitter<MovieImgBean> ->
            try {
                val uri = Glide.with(context).asFile().load(movieBean.thumb).submit().get()
                val movieImg = MovieImgBean(movieBean.drama_id, movieBean.name, movieBean.thumb, uri.toString())
                AppDatabase.getInstance(context).movieImgDao().insert(movieImg)
                CacheModelFactory.instance.saveModelDisk(context, SharedPreferencesGroup.DataCache.Key_Movie_Img_Expire)
                e.onNext(movieImg)
            } catch (ex: Exception) {
                KLog.e(TAG, ex.message)
            }
            e.onComplete()
        }, BackpressureStrategy.BUFFER)
        return flowable.compose(CacheModelFactory.logSource(CacheModelFactory.SOURCE_NET, List<MovieImgBean>::javaClass.name))
    }


}