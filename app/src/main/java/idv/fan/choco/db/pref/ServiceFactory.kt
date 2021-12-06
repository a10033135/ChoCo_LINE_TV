package idv.fan.choco.db.pref

/**
 * Created by Warren on 2016/12/28.
 */
object ServiceFactory {
    private var sharedPreferencesService: SharedPreferencesService? = null

    fun getSharedPreferencesService(): SharedPreferencesService? {
        if (sharedPreferencesService == null) {
            sharedPreferencesService = SharedPreferencesService()
        }
        return sharedPreferencesService
    }
}