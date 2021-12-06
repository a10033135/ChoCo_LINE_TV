package idv.fan.choco.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import idv.fan.choco.model.MovieBean

@Database(entities = arrayOf(MovieBean::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

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