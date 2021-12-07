package idv.fan.choco.pref

import android.content.Context
import android.content.SharedPreferences
import com.socks.library.KLog

class SharedPreferencesService {
    private val TAG = SharedPreferencesService::class.java.simpleName
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(SharedPreferencesGroup.DataCache.Group_Expire, Context.MODE_PRIVATE)
    }

    fun save(key: String, value: Any) {
        sharedPreferences?.let { pref ->
            val editor = pref.edit()
            when (value) {
                is String -> {
                    editor.putString(key, value as String?)
                }
                is Int -> {
                    editor.putInt(key, (value as Int?)!!)
                }
                is Long -> {
                    editor.putLong(key, (value as Long?)!!)
                }
                is Boolean -> {
                    editor.putBoolean(key, (value as Boolean?)!!)
                }
            }
            val success = editor.apply()
            KLog.i(TAG, "key:$key save success:$success")
        }
    }

    fun getLong(key: String): Long {
        return sharedPreferences?.getLong(key, 0L) ?: 0L
    }

    fun getString(key: String): String {
        return sharedPreferences?.getString(key, "") ?: ""
    }

    fun hasKey(key: String?): Boolean {
        return sharedPreferences?.contains(key) ?: false
    }

    fun clear() {
        sharedPreferences?.edit()?.clear()?.apply()
    }

    fun removeByKey(key: String) {
        KLog.i(TAG, "removeByKey:$key")
        sharedPreferences?.edit()?.remove(key)?.apply()
    }
}