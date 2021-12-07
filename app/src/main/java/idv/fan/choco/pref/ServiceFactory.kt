package idv.fan.choco.pref

object ServiceFactory {
    private var sharedPreferencesService: SharedPreferencesService? = null

    fun getSharedPreferencesService(): SharedPreferencesService {
        if (sharedPreferencesService == null) {
            sharedPreferencesService = SharedPreferencesService()
        }
        return sharedPreferencesService!!
    }
}