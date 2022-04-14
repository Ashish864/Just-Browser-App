package justemazine.justbrowser;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.room.Room;

import justemazine.justbrowser.common.Const;
import justemazine.justbrowser.entity.dao.AppDatabase;
import justemazine.justbrowser.utils.SharedPreferencesUtils;

public class EasyApplication extends Application {

    AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        initSetting();
        initDB();
    }

    private void initSetting() {
        SharedPreferences sp = SharedPreferencesUtils.getSettingSP(this);
        if (sp == null) {
            return;
        }
        boolean hasBoot = sp.contains(SharedPreferencesUtils.KEY_FIRST_BOOT);
        if (hasBoot) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(SharedPreferencesUtils.KEY_FIRST_BOOT, false);
            editor.apply();
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(SharedPreferencesUtils.KEY_FIRST_BOOT, true);
            editor.apply();
        }
    }

    private void initDB() {
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, Const.APP_DATABASE_NAME).build();
    }

    public AppDatabase getAppDatabase() {
        return db;
    }
}
