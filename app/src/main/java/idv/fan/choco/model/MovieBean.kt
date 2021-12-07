package idv.fan.choco.model

import android.content.Context
import androidx.room.Entity
import idv.fan.choco.R
import idv.fan.choco.db.DbNames.MOVIE_TABLE
import idv.fan.choco.utils.toMovieFormat
import java.util.*

@Entity(tableName = MOVIE_TABLE, primaryKeys = ["drama_id"])
data class MovieBean(
    val created_at: Date,
    val drama_id: Int,
    val name: String,
    val rating: Float,
    val thumb: String,
    val total_views: Int
)

fun MovieBean.getRatingFormat(context: Context): String {
    val rating = String.format("%.4f", this.rating)
    return context.getString(R.string.movie_rating_format, rating)
}