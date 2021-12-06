package idv.fan.choco.db

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
    fun getMovies(): Flowable<List<MovieBean>>

    @Query("SELECT name FROM MOVIES")
    fun queryMovies(): Flowable<List<MovieBean>>
}