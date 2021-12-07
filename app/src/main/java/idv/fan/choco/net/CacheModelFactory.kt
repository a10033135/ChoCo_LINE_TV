package idv.fan.choco.net

import android.content.Context
import com.socks.library.KLog
import idv.fan.choco.db.AppDatabase
import idv.fan.choco.pref.ServiceFactory
import idv.fan.choco.pref.SharedPreferencesService
import idv.fan.choco.model.MovieBean
import idv.fan.choco.model.MovieImgBean
import io.reactivex.*
import io.reactivex.functions.Consumer
import java.io.File

class CacheModelFactory {

    companion object {
        private var modelCacheFactory: CacheModelFactory? = null
        private val EXPIRE_TIME = 5 * 60 * 1000 // 網路資料的逾期時間：五分鐘，逾期則需重取網路資料
        private val TAG = CacheModelFactory::class.java.simpleName

        val SOURCE_DISK = "DISK"
        val SOURCE_NET = "NET"

        //memory cache
        val instance: CacheModelFactory
            get() {
                if (modelCacheFactory == null) {
                    modelCacheFactory = CacheModelFactory()
                }
                return modelCacheFactory!!
            }

        fun <T> logSource(source: String, classOfName: String): FlowableTransformer<T, T> {
            return FlowableTransformer { upstream: Flowable<T> ->
                upstream
                    .doOnNext(Consumer { data: T? ->
                        if (data == null) {
                            KLog.i(TAG, "$classOfName $source does not have any data.")
                        } else {
                            KLog.i(TAG, "$classOfName $source has the data you are looking for!")
                        }
                    } as Consumer<T>)
            }
        }
    }

    fun getMovieListModelDisk(
        context: Context, expireKey: String, isForceDisk: Boolean
    ): Flowable<List<MovieBean>> {
        KLog.i(TAG, "getMovieListModelDisk")
        val movieDao = AppDatabase.getInstance(context).movieDao()
        // 逾期清空 room
        if (isModelExpire(context, expireKey) && !isForceDisk) {
            movieDao.cleanMovies()
            return Flowable.empty()
        }
        val movieList = movieDao.getMovies()
        return Flowable.just(movieList).compose(logSource(SOURCE_DISK, movieList::class.java.simpleName))
    }

    fun getMovieImgModelDisk(context: Context, movieBean: MovieBean, expireKey: String, isForceDisk: Boolean)
            : Flowable<MovieImgBean> {
        KLog.i(TAG, "getMovieImgModelDisk")
        val movieImgDao = AppDatabase.getInstance(context).movieImgDao()
        val movieImg = movieImgDao.getMovieImg(movieBean.drama_id) ?: return Flowable.empty()
        // 逾期清空 room 跟暫存檔案
        if (isModelExpire(context, expireKey) && !isForceDisk) {
            val file = File(movieImg.imgUri)
            if (file.exists()) file.delete()
            movieImgDao.deleteMovieImg(movieImg)
            return Flowable.empty()
        }
        return Flowable.just(movieImg).compose(logSource(SOURCE_DISK, movieImg::class.java.simpleName))
    }

    fun saveModelDisk(context: Context, saveExpireKey: String) {
        KLog.i(TAG, "saveModelDisk")
        val sharedPreferencesService: SharedPreferencesService = ServiceFactory.getSharedPreferencesService()
        sharedPreferencesService.init(context)
        sharedPreferencesService.save(saveExpireKey, System.currentTimeMillis())
    }

    /* 判斷從網路下載的資料是否逾期，若無逾期則從room裏取資料，逾期則重取資料 */
    private fun isModelExpire(context: Context, expireKey: String): Boolean {
        val sharedPreferencesService: SharedPreferencesService = ServiceFactory.getSharedPreferencesService()
        sharedPreferencesService.init(context)
        val expireTime = sharedPreferencesService.getLong(expireKey)
        val now = System.currentTimeMillis()
        return now - expireTime > EXPIRE_TIME
    }
}
