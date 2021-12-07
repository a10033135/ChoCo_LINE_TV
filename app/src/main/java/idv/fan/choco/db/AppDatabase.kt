package idv.fan.choco.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import idv.fan.choco.db.dao.DateConverter
import idv.fan.choco.db.dao.MovieDao
import idv.fan.choco.db.dao.MovieImgDao
import idv.fan.choco.model.MovieBean
import idv.fan.choco.model.MovieImgBean

@TypeConverters(DateConverter::class)
@Database(entities = [MovieBean::class, MovieImgBean::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieImgDao(): MovieImgDao

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, AppDatabase::class.java, DbNames.APP_DB).allowMainThreadQueries().build()
            }
            return instance!!
        }

        fun destroyDatabase() {
            instance?.close()
            instance = null
        }
    }
}