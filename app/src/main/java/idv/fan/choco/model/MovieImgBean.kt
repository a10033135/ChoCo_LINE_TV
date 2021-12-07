package idv.fan.choco.model

import androidx.room.Entity
import idv.fan.choco.db.DbNames.MOVIE_IMG_TABLE

@Entity(tableName = MOVIE_IMG_TABLE, primaryKeys = ["drama_id"])
data class MovieImgBean(
    val drama_id: Int,
    val name: String,
    val thumb: String,
    val imgUri: String
)