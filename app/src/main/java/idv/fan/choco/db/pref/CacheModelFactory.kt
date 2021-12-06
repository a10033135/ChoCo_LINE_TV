package idv.fan.choco.db.pref

import android.content.Context
import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.socks.library.KLog
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableTransformer
import io.reactivex.functions.Consumer
import java.util.concurrent.ConcurrentHashMap

class CacheModelFactory {
    private val TAG = CacheModelFactory::class.java.simpleName


    companion object {
        const val CACHE_EXPIRETIME: Long = 30000 // 30 sec cache time
//        const val CACHE_EXPIRETIME: Long = 3600000 //1 hr cache time
        private var modelCacheFactory: CacheModelFactory? = null

        fun getInstance(): CacheModelFactory? {
            if (modelCacheFactory == null) {
                modelCacheFactory = CacheModelFactory()
            }
            return modelCacheFactory
        }
    }


    fun <T> getModelDisk(context: Context, classOfT: Class<T>, key: String, expireKey: String, isForceDisk: Boolean): Flowable<T> {
        KLog.i(TAG, "getModelDisk")
        if (isModelExpire(context, expireKey) && !isForceDisk) {
            return Flowable.empty()
        }
        val flowable = Flowable.create({ e: FlowableEmitter<T> ->
            ServiceFactory.getSharedPreferencesService()?.let { sharePref ->
                sharePref.init(context, PrefGroup.MoviePreference.Name)
                val json: String = sharePref.getData(key, String::class.java)
                if (!TextUtils.isEmpty(json)) {
                    val gson = GsonBuilder().create()
                    try {
                        val classT = gson.fromJson(json, classOfT)
                        e.onNext(classT)
                    } catch (ex: Exception) {
                        e.onError(ex)
                    }
                }
                e.onComplete()
            }
        }, BackpressureStrategy.BUFFER)
        return flowable.compose(logSource("Disk", classOfT))
    }

    fun <T> logSource(source: String, classOfT: Class<T>): FlowableTransformer<T, T>? {
        return FlowableTransformer { upstream: Flowable<T> ->
            upstream
                .doOnNext(Consumer { data: T? ->
                    if (data == null) {
                        KLog.i(TAG, classOfT.simpleName + " " + source + " does not have any data.")
                    } else {
                        KLog.i(TAG, classOfT.simpleName + " " + source + " has the data you are looking for!")
                    }
                } as Consumer<T>)
        }
    }

    fun saveModelDisk(context: Context?, `object`: Any?, saveKey: String?, saveExpireKey: String?) {
        ServiceFactory.getSharedPreferencesService()?.let { sharePref ->
            sharePref.init(context, PrefGroup.MoviePreference.Name)
            val gson = GsonBuilder().create()
            val json = gson.toJson(`object`)
            sharePref.save(saveKey, json)
            sharePref.save(saveExpireKey, System.currentTimeMillis())
        }
    }

    private fun isModelExpire(context: Context, expireKey: String): Boolean {
        ServiceFactory.getSharedPreferencesService()?.let { sharePref ->
            sharePref.init(context, PrefGroup.MoviePreference.Name)
            val expireTime: Long = sharePref.getData(expireKey, Long::class.java)
            val now = System.currentTimeMillis()
            return now - expireTime > CACHE_EXPIRETIME
        }
        return false
    }

}