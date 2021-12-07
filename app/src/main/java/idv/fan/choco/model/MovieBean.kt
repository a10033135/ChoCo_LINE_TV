package idv.fan.choco.model

import androidx.room.Entity
import idv.fan.choco.db.DbNames.MOVIE_TABLE
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