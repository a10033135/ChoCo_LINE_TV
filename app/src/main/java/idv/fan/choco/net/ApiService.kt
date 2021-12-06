package idv.fan.cathaybk.net

import android.util.Log
import com.google.gson.GsonBuilder
import com.socks.library.KLog
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okio.IOException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService private constructor() {

    private val TIME_OUT = 30L

    /* Github API 網址 */
    private val BASE_URL = "https://static.linetv.tw/interview/"
    private var retrofit: Retrofit

    companion object {
        val instance: ApiService by lazy { ApiService() }
    }

    init {
        val client = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(UnzippingInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    fun <T> getApiService(classofT: Class<T>): T {
        return retrofit.create(classofT)
    }


    class UnzippingInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val response = chain!!.proceed(chain.request())
            return unzip(response)
        }

        @Throws(IOException::class)
        private fun unzip(response: Response): Response {

            val respBody = response.body ?: return response
            try {
                val json = JSONObject(respBody.string())
                var ret = ""
                if (json.has("data")) {
                    val jsonObj = json.optJSONObject("data")
                    if (jsonObj != null) {
                        ret = jsonObj.toString()
                    } else {
                        val jsonArray = json.optJSONArray("data")
                        jsonArray?.let { ret = it.toString() }
                    }
                } else {
                    ret = json.toString()
                }

                val contentType = respBody.contentType()
                val responseBody = ResponseBody.create(contentType, ret)

                return response.newBuilder()
                    .headers(response.headers)
                    .body(responseBody)
                    .build()
            } catch (exception: Exception) {
                Log.e("TAG", exception.toString())
                Log.e("TAG", response.toString())
            }
            return response
        }
    }
}