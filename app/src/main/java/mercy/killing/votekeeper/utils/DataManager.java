package mercy.killing.votekeeper.utils;

import android.content.Context;
import android.content.SharedPreferences;

import mercy.killing.votekeeper.models.User;

/**
 * Created by KOHA on 7/9/16.
 */

public class DataManager {
    /* Data Keys */
    public static String USER_ID = "user_id";
    public static String USER_PW = "user_pw";
    public static String USER_NAME = "user_name";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    public DataManager instance;

    public DataManager() {
    }

    public void initializeManager(Context c) {
        this.context = c;
        preferences = context.getSharedPreferences("VoteKeeper", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void save(String key, String data) {
        editor.putString(key, data);
        editor.apply();
    }

    public void removeAllData() {
        editor.clear();
        editor.apply();
    }

    public boolean hasActiveUser() {
        return preferences.getBoolean("hasActiveUser", false);
    }

    public void saveUser(User user){
        editor.putString(USER_ID, user.getId());
        editor.putString(USER_PW, user.getPw());
        editor.putString(USER_NAME, user.getName());
        editor.putBoolean("hasActiveUser", true);
        editor.apply();
    }
    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }

}
