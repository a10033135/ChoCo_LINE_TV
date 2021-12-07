package idv.fan.choco.net

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import idv.fan.choco.model.MovieBean
import idv.fan.choco.model.MovieImgBean
import io.reactivex.Flowable
import okhttp3.ResponseBody
import java.io.File
import java.net.URI

interface ChocoInteractor {

    /**
     * 先用 room 搜尋資料，如果沒有則網路下載影片列表
     * */
    fun getMovies(context: Context): Flowable<List<MovieBean>>

    /**
     * 用網路下載影片列表
     * */
    fun getMoviesNetwork(context: Context): Flowable<List<MovieBean>>

    /**
     * 根據關鍵字搜尋 room 資料庫
     * @param key 搜尋關鍵字
     * */
    fun searchMovies(context: Context, key: String): Flowable<List<MovieBean>>

    /**
     * 先用 room 搜尋資料，如果沒有則網路下載影片圖片
     * */
    fun getMovieImg(context: Context, movieBean: MovieBean): Flowable<MovieImgBean>

    /**
     * 用網路下載影片列表
     * */
    fun getMovieImgNetwork(context: Context, movieBean: MovieBean): Flowable<MovieImgBean>

}