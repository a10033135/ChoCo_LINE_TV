package idv.fan.choco.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import idv.fan.choco.model.MovieBean
import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insert(movieList: List<MovieBean>): Array<Long>

    @Query("SELECT * FROM MOVIES")
    fun getMovies(): List<MovieBean>

    @Query("SELECT * FROM MOVIES WHERE name like :searchName")
    fun searchMovieName(searchName: String): List<MovieBean>

    @Query("SELECT * FROM MOVIES WHERE drama_id like :dramaId LIMIT 1")
    fun searchMovieDramaId(dramaId: Int): MovieBean

    @Query("DELETE FROM MOVIES")
    fun cleanMovies()
}