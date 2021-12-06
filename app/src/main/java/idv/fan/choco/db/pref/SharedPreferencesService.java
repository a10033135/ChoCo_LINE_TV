package idv.fan.choco.db.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.socks.library.KLog;

import java.util.HashMap;

public class SharedPreferencesService {
    private final String TAG = SharedPreferencesService.class.getSimpleName();
    private static final String NAME = "oneApp";

    private SharedPreferences sharedPreferences;

    public void init(Context context) {
        if (context != null) {
            sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
    }

    public void init(Context context, String name) {
        if (context != null) {
            sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
    }

    public void save(HashMap<String, String> hashMap) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (String key : hashMap.keySet()) {
                String value = hashMap.get(key);
                editor.putString(key, value);
            }
            editor.commit();
        }
    }

    public void save(String key, Object value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            }
//            editor.apply();

            boolean success = editor.commit();

            KLog.i(TAG, "key:" + key + " save success:" + success);
        }
    }

    public <T> T getData(String key, Class<T> tClass) {
        if (sharedPreferences != null) {
            String className = tClass.getSimpleName().toLowerCase();
            KLog.d(TAG, "className : " + className);
            if (String.class.getSimpleName().toLowerCase().equals(className)) {
                String str = sharedPreferences.getString(key, "");
                return (T) str;
            } else if (Integer.class.getSimpleName().toLowerCase().equals(className)) {
                Integer value = sharedPreferences.getInt(key, 0);
                return (T) value;
            } else if (int.class.getSimpleName().toLowerCase().equals(className)) {
                Integer value = sharedPreferences.getInt(key, 0);
                return (T) value;
            } else if (Long.class.getSimpleName().toLowerCase().equals(className)) {
                Long value = sharedPreferences.getLong(key, 0);
                return (T) value;
            } else if (Boolean.class.getSimpleName().toLowerCase().equals(className)) {
                Boolean value = sharedPreferences.getBoolean(key, false);
                return (T) value;
            }
        }
        return null;
    }

    public boolean getBooleanData(String key, boolean defaultValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, defaultValue);
        }
        return false;
    }

    public String getData(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public boolean hasKey(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.contains(key);
        }
        return false;
    }

    public void clearKeyData(String key) {
        if (sharedPreferences != null) {
            sharedPreferences.edit()
                    .putString(key, "")
                    .commit();
        }
    }

    public void clear() {
        if (sharedPreferences != null) {
            sharedPreferences.edit().clear().commit();
        }
    }

    public void removeByKey(String key) {
        KLog.i(TAG, "removeByKey:" + key);
        if (sharedPreferences != null) {
            sharedPreferences.edit()
                    .remove(key)
                    .commit();
        }
    }

}
