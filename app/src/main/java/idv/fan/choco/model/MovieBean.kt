package idv.fan.choco.model

import androidx.room.Entity
import idv.fan.choco.db.DbNames.MOVIE_TABLE

@Entity(tableName = MOVIE_TABLE, primaryKeys = arrayOf("name", "created_at"))
data class MovieBean(
    val created_at: String,
    val drama_id: Int,
    val name: String,
    val rating: Float,
    val thumb: String,
    val total_views: Int
)