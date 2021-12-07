package idv.fan.choco.db.dao

import androidx.room.*
import idv.fan.choco.model.MovieImgBean

@Dao
interface MovieImgDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insert(movieImg: MovieImgBean)

    @Query("SELECT * FROM MOVIE_IMG WHERE drama_id = :dramaId LIMIT 1")
    fun getMovieImg(dramaId: Int): MovieImgBean?

    @Delete
    fun deleteMovieImg(movieImg: MovieImgBean)
}